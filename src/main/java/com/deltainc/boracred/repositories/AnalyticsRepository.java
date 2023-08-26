package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Analytics;
import com.deltainc.boracred.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {

    Analytics findByProposal(Proposal proposal);

}
