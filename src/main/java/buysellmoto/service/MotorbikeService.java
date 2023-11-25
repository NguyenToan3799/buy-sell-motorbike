package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
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
        if (Objects.isNull(id)) {
        }
        return motorbikeDao.getById(id);
    }

    public List<MotorbikeDto> getAll() {
        return motorbikeDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeDto createOne(MotorbikeFilter filter) {
        MotorbikeDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return motorbikeDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotorbikeDto updateOne(MotorbikeFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        MotorbikeDto preparingDto = filter.getCriteria();
        return motorbikeDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motorbikeDao.deleteById(id);
        return true;
    }

}
