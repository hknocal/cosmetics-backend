package com.kea.cosmeticsbackend.service;

import com.kea.cosmeticsbackend.dto.TreatmentDTO;
import com.kea.cosmeticsbackend.model.Treatment;
import com.kea.cosmeticsbackend.repository.TreatmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TreatmentService {
    private TreatmentRepository treatmentRepository;

    public List<Treatment> getTreatmentByTreatmentId(int id){
        return treatmentRepository.findTreatmentByTreatmentId(id);
    }
    public List<TreatmentDTO> getTreatments(){
        List<Treatment> treatmentList = treatmentRepository.findAll();
        return convertEntityListToDTOList(treatmentList);
    }

    private List<TreatmentDTO> convertEntityListToDTOList(List<Treatment> treatments) {
        List<TreatmentDTO> treatmentDTOList = new ArrayList<>();

        for (Treatment treatment : treatments) {
            TreatmentDTO treatmentDTO = new TreatmentDTO();
            treatmentDTO.setTreatmentType(treatment.getTreatmentType());
            treatmentDTO.setPrice(treatment.getPrice());
            treatmentDTO.setDuration(treatment.getDuration());
            treatmentDTO.setDiscount(treatment.getDiscount());

            treatmentDTOList.add(treatmentDTO);
        }

        return treatmentDTOList;
    }
    public Treatment saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }
    public Treatment updateTreatment(int id, Treatment updatedTreatment){
        if (treatmentRepository.existsById(id)){
            updatedTreatment.setTreatmentId(id);
            return treatmentRepository.save(updatedTreatment);
        }else {
            return null;
        }
    }
    public void deleteTreatment(int id){
        treatmentRepository.deleteById(id);
    }
}
