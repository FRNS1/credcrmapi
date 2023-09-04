package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Address;
import com.deltainc.boracred.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findByCustomer(Customer customer);

}
