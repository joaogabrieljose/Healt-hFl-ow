package main.java.jjose.dev.com.doctor.domain.service;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.doctor.domain.entity.Doctor;
import jjose.dev.com.doctor.domain.entity.DoctorSchedule;
import jjose.dev.com.doctor.domain.service.DoctorScheduleService;
import jjose.dev.com.doctor.dto.doctorScheduleDTO.DoctorScheduleDTO;
import jjose.dev.com.doctor.repository.DoctorRepository;
import jjose.dev.com.doctor.repository.DoctorScheduleRepository;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;

    public DoctorScheduleServiceImpl(
            DoctorScheduleRepository doctorScheduleRepository,
            DoctorRepository doctorRepository
    ) {
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorScheduleDTO createSchedule(Long doctorId, DoctorScheduleDTO dto) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com ID: " + doctorId));

        DoctorSchedule schedule = toEntity(dto);
        schedule.setDoctor(doctor);

        DoctorSchedule savedSchedule = doctorScheduleRepository.save(schedule);

        return toDTO(savedSchedule);
    }

    @Override
    public List<DoctorScheduleDTO> getSchedulesByDoctorId(Long doctorId) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new RuntimeException("Médico não encontrado com ID: " + doctorId);
        }

        return doctorScheduleRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<DoctorScheduleDTO> getAvailableSchedulesByDoctorId(Long doctorId) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new RuntimeException("Médico não encontrado com ID: " + doctorId);
        }

        return doctorScheduleRepository.findByDoctorIdAndAvailableTrue(doctorId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<DoctorScheduleDTO> getSchedulesByDoctorAndDay(Long doctorId, DayOfWeek dayOfWeek) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new RuntimeException("Médico não encontrado com ID: " + doctorId);
        }

        return doctorScheduleRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public DoctorScheduleDTO updateSchedule(Long id, DoctorScheduleDTO dto) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado com ID: " + id));

        schedule.setDayOfWeek(dto.dayOfWeek());
        schedule.setStartTime(dto.startTime());
        schedule.setEndTime(dto.endTime());
        schedule.setAvailable(dto.available());

        DoctorSchedule updatedSchedule = doctorScheduleRepository.save(schedule);

        return toDTO(updatedSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado com ID: " + id));

        doctorScheduleRepository.delete(schedule);
    }

    private DoctorSchedule toEntity(DoctorScheduleDTO dto) {
        DoctorSchedule schedule = new DoctorSchedule();

        schedule.setDayOfWeek(dto.dayOfWeek());
        schedule.setStartTime(dto.startTime());
        schedule.setEndTime(dto.endTime());
        schedule.setAvailable(dto.available());

        return schedule;
    }

    private DoctorScheduleDTO toDTO(DoctorSchedule schedule) {
        return new DoctorScheduleDTO(
                schedule.getId(),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getAvailable()
        );
    }
}