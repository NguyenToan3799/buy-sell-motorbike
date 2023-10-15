package buysellmoto.dao;

import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.mapper.CheckingAppointmentMapper;
import buysellmoto.repository.CheckingAppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckingAppointmentDao {

    @Autowired
    private CheckingAppointmentRepository checkingAppointmentRepository;

    @Autowired
    private CheckingAppointmentMapper mapper;

    public CheckingAppointmentDto getById(Long id) {
        return mapper.toDto(checkingAppointmentRepository.findById(id).orElseThrow());
    }

    public List<CheckingAppointmentDto> getAll() {
        return mapper.toDto(checkingAppointmentRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckingAppointmentDto createOne(CheckingAppointmentDto dto) {
        return mapper.toDto(checkingAppointmentRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckingAppointmentDto updateOne(CheckingAppointmentDto dto) {
        return mapper.toDto(checkingAppointmentRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        checkingAppointmentRepository.delete(checkingAppointmentRepository.findById(id).orElseThrow());
        return true;
    }

}
