package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.MotoBrandDao;
import buysellmoto.model.dto.MotoBrandDto;
import buysellmoto.model.filter.MotoBrandFilter;
import buysellmoto.model.mapper.MotoBrandMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MotoBrandService {

    @Autowired
    private MotoBrandDao motoBrandDao;
    @Autowired
    private MotoBrandMapper motoBrandMapper;

    public MotoBrandDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return motoBrandDao.getById(id);
    }
    
    public List<MotoBrandDto> getAll() {
        return motoBrandDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoBrandDto createOne (MotoBrandFilter filter) {
        MotoBrandDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return motoBrandDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoBrandDto updateOne(MotoBrandFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        MotoBrandDto preparingDto = filter.getCriteria();
        return motoBrandDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motoBrandDao.deleteById(id);
        return true;
    }

}