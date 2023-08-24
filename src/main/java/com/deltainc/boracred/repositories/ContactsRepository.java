package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Integer> {

}
