package com.runninglogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.runninglogapi.model.Runner;

import java.util.List;

/**Repository interface that works with Runner entities*/
@Repository
public interface RunnerRepository extends JpaRepository<Runner, Long> {
    Runner findByUsername(String lastName);
}

