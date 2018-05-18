package com.jc.nlp.domain;

import com.jc.core.persistence.entity.DataEntity;

public class Right extends DataEntity<Right> {

    private String set_basis;

    private String set_basis_summary;

    public String getSet_basis() {
        return set_basis;
    }

    public void setSet_basis(String set_basis) {
        this.set_basis = set_basis;
    }

    public String getSet_basis_summary() {
        return set_basis_summary;
    }

    public void setSet_basis_summary(String set_basis_summary) {
        this.set_basis_summary = set_basis_summary;
    }
}
