package com.sap.hackthon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;


public interface PropertyMetaRepository extends JpaRepository<PropertyMeta, Long> {
	
	List<PropertyMeta> findByTenantIdAndObjectName(String tenantId, String objectName);
	
	List<PropertyMeta> findByTenantIdAndObjectNameAndDisplayName(String tenantId, String objectName, String displayName);
	
	@Query("select ifnull(max(pm.paramIndex),0) from PropertyMeta pm where pm.tenantId = ?1 and pm.objectName = ?2 and pm.type = ?3")
	int findMaxParamIndexByTenantIdAndObjectNameAndType(String tenantId, String objectName, UDFTypeEnum type);
}
