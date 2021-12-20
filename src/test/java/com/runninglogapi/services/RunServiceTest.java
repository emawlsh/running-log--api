package com.runninglogapi.services;

import com.runninglogapi.model.Run;
import com.runninglogapi.model.Runner;
import com.runninglogapi.repository.RunRepository;
import com.runninglogapi.repository.RunnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class RunServiceTest {

    @InjectMocks
    private RunService runService;

    @Mock
    private RunRepository runRepository;

    private Run run;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //Given Run
        run = new Run();
        Double time = 20.0;
        Double distance = 5.0;
        Double avgSpeed = distance/time;
        Long runId = Long.valueOf(1);

        run.setTime(time);
        run.setDistance(distance);
        run.setAvgSpeed(avgSpeed);
        run.setRunId(runId);

    }

    @Test
    void saveRun() {
        Mockito.when(runRepository.save(ArgumentMatchers.any(Run.class))).thenReturn(run);
        Run run1 = runService.saveRun(run);
        assertEquals(run1, run);
    }

    @Test
    void findRunById() {
        Mockito.when(runRepository.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.ofNullable(run));
        Run run1 = runService.findRunById(run.getRunId());
        assertEquals(run, run1);
    }

    @Test
    void deleteRun() {
        Mockito.doNothing().when(runRepository).delete(ArgumentMatchers.any(Run.class));
        runService.deleteRun(run);
        Mockito.verify(runRepository).delete(ArgumentMatchers.any());
    }
}