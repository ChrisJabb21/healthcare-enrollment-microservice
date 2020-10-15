package com.challenge.enrollment.enrolleeservice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.challenge.enrollment.enrolleeservice.Dependent.Dependent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/***
 * svc-enrollee
 * Entity class 
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "enrollees")
public class Enrollee {

    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int enrollee_Id;
	private String name;
	private String activation_Status;
	private String birth_Date;
    private String contact_Number;
    @OneToMany(mappedBy= "enrollee")
    private List<Dependent> dependents = new ArrayList<>();
    
    public Enrollee(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivation_Status() {
        return activation_Status;
    }

    public void setActivation_Status(String activation_Status) {
        this.activation_Status = activation_Status;
    }

    public String getBirth_Date() {
        return birth_Date;
    }

    public void setBirth_Date(String birth_Date) {
        this.birth_Date = birth_Date;
    }

    public String getContact_Number() {
        return contact_Number;
    }

    public void setContact_Number(String contact_Number) {
        this.contact_Number = contact_Number;
    }

    public Enrollee(String name, String activation_Status, String birth_Date, String contact_Number,
            List<Dependent> dependents) {
        this.name = name;
        this.activation_Status = activation_Status;
        this.birth_Date = birth_Date;
        this.contact_Number = contact_Number;
        this.dependents = dependents;
    }

    
}
