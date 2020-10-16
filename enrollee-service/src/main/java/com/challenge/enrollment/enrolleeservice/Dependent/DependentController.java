package com.challenge.enrollment.enrolleeservice.dependent;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dependents")
@EnableJpaRepositories
public class DependentController {
    
    @Autowired
    DependentRepository dependentRepo;

    @GetMapping
	public List<Dependent> getAllDependents(){
		return dependentRepo.findAll();
    }


    @DeleteMapping("/dependent/{dependentId}")
    public void deleteDependent(@PathVariable int dependentId) throws Exception {
        Dependent dependentToDelete = dependentRepo.getOne(dependentId);
        if (dependentToDelete == null) {
            System.out.println("Could not find dependent in system");
        }
        System.out.println("Dependent deleted");
        dependentRepo.deleteById(dependentId);
    }

    @PatchMapping("/{dependentId}")
    public Dependent updateDependent(@PathVariable int dependentId) throws Exception {

        Dependent dependentToUpdate = dependentRepo.getOne(dependentId);
        if (dependentToUpdate == null) {
            System.out.println("Could not find dependent in system");

        }
        return dependentRepo.save(dependentToUpdate);
    }


}
