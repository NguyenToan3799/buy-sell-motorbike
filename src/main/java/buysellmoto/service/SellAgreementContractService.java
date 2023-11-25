package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
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
        SellAgreementContractDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return sellAgreementContractDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellAgreementContractDto updateOne(SellAgreementContractFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        SellAgreementContractDto preparingDto = filter.getCriteria();
        return sellAgreementContractDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        sellAgreementContractDao.deleteById(id);
        return true;
    }

}
