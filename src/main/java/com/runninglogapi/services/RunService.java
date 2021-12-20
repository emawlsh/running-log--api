package com.runninglogapi.services;

import com.runninglogapi.repository.RunRepository;
import com.runninglogapi.model.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


/**
 * This class contains the service methods for the various Run entities.
 */
@Service
@Transactional
public class RunService {

    @Autowired
    private RunRepository runRepository;

    /**
     * This method saves a Run object in the database
     * @param run The Run object to be saved
     * @return Run The saved Run object
     */
    public Run saveRun(Run run){
        return runRepository.save(run);
    }

    /**
     * This method finds a Run object given the Run object's id
     * @param id The id of the requested Run object
     * @return Run The requested Run object
     */
    public Run findRunById(Long id){
        Optional<Run> optionalRun = runRepository.findById(id);
        Run run = optionalRun.get();
        return run;
    }

    /**
     * This method deletes a given Run object from the database
     * @param run The Run object to be deleted
     */
    public void deleteRun(Run run){
        runRepository.delete(run);
    }


}
