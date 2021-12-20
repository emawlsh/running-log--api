package com.runninglogapi.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;


/**Entity class describing a Runner*/
@Entity
@Getter @Setter
public class Runner{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long runnerId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private int age;
    private String province;
    private String city;

    private Run[] runs;

    public void setRuns( Run[] runs) {
        this.runs = runs;
    }

}
