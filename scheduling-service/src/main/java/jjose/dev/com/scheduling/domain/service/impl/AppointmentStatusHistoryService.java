package jjose.dev.com.scheduling.domain.service.impl;

import java.util.List;

import jjose.dev.com.scheduling.dto.appointmentStatusHistoryDTO.AppointmentStatusHistoryDTO;

public interface AppointmentStatusHistoryService {

    List<AppointmentStatusHistoryDTO> getHistoryByAppointmentId(Long appointmentId);

    AppointmentStatusHistoryDTO getHistoryById(Long id);
}