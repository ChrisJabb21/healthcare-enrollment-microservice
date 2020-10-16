package com.challenge.enrollment.enrolleeservice;

import java.util.List;

import com.challenge.enrollment.enrolleeservice.dependent.Dependent;
import com.challenge.enrollment.enrolleeservice.dependent.DependentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollees")
@EnableJpaRepositories
public class EnrolleeController {


   
    @Autowired
    EnrolleeRepository enrolleeRepo;
    @Autowired
    DependentRepository depRepo;
     
    
    @GetMapping
	public List<Enrollee> getAllEnrollees() {
		return enrolleeRepo.findAll();
    }
    
    @PostMapping
    /***
     * Add a new enrollee
     * @param enrollee
     * @return 200 http response for success otherwise 404 and display error. 
     */
    public Enrollee addEnrollee(@RequestBody Enrollee enrollee) {
        return enrolleeRepo.save(enrollee);
    }



    @GetMapping("/{enrolleeId}")
    public void getEnrollee(@PathVariable int enrolleeId) throws Exception {
        Enrollee enrolleeToGet = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToGet == null){
            System.out.println("Could not find enrollee in system");
        }
        System.out.println("Enrollee retrieved");
        enrolleeRepo.findById(enrolleeId);
    }





    @GetMapping("/enrollee/{enrolleeId}/dependents")
    public void getDependentsByEnrollee(@PathVariable int enrolleeId) throws Exception {
        Enrollee enrolleeToGet = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToGet == null){
            System.out.println("Could not find enrollee in system");
        }
        System.out.println("");
        enrolleeRepo.findById(enrolleeId);
    }

    // - Add dependents to an enrollee
    @PostMapping("/enrollee/{enrolleeId}/dependents")
    public Dependent addDependentByEnrollee(@RequestBody Dependent dependent, @PathVariable int enrolleeId )
            throws Exception {
        Enrollee enrollee = enrolleeRepo.getOne(enrolleeId);
        if(enrollee == null){
            System.out.println("Could not find enrollee in system");
        }
        List<Dependent> dependents = enrollee.getDependents();
        
        dependents.add(dependent); 
        return depRepo.save(dependent);
    }

    @DeleteMapping("/enrollee/{enrolleeId}/dependent/{dependentId}")
    public void deleteDependent(@PathVariable int enrolleeId) throws Exception {
        Enrollee enrolleeToDelete = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToDelete == null){
            System.out.println("Could not find enrollee in system");
        }
        System.out.println("Enrollee deleted");
        enrolleeRepo.deleteById(enrolleeId);
    }

    
    /***
     * Remove an enrollee entirely, as well as dependents belonging to that
     * enrollee.
     * 
     * @param enrollee 
     * @throws Exception 200 http response for success otherwise 404 and display error.
     */
    @DeleteMapping("/{enrolleeId}")
    public void deleteEnrollee(@PathVariable int enrolleeId) throws Exception {
        Enrollee enrolleeToDelete = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToDelete == null){
            System.out.println("Could not find enrollee in system");
        }
        System.out.println("Enrollee deleted");
        enrolleeRepo.deleteById(enrolleeId);
    }

    
    /***
     * - Modify an existing enrollee
     * @param enrolleeId
     * @return
     * @throws Exception
     */
    @PatchMapping("/{enrolleeId}")
    public Enrollee updateEnrollee(@PathVariable int enrolleeId) throws Exception {

        Enrollee enrolleeToUpdate = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToUpdate == null){
            System.out.println("Could not find enrollee in system");
        }
        return enrolleeRepo.save(enrolleeToUpdate);
    }








}
