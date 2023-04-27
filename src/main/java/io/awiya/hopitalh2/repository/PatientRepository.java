package io.awiya.hopitalh2.repository;

import io.awiya.hopitalh2.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
