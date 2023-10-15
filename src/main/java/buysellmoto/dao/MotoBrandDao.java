package buysellmoto.dao;

import buysellmoto.model.dto.MotoBrandDto;
import buysellmoto.model.mapper.MotoBrandMapper;
import buysellmoto.repository.MotoBrandRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoBrandDao {

    @Autowired
    private MotoBrandRepository motoBrandRepository;

    @Autowired
    private MotoBrandMapper mapper;

    public MotoBrandDto getById(Long id) {
        return mapper.toDto(motoBrandRepository.findById(id).orElseThrow());
    }

    public List<MotoBrandDto> getAll() {
        return mapper.toDto(motoBrandRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoBrandDto createOne(MotoBrandDto dto) {
        return mapper.toDto(motoBrandRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public MotoBrandDto updateOne(MotoBrandDto dto) {
        return mapper.toDto(motoBrandRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        motoBrandRepository.delete(motoBrandRepository.findById(id).orElseThrow());
        return true;
    }

}
