package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Address;
import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.business = :business AND c.created_by = :createdBy GROUP BY c.customer_id ORDER BY c.customer_id DESC")
    List<Customer> findByBusinessAndCreatedBy(@Param("business") String business, @Param("createdBy") Users createdBy);

    List<Customer> findByBusiness(String business);

    @Query("SELECT c FROM Customer c GROUP BY c.customer_id ORDER BY c.customer_id DESC")
    List<Customer> findAll();

    @Query("SELECT c FROM Customer c WHERE c.created_by = :createdBy GROUP BY c.customer_id ORDER BY c.customer_id DESC")
    List<Customer> findByCreatedBy(@Param("createdBy") Users createdBy);
}
