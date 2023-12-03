package buysellmoto.dao;

import buysellmoto.model.dto.ShowroomImageDto;
import buysellmoto.model.mapper.ShowroomImageMapper;
import buysellmoto.repository.ShowroomImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowroomImageDao {

    @Autowired
    private ShowroomImageRepository showroomImageRepository;

    @Autowired
    private ShowroomImageMapper mapper;

    public ShowroomImageDto getById(Long id) {
        return mapper.toDto(showroomImageRepository.findById(id).orElseThrow());
    }

    public List<ShowroomImageDto> getByShowroomId(Long showroomId) {
        return mapper.toDto(showroomImageRepository.findAllByShowroomId(showroomId));
    }

    public List<ShowroomImageDto> getAll() {
        return mapper.toDto(showroomImageRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomImageDto createOne(ShowroomImageDto dto) {
        return mapper.toDto(showroomImageRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomImageDto updateOne(ShowroomImageDto dto) {
        return mapper.toDto(showroomImageRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        showroomImageRepository.delete(showroomImageRepository.findById(id).orElseThrow());
        return true;
    }

}
