package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.FluxoDePagamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FluxoDePagamentosRepository extends JpaRepository<FluxoDePagamentos, Integer> {

    @Query("SELECT f FROM FluxoDePagamentos f WHERE f.proposal.proposalId = :proposal_id")
    List<FluxoDePagamentos> findAllByProposal(@Param("proposal_id") Integer proposal_id);

}
