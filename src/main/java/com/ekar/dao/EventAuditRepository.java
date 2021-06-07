package com.ekar.dao;

import com.ekar.model.EventAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAuditRepository extends CrudRepository<EventAudit, Long> {

}
