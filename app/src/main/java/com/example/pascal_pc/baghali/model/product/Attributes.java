package com.example.pascal_pc.baghali.model.product;

import java.util.List;

public class Attributes {
    private String name;
    private List<String> options;
    int id;

    public Attributes(String name, List<String> options, int id) {
        this.name = name;
        this.options = options;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getOptions() {
        return options;
    }
}
