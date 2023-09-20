package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Files;
import com.deltainc.boracred.entity.Proposal;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Integer> {

    Files findByProposal(Proposal proposal);

}
