package com.kea.cosmeticsbackend.controller;

import com.kea.cosmeticsbackend.model.Treatment;
import com.kea.cosmeticsbackend.service.TreatmentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment")
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentController {
    private TreatmentService treatmentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Treatment>> getTreatmentById(@PathVariable int id) {
        List<Treatment> treatmentList = treatmentService.getTreatmentByTreatmentId(id);
        return new ResponseEntity<>(treatmentList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Treatment>> getTreatments() {
        List<Treatment> treatmentList = treatmentService.getTreatments();
        return new ResponseEntity<>(treatmentList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Treatment> saveTreatment(@RequestBody Treatment treatment) {
        Treatment savedTreatment = treatmentService.saveTreatment(treatment);
        return new ResponseEntity<>(savedTreatment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable int id, @RequestBody Treatment updatedTreatment) {
        Treatment updated = treatmentService.updateTreatment(id, updatedTreatment);

        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable int id) {
        treatmentService.deleteTreatment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
