package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class SaleDTO {
    private String titre;
    private String description;

    @JsonProperty("customer_id")
    private Integer customerId;

    private Map<String, Boolean> materials = new HashMap<>();
    
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
    
    @JsonAnyGetter
    public Map<String, Boolean> getMaterials() {
        return materials;
    }

    @JsonAnySetter
    public void setMaterial(String key, Boolean value) {
        this.materials.put(key, value);
    }
    
	public void setMaterials(Map<String, Boolean> materials) {
		this.materials = materials;
	}
}