package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.SocioPj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioPjRepository extends JpaRepository<SocioPj, Integer> {
    SocioPj findByCustomer(Customer customer);
}
