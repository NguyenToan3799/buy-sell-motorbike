package buysellmoto.service;

import buysellmoto.dao.BuyRequestDao;
import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.model.mapper.BuyRequestMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BuyRequestService {

    @Autowired
    private BuyRequestDao buyRequestDao;
    @Autowired
    private BuyRequestMapper buyRequestMapper;

    public BuyRequestDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return buyRequestDao.getById(id);
    }
    
    public List<BuyRequestDto> getAll() {
        return buyRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public BuyRequestDto createOne (BuyRequestFilter filter) {
        BuyRequestDto preparingDto = buyRequestMapper.filterToDto(filter);
        return buyRequestDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public BuyRequestDto updateOne(Long id, BuyRequestFilter filter) {
        BuyRequestDto preparingDto = buyRequestMapper.filterToDto(filter);
        preparingDto.setId(id);
        return buyRequestDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        buyRequestDao.deleteById(id);
        return true;
    }

}
