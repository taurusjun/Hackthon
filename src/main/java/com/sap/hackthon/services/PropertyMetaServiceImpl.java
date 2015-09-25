package com.sap.hackthon.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;
import com.sap.hackthon.repository.PropertyMetaRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class PropertyMetaServiceImpl implements PropertyMetaService {

	@Autowired
	PropertyMetaRepository propertyMetaRepository;

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean create(PropertyMeta propertyMeta) {
		propertyMetaRepository.saveAndFlush(propertyMeta);
		jdbcTemplate.execute(this.addColumn(propertyMeta.getObjectName(), propertyMeta.getInternalName(), propertyMeta.getType()));
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getTenantId(), propertyMeta.getObjectName()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getTenantId(), propertyMeta.getObjectName()));
		return true;
	}
	
	// drop view T_ORDER_TENANT1005_VIEW
	private String dropView(String tenantId, String objectName) {
		StringBuffer dropView = new StringBuffer();
		dropView
		.append("drop view ")
		.append(objectName)
		.append("_")
		.append(tenantId)
		.append("_")
		.append("VIEW");
		return dropView.toString();
	}

	// create view T_ORDER_TENANT1005_VIEW as select order_id from T_ORDER
	private String createView(String tenantId, String objectName) {
		List<PropertyMeta> propertiesMeta = propertyMetaRepository.findByTenantIdAndObjectName(tenantId, objectName);
		StringBuffer createView = new StringBuffer();
		createView.append("create view ").append(objectName).append("_").append(tenantId).append("_").append("VIEW as select ");
		for(PropertyMeta propertyMeta : propertiesMeta) {
			createView.append(" ").append(propertyMeta.getInternalName()).append(" as ").append(propertyMeta.getDisplayName()).append(", ");
		}
		createView.deleteCharAt(createView.lastIndexOf(","));
		createView.append(" from ").append(objectName);
		return createView.toString();
	}
	
	private String addColumn(String objectName, String internalName, UDFTypeEnum type) {
		StringBuffer alterTableAddColumn = new StringBuffer();
		alterTableAddColumn
		.append("alter table ")
		.append(objectName)
		.append(" add(")
		.append(internalName)
		.append(" ")
		.append(type.equals(UDFTypeEnum.NVARCHAR) ? type + "(200)" : type)
		.append(")");
		return alterTableAddColumn.toString();
	}

	@Override
	public boolean delete(Long id) {
		PropertyMeta propertyMeta = propertyMetaRepository.findOne(id);
		jdbcTemplate.execute(this.dropColumn(propertyMeta));
		propertyMetaRepository.delete(propertyMeta);
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getTenantId(), propertyMeta.getObjectName()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getTenantId(), propertyMeta.getObjectName()));
		return true;
	}
	
	private String dropColumn(PropertyMeta propertyMeta) {
		StringBuffer alterTableDropColumn = new StringBuffer();
		alterTableDropColumn
		.append("alter table ")
		.append(propertyMeta.getObjectName())
		.append(" drop(")
		.append(propertyMeta.getInternalName())
		.append(" ")
		.append(")");
		return alterTableDropColumn.toString();
	}

	@Override
	public boolean update(PropertyMeta propertyMeta) {
		propertyMetaRepository.saveAndFlush(propertyMeta);
		try{
			jdbcTemplate.execute(this.dropView(propertyMeta.getTenantId(), propertyMeta.getObjectName()));
		}
		catch(Exception e) {
		}
		jdbcTemplate.execute(this.createView(propertyMeta.getTenantId(), propertyMeta.getObjectName()));
		return true;
	}

	@Override
	public List<PropertyMeta> getByTenantIdAndObjectName(String tenantId,
			String objectName) {
		return propertyMetaRepository.findByTenantIdAndObjectName(tenantId, objectName);
	}

	@Override
	public PropertyMeta get(Long id) {
		return propertyMetaRepository.findOne(id);
	}

	@Override
	public int getMaxParamIndexByTenantIdAndObjectNameAndType(String tenantId,
			String objectName, UDFTypeEnum type) {
		return propertyMetaRepository.findMaxParamIndexByTenantIdAndObjectNameAndType(tenantId, objectName, type);
	}

	@Override
	public boolean getByTenantIdAndObjectNameAndDisplayName(
			String tenantId, String objectName, String displayName) {
		List<PropertyMeta> propertiesMeta = propertyMetaRepository.findByTenantIdAndObjectNameAndDisplayName(tenantId, objectName, displayName);
		
		if(propertiesMeta != null && propertiesMeta.size() > 0) {
			return true;
		}
		return false;
	}
}
