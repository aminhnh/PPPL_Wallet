package org.example;

public class Cash {
    Integer nominal;
    Integer banyakLembar;

    public Cash(Integer nominal, Integer banyakLembar) {
        this.nominal = nominal;
        this.banyakLembar = banyakLembar;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public Integer getBanyakLembar() {
        return banyakLembar;
    }

    public void setBanyakLembar(Integer banyakLembar) {
        this.banyakLembar = banyakLembar;
    }
}
