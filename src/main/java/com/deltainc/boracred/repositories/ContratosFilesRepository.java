package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.ContratosFiles;
import com.deltainc.boracred.entity.Files;
import com.deltainc.boracred.entity.Proposal;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContratosFilesRepository extends JpaRepository<ContratosFiles, Integer> {

    @Query(value = "SELECT * FROM ContratosFiles f WHERE f.proposal_proposal_id = :proposal", nativeQuery = true)
    List<Files> findByProposal(@Param("proposal") int proposal);

}

