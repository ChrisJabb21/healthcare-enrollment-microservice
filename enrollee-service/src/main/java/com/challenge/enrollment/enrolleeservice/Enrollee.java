package com.challenge.enrollment.enrolleeservice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.challenge.enrollment.enrolleeservice.dependent.Dependent;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/***
 * svc-enrollee
 * Entity class 
 */
@Entity
@Table(name = "enrollees")
public class Enrollee {

    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int enrollee_Id;
	private String name;
	private String activation_Status;
    private String birth_Date;
    @Column(name = "phone_number")
    private String contact_Number;
    @OneToMany(mappedBy= "enrollee")
    @Column(nullable = true)
    @JsonManagedReference
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

    public List<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    
}
