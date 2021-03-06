/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.facebookanalizapp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author ufuk
 */
@Entity
public class CosineEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column()
    private String vals;
    
    private Integer miningType;
    
    
    
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
        if (!(object instanceof CosineEntity)) {
            return false;
        }
        CosineEntity other = (CosineEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facebookanalizapp.entity.CosineEntity[ id=" + id + " ]";
    }

    /**
     * @return the values
     */
    public String getValues() {
        return vals;
    }

    /**
     * @param values the values to set
     */
    public void setValues(String values) {
        this.vals = values;
    }

    /**
     * @return the miningType
     */
    public Integer getMiningType() {
        return miningType;
    }

    /**
     * @param miningType the miningType to set
     */
    public void setMiningType(Integer miningType) {
        this.miningType = miningType;
    }
    
}
