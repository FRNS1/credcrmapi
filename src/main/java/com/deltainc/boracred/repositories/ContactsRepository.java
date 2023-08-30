package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Contacts;
import com.deltainc.boracred.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Integer> {

    Contacts findByCustomer(Customer customer);

}
