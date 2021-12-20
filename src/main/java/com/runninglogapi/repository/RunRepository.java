package com.runninglogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.runninglogapi.model.Run;

/**Repository interface that works with Run entities*/
@Repository
public interface RunRepository extends JpaRepository<Run, Long> {

}