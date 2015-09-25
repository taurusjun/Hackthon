/**
 * 
 */
package com.sap.hackthon.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author I310717
 *
 */
public class DynamicEntity {

    private Map<String, Object> propertities;

    private String objectType;

    public DynamicEntity() {
        super();
    }

    public DynamicEntity(String objectType) {
        this.objectType = objectType;
    }

    public DynamicEntity(String objectType, Map<String, Object> propertities) {
        this(objectType);
        this.propertities = propertities;
    }

    public Object getProperty(String name) {
        if (propertities == null || propertities.size() == 0) {
            return null;
        }
        return propertities.get(name);
    }

    public void setProperty(String name, Object value) {
        if (propertities == null) {
            propertities = new HashMap<String, Object>();
        }
        propertities.put(name, value);
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Map<String, Object> getPropertities() {
        return propertities;
    }

    public void setPropertities(Map<String, Object> propertities) {
        this.propertities = propertities;
    }

}
