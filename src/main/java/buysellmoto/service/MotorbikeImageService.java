package buysellmoto.service;

import buysellmoto.dao.MotorbikeImageDao;
import buysellmoto.model.dto.MotorbikeImageDto;
import buysellmoto.model.filter.MotorbikeImageFilter;
import buysellmoto.model.mapper.MotorbikeImageMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MotorbikeImageService {

    @Autowired
    private MotorbikeImageDao motorbikeImageDao;
    @Autowired
    private MotorbikeImageMapper motorbikeImageMapper;

    public MotorbikeImageDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return motorbikeImageDao.getById(id);
    }
    
    public List<MotorbikeImageDto> getAll() {
        return motorbikeImageDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeImageDto createOne (MotorbikeImageFilter filter) {
        MotorbikeImageDto preparingDto = motorbikeImageMapper.filterToDto(filter);
        return motorbikeImageDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeImageDto updateOne(Long id, MotorbikeImageFilter filter) {
        MotorbikeImageDto preparingDto = motorbikeImageMapper.filterToDto(filter);
        preparingDto.setId(id);
        return motorbikeImageDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motorbikeImageDao.deleteById(id);
        return true;
    }

}
