package buysellmoto.dao;

import buysellmoto.model.dto.MotoTypeDto;
import buysellmoto.model.mapper.MotoTypeMapper;
import buysellmoto.repository.MotoTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoTypeDao {

    @Autowired
    private MotoTypeRepository motoTypeRepository;

    @Autowired
    private MotoTypeMapper mapper;

    public MotoTypeDto getById(Long id) {
        return mapper.toDto(motoTypeRepository.findById(id).orElseThrow());
    }

    public List<MotoTypeDto> getAll() {
        return mapper.toDto(motoTypeRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoTypeDto createOne(MotoTypeDto dto) {
        return mapper.toDto(motoTypeRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoTypeDto updateOne(MotoTypeDto dto) {
        return mapper.toDto(motoTypeRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        motoTypeRepository.delete(motoTypeRepository.findById(id).orElseThrow());
        return true;
    }

}
