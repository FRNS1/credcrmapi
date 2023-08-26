package com.deltainc.boracred.repositories;

import com.deltainc.boracred.entity.Files;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Files, Integer> {
}
