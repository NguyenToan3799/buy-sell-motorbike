package buysellmoto.service;

import buysellmoto.dao.RejectRequestDao;
import buysellmoto.model.dto.RejectRequestDto;
import buysellmoto.model.filter.RejectRequestFilter;
import buysellmoto.model.mapper.RejectRequestMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RejectRequestService {

    @Autowired
    private RejectRequestDao rejectRequestDao;
    @Autowired
    private RejectRequestMapper rejectRequestMapper;

    public RejectRequestDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return rejectRequestDao.getById(id);
    }
    
    public List<RejectRequestDto> getAll() {
        return rejectRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public RejectRequestDto createOne (RejectRequestFilter filter) {
        RejectRequestDto preparingDto = rejectRequestMapper.filterToDto(filter);
        return rejectRequestDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public RejectRequestDto updateOne(Long id, RejectRequestFilter filter) {
        RejectRequestDto preparingDto = rejectRequestMapper.filterToDto(filter);
        preparingDto.setId(id);
        return rejectRequestDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        rejectRequestDao.deleteById(id);
        return true;
    }

}
