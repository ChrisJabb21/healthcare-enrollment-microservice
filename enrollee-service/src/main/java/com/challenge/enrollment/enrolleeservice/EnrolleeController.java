package com.challenge.enrollment.enrolleeservice;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.challenge.enrollment.enrolleeservice.dependent.Dependent;
import com.challenge.enrollment.enrolleeservice.dependent.DependentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@Tag(name = "enrollees", description = "The Enrollee API documentation with annotations and operations.")
@EnableJpaRepositories
public class EnrolleeController {

    @Autowired
    EnrolleeRepository enrolleeRepo;
    @Autowired
    DependentRepository depRepo;

    @Operation(summary = "Get all enrollees.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieval successful!", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Enrollee.class))) }),
            @ApiResponse(responseCode = "404", description = "No enrollees found!", content = @Content) })
    @GetMapping
    /***
     * Retrieve all enrollees in system or datasource.
     * 
     * @return A list of all avialiable enrollees.
     */
    public ResponseEntity<List<Enrollee>> getAllEnrollees() {
        List<Enrollee> enrolleeList = (List<Enrollee>) enrolleeRepo.findAll();
        if (enrolleeList.isEmpty()) {
            System.out.println("No enrollees found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(enrolleeList, HttpStatus.OK);
    }

    @Operation(summary = "Create a enrollee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Enrollee created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Enrollee.class)) }),
            @ApiResponse(responseCode = "404", description = "Enrollee could not be created!", content = @Content) })
    @PostMapping
    /***
     * Add a new enrollee
     * 
     * @param enrollee
     */
    public ResponseEntity<Enrollee> addEnrollee(
            @Parameter(description = "Enrollee object to be created") @RequestBody @Valid Enrollee enrollee) {

        Enrollee savedEnrollee;
        try {
            savedEnrollee = enrolleeRepo.save(enrollee);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedEnrollee, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a enrollee by enrollee id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Enrollee found!", content = { 
                @Content(mediaType = "application/json", schema = @Schema(implementation = Enrollee.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied!", content = @Content), 
        @ApiResponse(responseCode = "404", description = "Enrollee could not be found by id!", content = @Content) })
    @GetMapping("/{enrolleeId}")
    public ResponseEntity<Enrollee> getEnrolleeById(@Parameter(description="id of Enrollee to be searched")@PathVariable ("enrolleeId") int enrolleeId) {

        Optional<Enrollee> enrollee = enrolleeRepo.findById(Integer.valueOf(enrolleeId));

        return enrollee.isPresent() ? new ResponseEntity<>(enrollee.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update a enrollee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Enrollee updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Enrollee.class))}),
        @ApiResponse(responseCode = "404", description = "No Enrollee exists with given id", content = @Content) })
    @PutMapping("/{enrolleeId}")
    public ResponseEntity<Enrollee> updateEnrollee(@Parameter(description="id of Enrollee to be updated.")@PathVariable("enrolleeId") int enrolleeId, @RequestBody Enrollee enrollee) {
          boolean isEnrolleePresent = enrolleeRepo.existsById(enrolleeId);
          if (!isEnrolleePresent) {
              System.out.println("Could not find enrollee in system");
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
  
          Enrollee updatedEnrollee = enrolleeRepo.save(enrollee);
  
          return new ResponseEntity<>(updatedEnrollee, HttpStatus.OK);
      }

    /***
     * Delete an enrollee by id.
     * 
     * Remove an enrollee entirelys, as well as dependents belonging to that
     * enrollee.
     * 
     * @param enrollee
     * @throws Exception 200 http response for success otherwise 404 and display
     *                   error.
     */
    @Operation(summary = "Delete a enrollee.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Enrollee deleted"),
            @ApiResponse(responseCode = "404", description = "Bad request!", content = @Content) })
    @DeleteMapping("/{enrolleeId}")
    public ResponseEntity<Void> deleteEnrollee(
            @Parameter(description = "id of Enrollee to be deleted.") @PathVariable("enrolleeId") int enrolleeId) {

        try {
            enrolleeRepo.deleteById(enrolleeId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all dependents of an enrollee by Id.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieval successful!", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Dependent.class))) }),
            @ApiResponse(responseCode = "404", description = "No enrollees found!", content = @Content) })
    @GetMapping("/{enrolleeId}/dependents")
    public ResponseEntity<List<Dependent>> getDependentsByEnrollee(@PathVariable int enrolleeId){
        
        Enrollee enrolleeToGet = enrolleeRepo.getOne(enrolleeId); //enrolleeRepo.findById(enrolleeId);

        List<Dependent> dependents = enrolleeToGet.getDependents();
        
        if(dependents.isEmpty()) {
            System.out.println("No dependents are registered for this enrollee.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dependents, HttpStatus.OK);
    }

    @Operation(summary = "Add a new dependent to a enrollee by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Depedent created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Enrollee.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content)
        })
    @PostMapping("/{enrolleeId}/dependents")
    public ResponseEntity<Dependent> addDependentByEnrollee(@PathVariable int enrolleeId, @RequestBody Dependent dependentToAdd) {
            Enrollee enrolleeToGet;
    try{
        enrolleeToGet = enrolleeRepo.getOne(Integer.valueOf(enrolleeId));
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    dependentToAdd.setEnrollee(enrolleeToGet); //= dependentToAdd.setEnrollee_Id(enrolleeId)
    depRepo.save(dependentToAdd);
    return new ResponseEntity<>(dependentToAdd, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a dependent by id under enrollee.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Dependent deleted"),
            @ApiResponse(responseCode = "404", description = "Bad request", content = @Content) })
    @DeleteMapping("/{enrolleeId}/dependents/{dependentId}")
    public ResponseEntity<Void> deleteDependentByEnrolleeId(@PathVariable int enrolleeId, @PathVariable int dependentId) 
    {
        Enrollee enrolleeToFind;
        Dependent dependentToDelete;

            try{
                enrolleeToFind = enrolleeRepo.getOne(Integer.valueOf(enrolleeId));
                dependentToDelete = depRepo.getOne(Integer.valueOf(dependentId));
 
                if(enrolleeToFind.equals(dependentToDelete.getEnrollee())){
                depRepo.deleteById(dependentId);   
            }
 
        } 
            catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);       
    }
}