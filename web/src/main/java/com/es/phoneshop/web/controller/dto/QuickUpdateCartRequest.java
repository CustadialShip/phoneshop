package com.es.phoneshop.web.controller.dto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class QuickUpdateCartRequest {
    List<@Valid QuickQuantityForm> quickQuantityFormList;

    public QuickUpdateCartRequest() {
        quickQuantityFormList = new ArrayList<>();
    }

    public List<QuickQuantityForm> getQuantityFormList() {
        return quickQuantityFormList;
    }

    public void setQuantityFormList(List<QuickQuantityForm> quantityFormList) {
        this.quickQuantityFormList = quantityFormList;
    }
}
