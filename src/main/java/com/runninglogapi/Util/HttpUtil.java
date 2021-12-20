package com.runninglogapi.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class HttpUtil {


    private URL getGeoUrl (String province, String city) throws MalformedURLException {
        URL url = new URL("http://api.positionstack.com/v1/forward?access_key=13bb2b6f246325a19f67209d93508af6&query= " + city + " " + province);
        return url;
    }

private String makeHttpRequest(URL url) throws IOException {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    Response response = client.newCall(request).execute() ;
    String responseBody = response.body().string();
    return responseBody;
}

private JSONObject getCoordinates(String province, String city) throws IOException {

    URL url = getGeoUrl(province, city);
    String responseBody = makeHttpRequest(url);
    JSONObject obj = new JSONObject(responseBody);
    JSONArray lineItems = obj.getJSONArray("data");
    JSONObject obj2 = lineItems.getJSONObject(0);

    JSONObject obj3 = new JSONObject();
    obj3.put("latitude", obj2.getDouble("latitude"));
    obj3.put("longitude", obj2.getDouble("longitude"));
    return obj3;
}

public JSONArray getWeather(String province, String city) throws IOException {
    JSONObject obj = getCoordinates(province, city);
    String latt = Double.toString(obj.getDouble("latitude"));
    String longt = Double.toString(obj.getDouble("longitude"));
    URL url  = new URL("http://www.7timer.info/bin/api.pl?lon=" + longt + "&lat=" + latt + "&product=civillight&output=json");
    String responseBody = makeHttpRequest(url);
    JSONObject obj2 = new JSONObject(responseBody);
    JSONArray array = obj2.getJSONArray("dataseries");
    return array;
}


}
