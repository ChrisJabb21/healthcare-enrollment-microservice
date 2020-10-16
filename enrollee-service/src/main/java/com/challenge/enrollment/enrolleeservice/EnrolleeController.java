package com.challenge.enrollment.enrolleeservice;

import java.util.List;

import com.challenge.enrollment.enrolleeservice.dependent.Dependent;
import com.challenge.enrollment.enrolleeservice.dependent.DependentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;



@RestController
@RequestMapping("/enrollees")
@Tag(name = "enrollees", description = "the Enrollee API with documentation annotations")
@EnableJpaRepositories
public class EnrolleeController {


   
    @Autowired
    EnrolleeRepository enrolleeRepo;
    @Autowired
    DependentRepository depRepo;
     
    
    @Operation(summary = "Get all enrollees")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "found enrollees", content = { 
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Enrollee.class)))}), 
            @ApiResponse(responseCode = "404", description = "No enrollees found", content = @Content) })
    @GetMapping
	public List<Enrollee> getAllEnrollees() {
        List<Enrollee> enrolleeList  = (List<Enrollee>) enrolleeRepo.findAll();
        if (enrolleeList.isEmpty()) {
            return (List<Enrollee>) new ResponseEntity<Enrollee>(HttpStatus.NOT_FOUND);
        }
		return (List<Enrollee>) new ResponseEntity<Enrollee>(HttpStatus.OK);
    }
    

    @Operation(summary = "Create an enrollee")
    @PostMapping
    /***
     * Add a new enrollee
     * @param enrollee
     * @return 200 http response for success otherwise 404 and display error. 
     */
    public Enrollee addEnrollee(@RequestBody Enrollee enrollee) {
        return enrolleeRepo.save(enrollee);
    }



    @Operation(summary = "Get an enrollee by enrollee id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "found the enrollee", content = { 
                @Content(mediaType = "application/json", schema = @Schema(implementation = Enrollee.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content), 
        @ApiResponse(responseCode = "404", description = "Enrollee not found", content = @Content) })
    @GetMapping("/{enrolleeId}")
    public void getEnrollee(@Parameter(description="id of Enrollee to be searched")@PathVariable int enrolleeId) throws Exception {
        
        Enrollee enrolleeToGet = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToGet == null){
            System.out.println("Could not find enrollee in system");
        }
        System.out.println("Enrollee retrieved");
        enrolleeRepo.findById(enrolleeId);
    }





        // - get dependents of an enrollee
    @GetMapping("/enrollee/{enrolleeId}/dependents")
    public void getDependentsByEnrollee(@PathVariable int enrolleeId) throws Exception {
        Enrollee enrolleeToGet = enrolleeRepo.getOne(enrolleeId);
        if(enrolleeToGet == null){
            System.out.println("Could not find enrollee in system");
        }
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
    public Enrollee updateEnrollee(@PathVariable int enrolleeId, @RequestBody Enrollee enrollee) throws Exception {

        Enrollee enrolleeToUpdate = enrolleeRepo.getOne(enrollee.getEnrollee_Id());
        if(enrolleeToUpdate == null){
            System.out.println("Could not find enrollee in system");
        }
        return enrolleeRepo.save(enrolleeToUpdate);
    }








}
