package com.iit.event.ticketing.system.repository;

import com.iit.event.ticketing.system.core.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Customer Repository
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

  // No custom methods
}
