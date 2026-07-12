package jjose.dev.com.triage.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjose.dev.com.triage.domain.entity.TriageVitalSigns;

@Repository
public interface TriageVitalSignsRepository extends JpaRepository<TriageVitalSigns, Long> {

    Optional<TriageVitalSigns> findByTriageId(Long triageId);

    boolean existsByTriageId(Long triageId);
}