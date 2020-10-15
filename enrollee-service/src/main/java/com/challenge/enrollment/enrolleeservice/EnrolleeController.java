package com.challenge.enrollment.enrolleeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollees")
public class EnrolleeController {

    @Autowired
    private EnrolleeRepository enrolleeRepo;
    @Autowired
    private Exception enrolleeNotFoundException;
     
    @PostMapping
    /***
     * Add a new enrollee
     * @param enrollee
     * @return 200 http response for success otherwise 404 and display error. 
     */
    public Enrollee addEnrollee(@RequestBody Enrollee enrollee) {
        return enrolleeRepo.save(enrollee);
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
            throw enrolleeNotFoundException;
        }
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
            throw enrolleeNotFoundException;
        }
        return enrolleeRepo.save(enrolleeToUpdate);
    }








}
