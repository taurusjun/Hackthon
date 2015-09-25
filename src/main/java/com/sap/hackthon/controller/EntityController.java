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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.hackthon.dto.EntityListParam;
import com.sap.hackthon.entity.DynamicEntity;
import com.sap.hackthon.services.EntityService;
import com.sap.hackthon.utils.GlobalConstants;

@Controller
public class EntityController {

    @Autowired
    private EntityService service;

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public @ResponseBody DynamicEntity create(@RequestBody DynamicEntity entity, HttpServletRequest request) {
        if (entity == null || entity.getObjectType() == null) {
            return null;
        }
        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        return service.create(entity, tenantId);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    public @ResponseBody DynamicEntity update(@RequestBody DynamicEntity entity, HttpServletRequest request) {
        if (entity == null || entity.getObjectType() == null) {
            return null;
        }
        if (entity.getProperty("id") == null) {
            return null;
        }

        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        return service.update(entity, tenantId);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public @ResponseBody boolean delete(@RequestBody String objectType, @PathVariable("id") Long entityId) {
        if (objectType == null || entityId == null) {
            return false;
        }
        return service.delete(entityId, objectType);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public @ResponseBody DynamicEntity get(@RequestBody String objectType, @PathVariable("id") Long entityId,
            HttpServletRequest request) {
        if (objectType == null || entityId == null) {
            return null;
        }
        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        return service.get(entityId, tenantId, objectType);
    }

    @RequestMapping(value = "/entities", method = RequestMethod.POST)
    public @ResponseBody List<DynamicEntity> list(@RequestBody EntityListParam param, HttpServletRequest request) {
        if (param.getObjectType() == null) {
            return null;
        }
        HttpSession session = request.getSession();
        String tenantId = (String) session.getAttribute(GlobalConstants.TENANT);
        if (tenantId == null) {
            return null;
        }
        return service.list(param.getObjectType(), tenantId);
    }

    @RequestMapping(value = "/test/cr", method = RequestMethod.GET)
    public String cr() {
        service.create(null, null);
        return "home";
    }

    @RequestMapping(value = "/test/yu", method = RequestMethod.GET)
    public String gt() {
        DynamicEntity entity = new DynamicEntity("T_ORDER");
        entity.setProperty("ORDER_ID", "orderid10092914");
        entity.setProperty("PRICE_UDF", "hh");
        service.create(entity, "TN001");

        DynamicEntity subEntity = new DynamicEntity("T_ORDER_LINE");
        subEntity.setProperty("ORDER_ID", "orderid10092914");
        subEntity.setProperty("ada", "hh");
        service.create(subEntity, "TN001");

        service.get((Long) entity.getProperty("ID"), "TN001", "T_ORDER");
        return "home";
    }

    @RequestMapping(value = "/test/gt", method = RequestMethod.GET)
    public String qr() {
        DynamicEntity entity = new DynamicEntity("T_ORDER");
        entity.setProperty("ORDER_ID", "orderid1009291");
        entity.setProperty("PRICE_UDF", "aa");
        service.update(entity, "TN001");
        service.list("T_ORDER", "Tenant004");
        return "home";
    }

}
