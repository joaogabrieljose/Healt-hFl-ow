package jjose.dev.com.scheduling.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.scheduling.domain.entity.AppointmentStatusHistory;
import jjose.dev.com.scheduling.domain.repository.AppointmentRepository;
import jjose.dev.com.scheduling.domain.repository.AppointmentStatusHistoryRepository;
import jjose.dev.com.scheduling.dto.appointmentStatusHistoryDTO.AppointmentStatusHistoryDTO;

@Service
public class AppointmentStatusHistoryServiceImpl implements AppointmentStatusHistoryService {

    private final AppointmentStatusHistoryRepository statusHistoryRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentStatusHistoryServiceImpl(
            AppointmentStatusHistoryRepository statusHistoryRepository,
            AppointmentRepository appointmentRepository
    ) {
        this.statusHistoryRepository = statusHistoryRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentStatusHistoryDTO> getHistoryByAppointmentId(Long appointmentId) {

        if (!appointmentRepository.existsById(appointmentId)) {
            throw new RuntimeException("Consulta não encontrada com ID: " + appointmentId);
        }

        return statusHistoryRepository.findByAppointmentIdOrderByChangedAtDesc(appointmentId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public AppointmentStatusHistoryDTO getHistoryById(Long id) {
        AppointmentStatusHistory history = statusHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado com ID: " + id));

        return toDTO(history);
    }

    private AppointmentStatusHistoryDTO toDTO(AppointmentStatusHistory history) {
        return new AppointmentStatusHistoryDTO(
                history.getId(),
                history.getAppointment() != null ? history.getAppointment().getId() : null,
                history.getPreviousStatus(),
                history.getNewStatus(),
                history.getReason(),
                history.getChangedAt()
        );
    }
}