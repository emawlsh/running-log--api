package com.runninglogapi.controllers;


import com.runninglogapi.Util.HttpUtil;
import com.runninglogapi.services.RunService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.runninglogapi.services.RunnerService;
import com.runninglogapi.model.Runner;
import com.runninglogapi.model.Run;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;


/**Controller for Runner data requests*/
@RestController
public class RunnerController {

    @Autowired
    private RunnerService runnerService;

    @Autowired
    private RunService runService;

    @Autowired
    private HttpUtil httpUtil;


    private final Logger log = LoggerFactory.getLogger(RunnerController.class);


    /**
     * Ping method to check connection
     * @return ResponseEntity confirming connection
     */
    @GetMapping("ping")
    public ResponseEntity<String> getPing() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    /**
     *This method covers the Post mapping for a Runner in the repository
     * @param first The first name of the runner being added
     * @param last The last name of the runner being added
     * @param username The username of the runner being added
     * @param password The password of the runner being added
     * @param email The email of the runner being added
     * @return ResponseEntity<Runner> Returns an http response as well as the new runner object
     */
    @PostMapping("/addRunner")
    public ResponseEntity<Runner> addRunner( String first,  String last, String username, String password, String email) {
        Runner runner = new Runner();

        runner.setFirstName(first);
        runner.setLastName(last);
        runner.setUsername(username);
        runner.setPassword(password);
        runner.setEmail(email);

        Run[] runs = new Run[0];
        runner.setRuns(runs);

        runnerService.saveRunner(runner);
        log.info("Added new runner to repo!");
        return new ResponseEntity<>(runner, HttpStatus.CREATED);
    }

    /**
     * This method covers the Get mapping for all Runners in the repository
     * @return ResponseEntity<List<Runner>> A confirmation response and a list of the Runners in the repository
     */
    @GetMapping("/list")
    public ResponseEntity<List<Runner>> getRunners() {

        return new ResponseEntity<List<Runner>>(runnerService.findRunners(), HttpStatus.OK);
    }

    /**
     * This method covers the Get mapping for a runner's runs in the repository
     * @param runnerId The id of the runner
     * @return Run[] The array of runs belonging to the runner
     */
    @GetMapping("/listRuns")
    public Run[] getRuns(Long runnerId) {
        Runner runner = runnerService.findRunnerById(runnerId);
        return runner.getRuns();
    }


    /**
     *This method covers the Post mapping for a runner's run in the repository
     * @param username The username of the runner that the run will be added to
     * @param duration The time of the run
     * @param distance  The distance of the run
     * @param comments The comments for the run
     * @param runDate The date of the run
     * @return Run The run that has been added to the runner
     */
    @PostMapping("/addRunToRunner")
    public Run addRunToRunner(String username, Double duration, Double distance, String comments, String runDate) {
        Runner runner = runnerService.findRunnerByUsername(username);

        Run run = addRun(distance, duration, comments, runDate);

        Run[] runs = runner.getRuns();
        Run[] runs2 = new Run[runs.length+1];
        for(int i = 0; i< runs.length; i++){
            runs2[i] = runs[i];
        }
        runs2[runs.length] = run;

        runner.setRuns(runs2);
        runnerService.saveRunner(runner);

        return run;
    }


    /**
     * This method covers the Post mapping for a Run in the repository
     * @param duration The run time
     * @param distance The run distance
     * @param comments The comments for the run
     * @param runDate The date of the run
     * @return Run Returns a new Run object
     */
    public Run addRun(Double distance, Double duration, String comments, String runDate) {
        Run run = new Run();
        run.setTime(duration);
        run.setDistance(distance);
        run.setAvgSpeed(distance/duration);
        run.setComments(comments);
        run.setRunDate(runDate);
        runService.saveRun(run);
        return run;
    }

    /**
     * This method covers the Delete mapping for a Run in the repository
     * @param id The id of the run being deleted
     * @return ResponseEntity A confirmation message
     */
    public ResponseEntity deleteRun(@RequestParam Long id){
        Run run = runService.findRunById(id);
        runService.deleteRun(run);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * This method covers the Get mapping for a weather information object
     * @param province The province for the location of the weather information
     * @param city  The city for the location of the weather information
     * @return String The JSONArray of the weather result in string form (for UI purposes)
     * @throws IOException
     */
    @GetMapping("/getWeather")
    public String getWeather(String province, String city) throws IOException {
        JSONArray array = httpUtil.getWeather(province, city);
        return array.toString();
    }


    /**
     * The method that covers the Get mapping for the runner account login
     * @param username The username of the runner account
     * @param password  The password of the runner account
     * @return ResponseEntity<String> A message either allowing or denying access to the account
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(String username, String password){
        try {
            Runner runner = runnerService.findRunnerByUsername(username);
            if(Objects.equals(runner.getPassword(), password)){
                return new ResponseEntity<String>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("invalid password", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<String>("invalid username", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * A method that covers the Get mapping for a runner in the repository (by username)
     * @param username The username of the requested runner
     * @return Runner The runner found using the given username
     */
    @GetMapping("/getRunner")
    public Runner findRunnerFromUsername(String username) {
        Runner runner = runnerService.findRunnerByUsername(username);
        return runner;
    }

    /**
     * A method that covers the Post mapping for a runner in the repository
     * @param username The username of the runner being edited
     * @param firstName The first name of the runner
     * @param lastName The last name of the runner
     * @param email The email of the runner
     * @param province The runner's province
     * @param city The runner's city
     * @return ResponseEntity<Runner> A message confirming that the runner has been successfully updated as well as the updated runner
     */
    @PostMapping("/editRunner")
    public ResponseEntity<Runner> editRunner(String username, String firstName, String lastName, String email, String province, String city){
        Runner runner = findRunnerFromUsername(username);
        runner.setFirstName(firstName);
        runner.setLastName(lastName);
        runner.setEmail(email);
        runner.setProvince(province);
        runner.setCity(city);
        runnerService.saveRunner(runner);
        return new ResponseEntity<>(runner, HttpStatus.OK);
    }

    /**
     * This method covers the Delete mapping for a runner in the repository (by username)
     * @param username The username of the runner being deleted
     * @return ResponseEntity A message confirming that the runner has been deleted
     */
    @DeleteMapping("/deleteRunner")
    public ResponseEntity deleteRunner(String username){
        Runner runner = runnerService.findRunnerByUsername(username);
        runnerService.deleteRunner(runner);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * This method covers the Post mapping for a runner's run in the repository
     * @param username The username of the runner
     * @param runId The run Id of the run being edited
     * @param duration The duration of the run
     * @param distance The distance of the run
     * @param comments The comments for the run
     * @param runDate The date of the run
     * @return ResponseEntity A message confirming that the run has been deleted
     */
    @PostMapping("/editRunnerRun")
    public ResponseEntity editRunnerRun(String username, Long runId, Double duration, Double distance, String comments, String runDate) {
        Runner runner = runnerService.findRunnerByUsername(username);

        Run run = runService.findRunById(runId);

        run.setRunDate(runDate);
        run.setDistance(distance);
        run.setTime(duration);
        run.setComments(comments);

        runService.saveRun(run);

        Run[] runs = runner.getRuns();
        Run[] runs2 = new Run[runs.length];

        for (int i = 0, j = 0; i < runs.length; i++) {
            if (Objects.equals(runs[i].getRunId(), runId)) {
                runs2[i] = run;
            }
            else{
                runs2[i] = runs[i];
            }
        }
        runner.setRuns(runs2);
        runnerService.saveRunner(runner);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * This method covers the Delete mapping for a runner's run in the repository
     * @param username The username of the runner
     * @param runId The run Id of the run being deleted
     * @return Runner The runner whos run has been deleted (for UI purposes)
     */
    @DeleteMapping("/deleteRunFromRunnerByUsername")
    public Runner deleteRunFromRunner(String username, Long runId){
        Runner runner = runnerService.findRunnerByUsername(username);

        Run[] runs = runner.getRuns();
        Run[] runs2 = new Run[runs.length-1];

        for (int i = 0, j = 0; i < runs.length; i++) {
            if (Objects.equals(runs[i].getRunId(), runId)) {
                deleteRun(runs[i].getRunId());
            }
            else{
                runs2[j++] = runs[i];
            }
        }

        runner.setRuns(runs2);
        runnerService.saveRunner(runner);

        return runner;
    }


}

