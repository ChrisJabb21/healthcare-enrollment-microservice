package com.challenge.enrollment.enrolleeservice.dependent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dependents")
@EnableJpaRepositories
@Tag(name = "dependent", description = "Dependent API with documentation annotations")
public class DependentController {
    
    @Autowired
    DependentRepository dependentRepo;

    @GetMapping
	public List<Dependent> getAllDependents(){
		return dependentRepo.findAll();
    }

    //TODO test dependents
    //Add documentation

    @Operation(summary = "Update a Depedent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dependent updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Dependent.class))}),
            @ApiResponse(responseCode = "404", description = "No dependent exists with given id", content = @Content) })
    @PutMapping("/{dependentId}")
    public ResponseEntity<Dependent> updateDependent(@Parameter(description = "id of dependent to be updated") @PathVariable("dependentId") int dependentId, @RequestBody Dependent dependent) {
        
        boolean isDependentPresent = dependentRepo.existsById(Integer.valueOf(dependentId));

        if (!isDependentPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Dependent updatedDependent = dependentRepo.save(dependent);

        return new ResponseEntity<>(updatedDependent, HttpStatus.OK);
    }




    @DeleteMapping("/{dependentId}")
    public void deleteDependent(@PathVariable int dependentId) throws Exception {
        Dependent dependentToDelete = dependentRepo.getOne(dependentId);
        if (dependentToDelete == null) {
            System.out.println("Could not find dependent in system");
        }
        System.out.println("Dependent deleted");
        dependentRepo.deleteById(dependentId);
    }
}
