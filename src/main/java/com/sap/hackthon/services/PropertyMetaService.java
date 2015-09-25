package com.sap.hackthon.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;

public interface PropertyMetaService {

	public PropertyMeta get(Long id);

	public boolean create(PropertyMeta propertyMeta);

	public boolean delete(Long id);

	public boolean update(PropertyMeta propertyMeta);

	public List<PropertyMeta> getByTenantIdAndObjectName(String tenantId, String objectName);

	public int getMaxParamIndexByTenantIdAndObjectNameAndType(String tenantId, String objectName, UDFTypeEnum type);
	
	public boolean getByTenantIdAndObjectNameAndDisplayName(String tenantId, String objectName, String displayName);
}
