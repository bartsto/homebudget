package com.homebudget.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.homebudget.views.Views;
import com.homebudget.model.Register;

import java.util.List;

public class RegisterSummaryResponse {

    @JsonView(Views.Summary.class)
    private List<Register> listOfRegisters;

    public List<Register> getListOfRegisters() {
        return listOfRegisters;
    }

    public void setListOfRegisters(List<Register> listOfRegisters) {
        this.listOfRegisters = listOfRegisters;
    }
}
