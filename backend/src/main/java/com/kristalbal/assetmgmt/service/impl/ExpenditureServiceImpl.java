package com.kristalbal.assetmgmt.service.impl;

import com.kristalbal.assetmgmt.model.Expenditure;
import com.kristalbal.assetmgmt.repository.ExpenditureRepository;
import com.kristalbal.assetmgmt.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    @Override
    public Expenditure createExpenditure(Expenditure expenditure) {
        expenditure.setDate(LocalDate.now());
        return expenditureRepository.save(expenditure);
    }

    @Override
    public List<Expenditure> getAllExpenditures() {
        return expenditureRepository.findAll();
    }

    @Override
    public Expenditure getExpenditureById(Long id) {
        return expenditureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expenditure not found with ID: " + id));
    }

    @Override
    public void deleteExpenditure(Long id) {
        expenditureRepository.deleteById(id);
    }
}
