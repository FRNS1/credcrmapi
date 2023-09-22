package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, Integer> {
}
