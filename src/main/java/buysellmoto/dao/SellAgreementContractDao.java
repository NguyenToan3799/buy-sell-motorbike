package buysellmoto.dao;

import buysellmoto.model.dto.SellAgreementContractDto;
import buysellmoto.model.mapper.SellAgreementContractMapper;
import buysellmoto.repository.SellAgreementContractRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellAgreementContractDao {

    @Autowired
    private SellAgreementContractRepository sellAgreementContractRepository;

    @Autowired
    private SellAgreementContractMapper mapper;

    public SellAgreementContractDto getById(Long id) {
        return mapper.toDto(sellAgreementContractRepository.findById(id).orElseThrow());
    }

    public List<SellAgreementContractDto> getAll() {
        return mapper.toDto(sellAgreementContractRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellAgreementContractDto createOne(SellAgreementContractDto dto) {
        return mapper.toDto(sellAgreementContractRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellAgreementContractDto updateOne(SellAgreementContractDto dto) {
        return mapper.toDto(sellAgreementContractRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        sellAgreementContractRepository.delete(sellAgreementContractRepository.findById(id).orElseThrow());
        return true;
    }

}
