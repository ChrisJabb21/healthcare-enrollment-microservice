package com.challenge.enrollment.enrolleeservice.dependent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentRepository extends JpaRepository <Dependent,Integer> {
     
}
