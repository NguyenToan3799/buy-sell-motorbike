package buysellmoto.dao;

import buysellmoto.model.dto.ShowroomDto;
import buysellmoto.model.filter.ShowroomFilter;
import buysellmoto.model.mapper.ShowroomMapper;
import buysellmoto.model.vo.ShowroomProjection;
import buysellmoto.model.vo.ShowroomVo;
import buysellmoto.repository.ShowroomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowroomDao {

    @Autowired
    private ShowroomRepository showroomRepository;

    @Autowired
    private ShowroomMapper mapper;

    public ShowroomDto getById(Long id) {
        return mapper.toDto(showroomRepository.findById(id).orElse(null));
    }

    public List<ShowroomDto> getByIds(List<Long> ids) {
        return mapper.toDto(showroomRepository.findAllByIdIn(ids));
    }

    public List<ShowroomDto> getAll() {
        return mapper.toDto(showroomRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomDto createOne(ShowroomDto dto) {
        return mapper.toDto(showroomRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomDto updateOne(ShowroomDto dto) {
        return mapper.toDto(showroomRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        showroomRepository.delete(showroomRepository.findById(id).orElseThrow());
        return true;
    }

    public Page<ShowroomProjection> getPaging(ShowroomFilter filter) {
        filter.beautify();
        return showroomRepository.getPaging(filter.getPageable(),
                filter.getSearchValue(),
                filter.getProvince());
    }

}
