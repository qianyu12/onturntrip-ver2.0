package com.example.springtest.domain;

import javax.persistence.*;

public class Province {
    @Id
    private Integer id;

    private String name;

    private String oriention;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return oriention
     */
    public String getOriention() {
        return oriention;
    }

    /**
     * @param oriention
     */
    public void setOriention(String oriention) {
        this.oriention = oriention;
    }
}