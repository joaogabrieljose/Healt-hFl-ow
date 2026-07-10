package jjose.dev.com.scheduling.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjose.dev.com.scheduling.domain.entity.AppointmentStatusHistory;

@Repository
public interface AppointmentStatusHistoryRepository extends JpaRepository<AppointmentStatusHistory, Long> {

    List<AppointmentStatusHistory> findByAppointmentId(Long appointmentId);

    List<AppointmentStatusHistory> findByAppointmentIdOrderByChangedAtDesc(Long appointmentId);
}