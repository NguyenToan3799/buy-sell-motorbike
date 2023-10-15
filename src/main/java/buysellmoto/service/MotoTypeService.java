package buysellmoto.service;


import buysellmoto.dao.MotoTypeDao;
import buysellmoto.model.dto.MotoTypeDto;
import buysellmoto.model.filter.MotoTypeFilter;
import buysellmoto.model.mapper.MotoTypeMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MotoTypeService {

    @Autowired
    private MotoTypeDao motoTypeDao;
    @Autowired
    private MotoTypeMapper motoTypeMapper;

    public MotoTypeDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return motoTypeDao.getById(id);
    }
    
    public List<MotoTypeDto> getAll() {
        return motoTypeDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoTypeDto createOne (MotoTypeFilter filter) {
        MotoTypeDto preparingDto = motoTypeMapper.filterToDto(filter);
        return motoTypeDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoTypeDto updateOne(Long id, MotoTypeFilter filter) {
        MotoTypeDto preparingDto = motoTypeMapper.filterToDto(filter);
        preparingDto.setId(id);
        return motoTypeDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        motoTypeDao.deleteById(id);
        return true;
    }

}
