package com.sap.hackthon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sap.hackthon.entity.Staff;

/**
 * 
 * @author 
 *
 */
@RepositoryRestResource
public interface StaffRepository extends CrudRepository<Staff, Long> {
}
