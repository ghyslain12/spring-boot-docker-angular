package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketDTO {
    private String titre;
    private String description;

    @JsonProperty("sale_id")
    private Integer saleId;

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
    public Integer getSaleId() {
        return saleId;
    }
    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }
}