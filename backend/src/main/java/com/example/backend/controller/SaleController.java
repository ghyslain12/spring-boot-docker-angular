package com.example.backend.controller;

import com.example.backend.dto.SaleDTO;
import com.example.backend.dto.SaleMaterialResponseDTO;
import com.example.backend.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<SaleMaterialResponseDTO> getAllSales() {
        return saleService.getAllSales();
    }
    
    @GetMapping("/{id}")
    public SaleMaterialResponseDTO getSaleById(@PathVariable Integer id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    public SaleMaterialResponseDTO createSale(@RequestBody SaleDTO saleDTO) {
        return saleService.createSale(saleDTO);
    }

    @PutMapping("/{id}")
    public SaleMaterialResponseDTO updateSale(@PathVariable Integer id, @RequestBody SaleDTO saleDTO) {
        return saleService.updateSale(id, saleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Integer id) {
        saleService.deleteSale(id);
    }
}