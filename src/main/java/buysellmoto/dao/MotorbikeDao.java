package buysellmoto.dao;

import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.mapper.MotorbikeMapper;
import buysellmoto.repository.MotorbikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorbikeDao {

    @Autowired
    private MotorbikeRepository motorbikeRepository;

    @Autowired
    private MotorbikeMapper mapper;

    public MotorbikeDto getById(Long id) {
        return mapper.toDto(motorbikeRepository.findById(id).orElseThrow());
    }

    public List<MotorbikeDto> getAll() {
        return mapper.toDto(motorbikeRepository.findAll());
    }

    public List<MotorbikeDto> getByIds(List<Long> ids){
        return mapper.toDto(motorbikeRepository.findByIdIn(ids));
    }

    public List<MotorbikeDto> getAllInShowroom(Long showroomId) {
        return mapper.toDto(motorbikeRepository.findByShowroomId(showroomId));
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeDto createOne(MotorbikeDto dto) {
        return mapper.toDto(motorbikeRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeDto updateOne(MotorbikeDto dto) {
        return mapper.toDto(motorbikeRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        motorbikeRepository.delete(motorbikeRepository.findById(id).orElseThrow());
        return true;
    }

}
