package com.sap.hackthon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.services.PropertyMetaService;
import com.sap.hackthon.utils.GlobalConstants;

@Controller
@RequestMapping("/propertiesMeta")
public class PropertyMetaController {

	@Autowired
	private PropertyMetaService service;

	@RequestMapping(value = "/getByTenantIdAndObjectName", method = RequestMethod.POST)
	public @ResponseBody List<PropertyMeta> getByTenantIdAndObjectName(
			@RequestParam String objectName, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
		if (tenantId == null) {
			return null;
		}
		return service.getByTenantIdAndObjectName(tenantId, objectName);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody boolean create(@RequestBody PropertyMeta propertyMeta,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
		if (tenantId == null) {
			return false;
		}
		if(service.getByTenantIdAndObjectNameAndDisplayName(tenantId, propertyMeta.getObjectName(), propertyMeta.getDisplayName())) {
			return false;
		}
		if (propertyMeta != null) {
			propertyMeta.setTenantId(tenantId);
			int nextParamIndex = service.getMaxParamIndexByTenantIdAndObjectNameAndType(tenantId, propertyMeta.getObjectName(), propertyMeta.getType()) + 1;
			String internalName = GlobalConstants.UDF + "_" + propertyMeta.getTenantId() + "_" + propertyMeta.getType() + "_" + nextParamIndex;
			propertyMeta.setParamIndex(nextParamIndex);
			propertyMeta.setInternalName(internalName);
			return service.create(propertyMeta);
		}
		return false;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody boolean update(@RequestBody PropertyMeta propertyMeta,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
		if (tenantId == null) {
			return false;
		}
		if(service.getByTenantIdAndObjectNameAndDisplayName(tenantId, propertyMeta.getObjectName(), propertyMeta.getDisplayName())) {
			return false;
		}
		if (propertyMeta != null) {
			return service.update(propertyMeta);
		}
		return false;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody PropertyMeta get(@PathVariable("id") Long id) {
		if (id == null) {
			return null;
		}
		return service.get(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody boolean delete(@PathVariable("id") Long id,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
		if (tenantId == null) {
			return false;
		}
		if (id == null) {
			return false;
		}
		return service.delete(id);
	}

}
