package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Referencia;
import com.deltainc.boracred.entity.SocioPj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenciaRepository extends JpaRepository<Referencia, Integer> {
    Referencia findByCustomer(Customer customer);
}
