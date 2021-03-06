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
@NamedQuery(name="MiningEntity.findAll", query="SELECT m FROM MiningEntity m")
public class MiningEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private Long cosineID;
    
    private Long clusteringID;
    
    private Integer k;
    
    private Integer loop;
    
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
        if (!(object instanceof MiningEntity)) {
            return false;
        }
        MiningEntity other = (MiningEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "com.facebookanalizapp.entity.MiningEntity[ id=" + id + " ]";
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
     * @return the cosineID
     */
    public Long getCosineID() {
        return cosineID;
    }

    /**
     * @param cosineID the cosineID to set
     */
    public void setCosineID(Long cosineID) {
        this.cosineID = cosineID;
    }

    /**
     * @return the clusteringID
     */
    public Long getClusteringID() {
        return clusteringID;
    }

    /**
     * @param clusteringID the clusteringID to set
     */
    public void setClusteringID(Long clusteringID) {
        this.clusteringID = clusteringID;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Integer getK() {
        return k;
    }

    public void setLoop(Integer loop) {
        this.loop = loop;
    }

    public Integer getLoop() {
        return loop;
    }
    
    
    
}
