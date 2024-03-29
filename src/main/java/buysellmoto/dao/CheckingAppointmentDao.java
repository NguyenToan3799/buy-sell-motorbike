package buysellmoto.dao;

import buysellmoto.core.enumeration.CheckingAppointmentEnum;
import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.mapper.CheckingAppointmentMapper;
import buysellmoto.model.vo.CheckingAppointmentVo;
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

    public CheckingAppointmentVo getById(Long id) {
        return mapper.entityToVo(checkingAppointmentRepository.findById(id).orElseThrow());
    }

    public List<CheckingAppointmentVo> getAllActive() {
        return mapper.entityToVo(checkingAppointmentRepository.findAllByStatus(CheckingAppointmentEnum.ACTIVE.getCode()));
    }

    public List<CheckingAppointmentDto> getAll() {
        return mapper.toDto(checkingAppointmentRepository.findAll());
    }

    public List<CheckingAppointmentVo> getByShowroomId(Long showroomId) {
        return mapper.entityToVo(checkingAppointmentRepository.findAllByShowroomIdAndStatus(showroomId, CheckingAppointmentEnum.ACTIVE.getCode()));
    }

    public CheckingAppointmentDto getByBuyRequestId(Long buyRequestId) {
        return mapper.toDto(checkingAppointmentRepository.findByBuyRequestIdAndStatus(buyRequestId, CheckingAppointmentEnum.ACTIVE.getCode()).orElse(null));
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
