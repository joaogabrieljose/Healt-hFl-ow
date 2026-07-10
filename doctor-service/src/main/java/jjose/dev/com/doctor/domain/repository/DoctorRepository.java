package jjose.dev.com.doctor.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjose.dev.com.doctor.domain.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    Optional<Doctor> findByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByEmail(String email);

    List<Doctor> findBySpecialtyId(Long specialtyId);

    List<Doctor> findByStatus(String status);
}