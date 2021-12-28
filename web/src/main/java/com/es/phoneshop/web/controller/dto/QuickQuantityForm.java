package com.es.phoneshop.web.controller.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class QuickQuantityForm {

    @DecimalMin(value = "1", message = "should be > 0")
    @NotNull(message = "should be not null")
    private Long quantity;

    @NotNull(message = "should be not null")
    private String model;

    public QuickQuantityForm() {
    }

    public QuickQuantityForm(Long quantity, String model) {
        this.quantity = quantity;
        this.model = model;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
