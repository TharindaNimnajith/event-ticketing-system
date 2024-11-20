package com.iit.event.ticketing.system.repository;

import com.iit.event.ticketing.system.core.model.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Vendor Repository
 */
public interface VendorRepository extends JpaRepository<Vendor, String> {

}
