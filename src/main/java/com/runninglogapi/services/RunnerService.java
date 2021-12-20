package com.runninglogapi.services;


import com.runninglogapi.model.Runner;
import com.runninglogapi.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This class contains the service methods for the various Runner entities.
 */
@Service
@Transactional
public class RunnerService {

    @Autowired
    private RunnerRepository runnerRepository;

    /**
     * This method returns a Runner object that has been saved in the databse
     * @param runner The Runner object being saved
     * @return Runner The saved Runner object
     */
    public Runner saveRunner(Runner runner){
        return runnerRepository.save(runner);
    }

    /**
     * This method returns a list of all the Runner objects in the database
     * @return List<Runner> A list of the Runner objects in the database
     */
    public List<Runner> findRunners() {
        Iterable<Runner> runnerIterator = runnerRepository.findAll();
        List<Runner> runnerList = new ArrayList<>();
        runnerIterator.forEach(runner -> runnerList.add(runner));
        return runnerList;
    }

    /**
     * This method finds a Runner object in the database given the Runner object's id
     * @param id The id of the requested Runner object
     * @return Runner The requested Runner object
     */
    public Runner findRunnerById(Long id){
        Optional<Runner> optionalRunner = runnerRepository.findById(id);
        Runner runner = optionalRunner.get();
        return runner;
    }

    /**
     * This method deletes a given Runner object from the database
     * @param runner The Runner object to be deleted
     */
    public void deleteRunner(Runner runner){
        runnerRepository.delete(runner);
    }

    /**
     * This method finds a runner in the database given the runner's username
     * @param username The username of the runner
     * @return Runner The runner requested
     */
    public Runner findRunnerByUsername(String username){
        Runner runner = runnerRepository.findByUsername(username);

        return runner;
    }


}
