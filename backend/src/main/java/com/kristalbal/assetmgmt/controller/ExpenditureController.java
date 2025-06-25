package com.kristalbal.assetmgmt.controller;

import com.kristalbal.assetmgmt.model.Expenditure;
import com.kristalbal.assetmgmt.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
public class ExpenditureController {

    @Autowired
    private ExpenditureService expenditureService;

    @PostMapping
    public Expenditure createExpenditure(@RequestBody Expenditure expenditure) {
        return expenditureService.createExpenditure(expenditure);
    }

    @GetMapping
    public List<Expenditure> getAllExpenditures() {
        return expenditureService.getAllExpenditures();
    }

    @GetMapping("/{id}")
    public Expenditure getExpenditureById(@PathVariable Long id) {
        return expenditureService.getExpenditureById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteExpenditure(@PathVariable Long id) {
        expenditureService.deleteExpenditure(id);
        return "Expenditure record deleted successfully!";
    }
}
