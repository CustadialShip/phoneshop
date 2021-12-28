package com.es.phoneshop.web.controller.dto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class QuickUpdateCartRequest {
    @Valid
    Map<String, QuickQuantityForm> quickQuantityFormList;

    public QuickUpdateCartRequest() {
        quickQuantityFormList = new HashMap<>();
    }

    public QuickUpdateCartRequest(int size) {
        quickQuantityFormList = new HashMap<>();
        for(int i = 0; i < size; i++){
            quickQuantityFormList.put(String.valueOf(i), new QuickQuantityForm());
        }
    }

    public Map<String, QuickQuantityForm> getQuantityFormList() {
        return quickQuantityFormList;
    }

    public void setQuantityFormList(Map<String, QuickQuantityForm> quantityFormList) {
        this.quickQuantityFormList = quantityFormList;
    }
}
