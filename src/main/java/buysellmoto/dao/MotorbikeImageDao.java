package buysellmoto.dao;

import buysellmoto.model.dto.MotorbikeImageDto;
import buysellmoto.model.mapper.MotorbikeImageMapper;
import buysellmoto.repository.MotorbikeImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorbikeImageDao {

    @Autowired
    private MotorbikeImageRepository motorbikeImageRepository;

    @Autowired
    private MotorbikeImageMapper mapper;

    public MotorbikeImageDto getById(Long id) {
        return mapper.toDto(motorbikeImageRepository.findById(id).orElseThrow());
    }

    public List<MotorbikeImageDto> getAll() {
        return mapper.toDto(motorbikeImageRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeImageDto createOne(MotorbikeImageDto dto) {
        return mapper.toDto(motorbikeImageRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeImageDto updateOne(MotorbikeImageDto dto) {
        return mapper.toDto(motorbikeImageRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        motorbikeImageRepository.delete(motorbikeImageRepository.findById(id).orElseThrow());
        return true;
    }

}
