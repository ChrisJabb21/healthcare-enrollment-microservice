package com.challenge.enrollment.enrolleeservice.enrollee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Integer>{
    
}
