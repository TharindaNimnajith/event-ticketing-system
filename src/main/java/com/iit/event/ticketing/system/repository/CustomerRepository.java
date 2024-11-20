package com.iit.event.ticketing.system.repository;

import com.iit.event.ticketing.system.core.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Customer Repository
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
