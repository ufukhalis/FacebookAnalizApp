/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ufuk
 */
@Entity
public class PresentationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String presentationName;
    
    private Integer chartType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresentationEntity)) {
            return false;
        }
        PresentationEntity other = (PresentationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facebookanalizapp.entity.PresentationEntity[ id=" + id + " ]";
    }

    /**
     * @return the presentationName
     */
    public String getPresentationName() {
        return presentationName;
    }

    /**
     * @param presentationName the presentationName to set
     */
    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    /**
     * @return the chartType
     */
    public Integer getChartType() {
        return chartType;
    }

    /**
     * @param chartType the chartType to set
     */
    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }
    
}
