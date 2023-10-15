package buysellmoto.service;

import buysellmoto.dao.SellRequestDao;
import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.model.mapper.SellRequestMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SellRequestService {

    @Autowired
    private SellRequestDao sellRequestDao;
    @Autowired
    private SellRequestMapper sellRequestMapper;

    public SellRequestDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return sellRequestDao.getById(id);
    }
    
    public List<SellRequestDto> getAll() {
        return sellRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto createOne (SellRequestFilter filter) {
        SellRequestDto preparingDto = sellRequestMapper.filterToDto(filter);
        return sellRequestDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto updateOne(Long id, SellRequestFilter filter) {
        SellRequestDto preparingDto = sellRequestMapper.filterToDto(filter);
        preparingDto.setId(id);
        return sellRequestDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        sellRequestDao.deleteById(id);
        return true;
    }

}
