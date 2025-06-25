package com.kristalbal.assetmgmt.service;

import com.kristalbal.assetmgmt.model.Expenditure;
import java.util.List;

public interface ExpenditureService {
    Expenditure createExpenditure(Expenditure expenditure);
    List<Expenditure> getAllExpenditures();
    Expenditure getExpenditureById(Long id);
    void deleteExpenditure(Long id);
}

