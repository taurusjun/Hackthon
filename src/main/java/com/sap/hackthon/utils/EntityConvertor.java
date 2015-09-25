/**
 * 
 */
package com.sap.hackthon.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.util.NumberUtils;

import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;

/**
 * @author I310717
 *
 */
public class EntityConvertor {

    private EntityConvertor() {

    }

    public synchronized static EntityConvertor getInstance() {
        return new EntityConvertor();
    }

    public DynamicEntity convertEntity(DynamicEntity rawEntity, List<PropertyMeta> entityMeta) {
        for (PropertyMeta proMeta : entityMeta) {
            String proName = proMeta.getInternalName();
            Object transValue = convertProperty(proMeta, rawEntity.getProperty(proName));
            rawEntity.setProperty(proName, transValue);
        }
        return rawEntity;
    }

    private Object convertProperty(PropertyMeta proMeta, Object value) {
        if (proMeta == null || value == null) {
            return null;
        }
        UDFTypeEnum typeEnum = proMeta.getType();
        switch (typeEnum) {
        case DECIMAL:
            return decimalValue(value);
        case TIMESTAMP:
            return timestampValue(value);
        default:
            return value;
        }
    }

    private BigDecimal decimalValue(Object value) {
        String numStr = value.toString();
        BigDecimal decimal = null;
        try {
            NumberUtils.parseNumber(numStr, BigDecimal.class);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return decimal;
    }

    private Timestamp timestampValue(Object value) {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(value.toString());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return ts;
    }
}
