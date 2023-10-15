package buysellmoto.service;

import buysellmoto.dao.SellAgreementContractDao;
import buysellmoto.model.dto.SellAgreementContractDto;
import buysellmoto.model.filter.SellAgreementContractFilter;
import buysellmoto.model.mapper.SellAgreementContractMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SellAgreementContractService {

    @Autowired
    private SellAgreementContractDao sellAgreementContractDao;
    @Autowired
    private SellAgreementContractMapper sellAgreementContractMapper;

    public SellAgreementContractDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return sellAgreementContractDao.getById(id);
    }
    
    public List<SellAgreementContractDto> getAll() {
        return sellAgreementContractDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellAgreementContractDto createOne (SellAgreementContractFilter filter) {
        SellAgreementContractDto preparingDto = sellAgreementContractMapper.filterToDto(filter);
        return sellAgreementContractDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellAgreementContractDto updateOne(Long id, SellAgreementContractFilter filter) {
        SellAgreementContractDto preparingDto = sellAgreementContractMapper.filterToDto(filter);
        preparingDto.setId(id);
        return sellAgreementContractDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        sellAgreementContractDao.deleteById(id);
        return true;
    }

}
