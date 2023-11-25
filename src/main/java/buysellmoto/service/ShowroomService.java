package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.ShowroomDao;
import buysellmoto.model.dto.ShowroomDto;
import buysellmoto.model.filter.ShowroomFilter;
import buysellmoto.model.mapper.ShowroomMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShowroomService {

    @Autowired
    private ShowroomDao showroomDao;
    @Autowired
    private ShowroomMapper customerMapper;

    public ShowroomDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return showroomDao.getById(id);
    }
    
    public List<ShowroomDto> getAll() {
        return showroomDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomDto createOne (ShowroomFilter filter) {
        ShowroomDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return showroomDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomDto updateOne(ShowroomFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        ShowroomDto preparingDto = filter.getCriteria();
        return showroomDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        showroomDao.deleteById(id);
        return true;
    }

}
