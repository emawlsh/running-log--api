package com.runninglogapi.controllers;

import com.runninglogapi.Util.HttpUtil;
import com.runninglogapi.model.Run;
import com.runninglogapi.model.Runner;
import com.runninglogapi.services.RunService;
import com.runninglogapi.services.RunnerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RunnerControllerTest {


    @InjectMocks
    private RunnerController runnerController;

    @Mock
    private RunnerService runnerService;

    @Mock
    private RunService runService;

    @Mock
    private HttpUtil httpUtil;

    private Runner runner;
    private Run run;
    private Run[] runs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //Given Runner
        runner = new Runner();
        int age = 20;
        String lastName = "Walsh";
        String firstName = "Emma";
        Long runnerId = Long.valueOf(1);
        String username = "ewalsh3";
        String password = "password";
        String email = "ewalsh3@unb.ca";
        String province = "NB";
        String city = "Fredericton";

        runner.setLastName(lastName);
        runner.setFirstName(firstName);
        runner.setAge(age);
        runner.setRunnerId(runnerId);
        runner.setUsername(username);
        runner.setPassword(password);
        runner.setEmail(email);
        runner.setProvince(province);
        runner.setCity(city);

        //Given Run
        run = new Run();
        Double time = 20.0;
        Double distance = 5.0;
        Long runId = Long.valueOf(1);
        Double avgSpeed = distance/time;
        String runDate = "20211216";
        String comments = "run";

        run.setTime(time);
        run.setDistance(distance);
        run.setAvgSpeed(avgSpeed);
        run.setRunDate(runDate);
        run.setComments(comments);
        run.setRunId(runId);

        runs = new Run[1];
        runs[0] = run;
        runner.setRuns(runs);
    }

    @Test
    void getPing() {
        ResponseEntity<String> responseString = runnerController.getPing();
        HttpStatus status = responseString.getStatusCode();
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void addRunner() {
        Mockito.when(runnerService.saveRunner(ArgumentMatchers.any(Runner.class))).thenReturn(runner);
        ResponseEntity<Runner> runnerResponse = runnerController.addRunner(runner.getFirstName(), runner.getLastName(), runner.getUsername(), runner.getPassword(), runner.getEmail());
        Runner runner1 = runnerResponse.getBody();

        assertEquals(runner1.getFirstName(), runner.getFirstName());
        assertEquals(runner1.getLastName(), runner.getLastName());
        assertEquals(runner1.getUsername(), runner.getUsername());
        assertEquals(runner1.getPassword(), runner.getPassword());
        assertEquals(runner1.getEmail(), runner.getEmail());
        assertEquals(HttpStatus.CREATED, runnerResponse.getStatusCode());
    }

    @Test
    void getRunners() {
        List<Runner> runnerList = new ArrayList<>();
        Runner runner1 = new Runner();
        Runner runner2 = new Runner();
        runnerList.add(runner1);
        runnerList.add(runner2);

        Mockito.when(runnerService.findRunners()).thenReturn(runnerList);
        ResponseEntity<List<Runner>> runnerResponse = runnerController.getRunners();
        List<Runner> runnerList2 = runnerResponse.getBody();

        assertEquals(2 , runnerList2.size());
        assertEquals(HttpStatus.OK, runnerResponse.getStatusCode());
    }


    @Test
    void getRuns() {
        Mockito.when(runnerService.findRunnerById(ArgumentMatchers.anyLong())).thenReturn(runner);
        Run[] runs2 = runnerController.getRuns(runner.getRunnerId());

        assertEquals(runner.getRuns() , runs2);
    }


    @Test
    void addRunToRunner() {
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);
        Mockito.when(runnerService.saveRunner(ArgumentMatchers.any(Runner.class))).thenReturn(runner);

        Run run1 = runnerController.addRunToRunner(runner.getUsername(), run.getTime(), run.getDistance(), run.getComments(), run.getRunDate());
        assertEquals(run.getTime(), run1.getTime());
        assertEquals(run.getDistance(), run1.getDistance());
        assertEquals(run.getComments(), run1.getComments());
        assertEquals(run.getRunDate(), run1.getRunDate());
    }


    @Test
    void addRun() {
        Mockito.when(runService.saveRun(ArgumentMatchers.any(Run.class))).thenReturn(run);
        Run run1 = runnerController.addRun( run.getDistance(),run.getTime(), run.getComments(), run.getRunDate());
        assertEquals(run.getTime(), run1.getTime());
        assertEquals(run.getDistance(), run1.getDistance());
        assertEquals(run.getComments(), run1.getComments());
        assertEquals(run.getRunDate(), run1.getRunDate());
    }


    @Test
    void deleteRun() {
        Mockito.when(runService.findRunById(ArgumentMatchers.anyLong())).thenReturn(run);
        Mockito.doNothing().when(runService).deleteRun(ArgumentMatchers.any(Run.class));

        Long id = Long.valueOf(1);
        ResponseEntity runResponse = runnerController.deleteRun(id);

        Mockito.verify(runService).deleteRun(ArgumentMatchers.any());
        assertEquals(HttpStatus.NO_CONTENT, runResponse.getStatusCode());

    }


    @Test
    void getWeather() throws IOException{
        JSONObject obj = new JSONObject();
        JSONArray response = new JSONArray();
        String response1 = "[]";
        Mockito.when(httpUtil.getWeather(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(response);

        String response2 = runnerController.getWeather("NB", "Fredericton");

        assertEquals(response1, response2);

    }

    @Test
    void login(){
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);

        ResponseEntity<String> response = runnerController.login(runner.getUsername(), runner.getPassword());

        assertEquals(new ResponseEntity<String>(HttpStatus.OK), response);
    }

    @Test
    void findRunnerFromUsername(){
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);

        Runner runner1 = runnerController.findRunnerFromUsername(runner.getUsername());

        assertEquals(runner.getFirstName(), runner1.getFirstName());
    }

    @Test
    void editRunner(){
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);
        Mockito.when(runnerService.saveRunner(ArgumentMatchers.any(Runner.class))).thenReturn(runner);

        ResponseEntity<Runner> runnerResponse = runnerController.editRunner(runner.getUsername(), runner.getFirstName(), runner.getLastName(), runner.getEmail(), runner.getProvince(), runner.getCity());
        Runner runner1 = runnerResponse.getBody();;

        assertEquals(runner1.getFirstName(), runner.getFirstName());
        assertEquals(runner1.getLastName(), runner.getLastName());
        assertEquals(runner1.getUsername(), runner.getUsername());
        assertEquals(runner1.getPassword(), runner.getPassword());
        assertEquals(runner1.getEmail(), runner.getEmail());
        assertEquals(runner1.getProvince(), runner.getProvince());
        assertEquals(runner1.getCity(), runner.getCity());
        assertEquals(HttpStatus.OK, runnerResponse.getStatusCode());
    }

    @Test
    void deleteRunner(){
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);
        Mockito.doNothing().when(runnerService).deleteRunner(ArgumentMatchers.any(Runner.class));

        ResponseEntity runnerResponse = runnerController.deleteRunner(runner.getUsername());

        Mockito.verify(runnerService).deleteRunner(ArgumentMatchers.any());
        assertEquals(HttpStatus.NO_CONTENT, runnerResponse.getStatusCode());
    }


    @Test
    void editRunnerRun(){
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);
        Mockito.when(runService.findRunById(ArgumentMatchers.anyLong())).thenReturn(run);
        Mockito.when(runService.saveRun(ArgumentMatchers.any(Run.class))).thenReturn(run);
        Mockito.when(runnerService.saveRunner(ArgumentMatchers.any(Runner.class))).thenReturn(runner);

        ResponseEntity editResponse = runnerController.editRunnerRun(runner.getUsername(), run.getRunId(), run.getDistance(),run.getTime(), run.getComments(), run.getRunDate());
        assertEquals(new ResponseEntity(HttpStatus.OK), editResponse);
    }

    @Test
    void deleteRunFromRunner(){
        Mockito.when(runnerService.findRunnerByUsername(ArgumentMatchers.anyString())).thenReturn(runner);
        Mockito.when(runnerService.saveRunner(ArgumentMatchers.any(Runner.class))).thenReturn(runner);

        Runner runner1 = runnerController.deleteRunFromRunner(runner.getUsername(), run.getRunId());

        assertEquals(runner1.getFirstName(), runner.getFirstName());
        assertEquals(runner1.getLastName(), runner.getLastName());
        assertEquals(runner1.getUsername(), runner.getUsername());
        assertEquals(runner1.getPassword(), runner.getPassword());
        assertEquals(runner1.getEmail(), runner.getEmail());
        assertEquals(runner1.getProvince(), runner.getProvince());
        assertEquals(runner1.getCity(), runner.getCity());
    }

}