package com.challenge.enrollment.enrolleeservice.Dependent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.challenge.enrollment.enrolleeservice.Enrollee;

@Entity
@Table(name = "dependents")
public class Dependent {

    @Id
	@GeneratedValue
	private int dependent_Id;
	private String name;
    private String birth_Date;
    @ManyToOne
    @JoinColumn(name = "enrollee_id")
    private Enrollee enrollee;

    

    public Dependent(String name, String birth_Date, Enrollee enrollee) {
        this.name = name;
        this.birth_Date = birth_Date;
        this.enrollee = enrollee;
    }

    @Override
    public String toString() {
        return "Dependent [birth_Date=" + birth_Date + ", dependent_Id=" + dependent_Id + ", enrollee=" + enrollee
                + ", name=" + name + "]";
    }

    public int getDependent_Id() {
        return dependent_Id;
    }

    public void setDependent_Id(int dependent_Id) {
        this.dependent_Id = dependent_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_Date() {
        return birth_Date;
    }

    public void setBirth_Date(String birth_Date) {
        this.birth_Date = birth_Date;
    }

    public Enrollee getEnrollee() {
        return enrollee;
    }

    public void setEnrollee(Enrollee enrollee) {
        this.enrollee = enrollee;
    }

 
    
}
