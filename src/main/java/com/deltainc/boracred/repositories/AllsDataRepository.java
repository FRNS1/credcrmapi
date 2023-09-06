package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.AllsData;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.entity.SCR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllsDataRepository extends JpaRepository<AllsData, Integer> {

    AllsData findByProposal(Proposal proposal);

}
