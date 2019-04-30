package com.example.springtest.domain;

import javax.persistence.*;

public class Province {
    @Column(name = "prov_id")
    private Integer provId;

    @Column(name = "prov_name")
    private String provName;

    private String oreintation;

    /**
     * @return prov_id
     */
    public Integer getProvId() {
        return provId;
    }

    /**
     * @param provId
     */
    public void setProvId(Integer provId) {
        this.provId = provId;
    }

    /**
     * @return prov_name
     */
    public String getProvName() {
        return provName;
    }

    /**
     * @param provName
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }

    /**
     * @return oreintation
     */
    public String getOreintation() {
        return oreintation;
    }

    /**
     * @param oreintation
     */
    public void setOreintation(String oreintation) {
        this.oreintation = oreintation;
    }
}