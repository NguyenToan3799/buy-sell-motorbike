package buysellmoto.service;


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
        MotoBrandDto preparingDto = motoBrandMapper.filterToDto(filter);
        return motoBrandDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoBrandDto updateOne(Long id, MotoBrandFilter filter) {
        MotoBrandDto preparingDto = motoBrandMapper.filterToDto(filter);
        preparingDto.setId(id);
        return motoBrandDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motoBrandDao.deleteById(id);
        return true;
    }

}
