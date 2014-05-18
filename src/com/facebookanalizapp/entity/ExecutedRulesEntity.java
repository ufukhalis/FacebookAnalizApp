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
import javax.persistence.NamedQuery;

/**
 *
 * @author ufuk
 */
@Entity
@NamedQuery(name="ExecutedRulesEntity.findAll", query="SELECT e FROM ExecutedRulesEntity e") 
public class ExecutedRulesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private Long dataID;
    
    private Long miningID;
    
    private Long presentationID;
    
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
        if (!(object instanceof ExecutedRulesEntity)) {
            return false;
        }
        ExecutedRulesEntity other = (ExecutedRulesEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "com.facebookanalizapp.entity.ExecutedRulesEntity[ id=" + id + " ]";
        return this.getName();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dataID
     */
    public Long getDataID() {
        return dataID;
    }

    /**
     * @param dataID the dataID to set
     */
    public void setDataID(Long dataID) {
        this.dataID = dataID;
    }

    /**
     * @return the miningID
     */
    public Long getMiningID() {
        return miningID;
    }

    /**
     * @param miningID the miningID to set
     */
    public void setMiningID(Long miningID) {
        this.miningID = miningID;
    }

    /**
     * @return the presentationID
     */
    public Long getPresentationID() {
        return presentationID;
    }

    /**
     * @param presentationID the presentationID to set
     */
    public void setPresentationID(Long presentationID) {
        this.presentationID = presentationID;
    }
    
}
