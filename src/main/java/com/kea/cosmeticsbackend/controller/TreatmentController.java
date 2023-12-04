package com.kea.cosmeticsbackend.controller;

import com.kea.cosmeticsbackend.dto.TreatmentDTO;
import com.kea.cosmeticsbackend.model.Treatment;
import com.kea.cosmeticsbackend.service.TreatmentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment")
@CrossOrigin(origins = "https://happy-sky-0a8b63303.4.azurestaticapps.net")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Treatment>> getTreatmentById(@PathVariable int id) {
        List<Treatment> treatmentList = treatmentService.getTreatmentByTreatmentId(id);
        return new ResponseEntity<>(treatmentList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentDTO>> getTreatmentDTOs() {
        List<TreatmentDTO> treatmentDTOList = treatmentService.getTreatments();
        return new ResponseEntity<>(treatmentDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Treatment> saveTreatment(@RequestBody Treatment treatment) {
        Treatment savedTreatment = treatmentService.saveTreatment(treatment);

        if (savedTreatment != null) {
            return new ResponseEntity<>(savedTreatment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
