package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

    List<Proposal> findByCustomer(Customer customer);

}
