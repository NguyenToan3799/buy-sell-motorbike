package buysellmoto.service;


import buysellmoto.dao.MotorbikeDao;
import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.filter.MotorbikeFilter;
import buysellmoto.model.mapper.MotorbikeMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MotorbikeService {

    @Autowired
    private MotorbikeDao motorbikeDao;
    @Autowired
    private MotorbikeMapper motorbikeMapper;

    public MotorbikeDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return motorbikeDao.getById(id);
    }
    
    public List<MotorbikeDto> getAll() {
        return motorbikeDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeDto createOne (MotorbikeFilter filter) {
        MotorbikeDto preparingDto = motorbikeMapper.filterToDto(filter);
        return motorbikeDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeDto updateOne(Long id, MotorbikeFilter filter) {
        MotorbikeDto preparingDto = motorbikeMapper.filterToDto(filter);
        preparingDto.setId(id);
        return motorbikeDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motorbikeDao.deleteById(id);
        return true;
    }

}
