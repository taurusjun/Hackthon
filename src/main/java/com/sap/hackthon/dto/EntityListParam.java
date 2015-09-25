package com.sap.hackthon.dto;

import java.io.Serializable;

/**
 * 
 * 
 */
public class EntityListParam implements Serializable {

    private static final long serialVersionUID = -2896255157637535567L;
    private String objectType;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

}
