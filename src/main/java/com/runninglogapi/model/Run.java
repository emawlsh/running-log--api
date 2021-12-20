package com.runninglogapi.model;


import lombok.Getter;
import lombok.Setter;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;

/**Entity class describing a Run*/
@Entity
@Getter @Setter
public class Run implements Serializable {

    private static final long serialVersionUID = 1744094505973332769L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long runId;

    private Double distance;

    private Double time; //change to duration

    private Double avgSpeed;

    private String comments;

    private String runDate;

    public Double getAvgSpeed(){
        Double avgSpeed = this.getDistance() / this.getTime();
        return avgSpeed;
    }

    @Nullable private int id;
}
