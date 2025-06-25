package com.kristalbal.assetmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristalbal.assetmgmt.dto.TransferRequestDTO;
import com.kristalbal.assetmgmt.model.Transfer;
import com.kristalbal.assetmgmt.service.TransferService;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;
    
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS')")
    @PostMapping
    public Transfer createTransfer(@RequestBody TransferRequestDTO request, Authentication auth) {
        return transferService.createTransferFromDto(request, auth);
    }


    @GetMapping
    public List<Transfer> getTransfersForUser(Authentication auth) {
        return transferService.getTransfersForUser(auth);
    }

   

    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable Long id) {
        return transferService.getTransferById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTransfer(@PathVariable Long id) {
        transferService.deleteTransfer(id);
        return "Transfer deleted successfully!";
    }
}
