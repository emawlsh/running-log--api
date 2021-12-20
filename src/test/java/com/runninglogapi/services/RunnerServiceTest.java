package com.runninglogapi.services;

import com.runninglogapi.controllers.RunnerController;
import com.runninglogapi.model.Run;
import com.runninglogapi.model.Runner;
import com.runninglogapi.repository.RunnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RunnerServiceTest {

    @InjectMocks
    private RunnerService runnerService;

    @Mock
    private RunnerRepository runnerRepository;

    private Runner runner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //Given Runner
        runner = new Runner();
        int age = 20;
        String lastName = "Walsh";
        String firstName = "Emma";
        Long runnerId = Long.valueOf(1);

        runner.setLastName(lastName);
        runner.setFirstName(firstName);
        runner.setAge(age);
        runner.setRunnerId(runnerId);
    }

    @Test
    void saveRunner() {
        Mockito.when(runnerRepository.save(ArgumentMatchers.any(Runner.class))).thenReturn(runner);
        Runner runner1 = runnerService.saveRunner(runner);
        assertEquals(runner1, runner);
    }

    @Test
    void findRunners() {
        List<Runner> runnerList = new ArrayList<>();
        runnerList.add(runner);
        Mockito.when(runnerRepository.findAll()).thenReturn(runnerList);
        List<Runner> runnerList2 = runnerService.findRunners();
        assertEquals(runnerList.contains(runner), runnerList2.contains(runner));
    }

    @Test
    void findRunnerById() {
        Mockito.when(runnerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.ofNullable(runner));
        Runner runner1 = runnerService.findRunnerById(runner.getRunnerId());
        assertEquals(runner, runner1);
    }

    @Test
    void deleteRunner() {
        Mockito.doNothing().when(runnerRepository).delete(ArgumentMatchers.any(Runner.class));
        runnerService.deleteRunner(runner);
        Mockito.verify(runnerRepository).delete(ArgumentMatchers.any());
    }
}