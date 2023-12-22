package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
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
        MotorbikeImageDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return motorbikeImageDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeImageDto updateOne(MotorbikeImageFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        MotorbikeImageDto preparingDto = filter.getCriteria();
        return motorbikeImageDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean updateAll(Long motorbikeId, MotorbikeImageFilter filter) {
        if (Objects.isNull(filter.getMotorbikeImageDtos())) {
            throw new BusinessException(ApiMessageCode.DATA_REQUIRED);
        }
        motorbikeImageDao.deleteByMotorbikeId(motorbikeId);
        filter.getMotorbikeImageDtos().forEach(motorbikeImageDto -> {
            motorbikeImageDto.setMotorbikeId(motorbikeId);
            motorbikeImageDto.setId(null);
        });
        motorbikeImageDao.createAll(filter.getMotorbikeImageDtos());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motorbikeImageDao.deleteById(id);
        return true;
    }

}
