package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

    List<Proposal> findByCustomer(Customer customer);

    @Query("SELECT p FROM Proposal p WHERE p.status = 'EMPRESTIMO CONCEDIDO'")
    List<Proposal> getAllLoans();

}
