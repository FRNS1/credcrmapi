package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.entity.SCR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SCRRepository extends JpaRepository<SCR, Integer> {

    SCR findByProposal(Proposal proposal);

}
