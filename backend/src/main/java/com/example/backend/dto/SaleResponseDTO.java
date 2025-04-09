package com.example.backend.dto;

import com.example.backend.entity.Sale;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class SaleResponseDTO {
    private Integer id;
    private String titre;
    private String description;

    @JsonProperty("customer_id")
    private Integer customerId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private PivotSale pivot;

    public SaleResponseDTO(Sale sale, Integer ticketId) {
        this.id = sale.getId();
        this.titre = sale.getTitre();
        this.description = sale.getDescription();
        this.customerId = sale.getCustomerId();
        this.createdAt = sale.getCreatedAt();
        this.updatedAt = sale.getUpdatedAt();
        this.pivot = new PivotSale(ticketId, sale.getId());
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public PivotSale getPivot() {
        return pivot;
    }
    public void setPivot(PivotSale pivot) {
        this.pivot = pivot;
    }
}

class PivotSale {
    @JsonProperty("ticket_id")
    private Integer ticketId;

    @JsonProperty("sale_id")
    private Integer saleId;

    public PivotSale(Integer ticketId, Integer saleId) {
        this.ticketId = ticketId;
        this.saleId = saleId;
    }

    public Integer getTicketId() {
        return ticketId;
    }
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }
    public Integer getSaleId() {
        return saleId;
    }
    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }
}