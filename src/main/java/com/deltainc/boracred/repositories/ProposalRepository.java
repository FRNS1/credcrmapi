package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
}
