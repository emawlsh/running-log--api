document.addEventListener("DOMContentLoaded", () => {

                   getWeather();

                var url = "http://localhost:8080/getRunner?username=" + localStorage.getItem('username')
                   let ajax = new XMLHttpRequest();

                   ajax.open("GET", url, true);

                   ajax.send();

                   ajax.onload = () => {
                    var runner = JSON.parse(ajax.response);
                    var runs = runner.runs;

                     runs.sort(function(a,b){
                       return new Date(a.runDate) - new Date(b.runDate);
                     });

                    var dates = [];
                    var distance = [];
                    var durations = [];
                    var avgSpeeds = [];

                     runs.forEach( run => {
                        dates.push(run.runDate);
                        distance.push(run.distance);
                        durations.push(run.time);
                        avgSpeeds.push(run.avgSpeed);
                     });

                    new Chart(document.querySelector('#distanceChart'), {
                    type: 'line',
                    data: {
                      labels: dates,
                      datasets: [{
                        label: 'Distance',
                        data: distance,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)',
                        tension: 0.1
                      }]
                    },
                    options: {
                      scales: {
                        y: {
                          beginAtZero: true
                        }
                      }
                    }
                  });

                    new Chart(document.querySelector('#durationChart'), {
                    type: 'line',
                    data: {
                      labels: dates,
                      datasets: [{
                        label: 'Duration',
                        data: durations,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)',
                        tension: 0.1
                      }]
                    },
                    options: {
                      scales: {
                        y: {
                          beginAtZero: true
                        }
                      }
                    }
                  });

                    new Chart(document.querySelector('#speedChart'), {
                    type: 'line',
                    data: {
                      labels: dates,
                      datasets: [{
                        label: 'Average Speed',
                        data: avgSpeeds,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)',
                        tension: 0.1
                      }]
                    },
                    options: {
                      scales: {
                        y: {
                          beginAtZero: true
                        }
                      }
                    }
                  });

                  }

});


function getWeather(){

    var province = localStorage.getItem('province')
    var city = localStorage.getItem('city')

     var url = "http://localhost:8080/getWeather?city=" + city + "&province=" + province


     let ajax = new XMLHttpRequest();

     ajax.open("GET", url, true);
     ajax.send();

     ajax.onload = () => {
     console.log(ajax.response)
     var forecast = JSON.parse(ajax.response);

     var tForecast = ""
     for (let i = 0; i < forecast.length ; i++) {
        var date = forecast[i].date
        var tempMin = forecast[i].temp2m.min
        var tempMax = forecast[i].temp2m.max
        var weather = forecast[i].weather
        var windMax = forecast[i].wind10m_max

        if(weather == "clear"){
            weather ="'bi bi-sun-fill'"
        }
        else if(weather == "mcloudy"){
            weather ="'bi bi-cloud-sun-fill'"
        }
        else if(weather == "pcloudy"){
            weather ="'bi bi-cloud-sun-fill'"
        }
        else if(weather == "cloudy"){
            weather = "'bi bi-cloudy-fill'"
        }
        else if(weather == "rain"){
            weather ="'bi bi-cloud-rain-heavy-fill'"
        }
        else if(weather == "lightrain"){
            weather ="'bi bi-cloud-drizzle-fill'"
        }
        else if(weather == "snow"){
            weather ="'bi bi-cloud-snow-fill'"
        }
        else if(weather == "lightsnow"){
            weather ="'bi bi-cloud-snow-fill'"
        }
        else if(weather == "ts"){
            weather ="'bi bi-cloud-lightning-fill'"
        }
        else if(weather == "tsrain"){
            weather ="'bi bi-cloud-lightning-rain-fill'"
        }

       tForecast += "<div class='icon'> <i class=" + weather + "></i>"
       + "<div class='label'> temp min: " + tempMin + "</div>"
       + "<div class='label'> temp max: " + tempMax + "</div>"
       + "<div class='label'> wind speed max: " + windMax + "</div>"
       + "<div class='label'>" + date + "</div></div>"
     }

     document.getElementById("forecastLoad").innerHTML = tForecast;

     }
}
