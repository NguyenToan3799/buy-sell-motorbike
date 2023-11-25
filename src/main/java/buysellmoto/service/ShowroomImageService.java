package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.ShowroomImageDao;
import buysellmoto.model.dto.ShowroomImageDto;
import buysellmoto.model.filter.ShowroomImageFilter;
import buysellmoto.model.mapper.ShowroomImageMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShowroomImageService {

    @Autowired
    private ShowroomImageDao showroomImageDao;
    @Autowired
    private ShowroomImageMapper showroomImageMapper;

    public ShowroomImageDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return showroomImageDao.getById(id);
    }
    
    public List<ShowroomImageDto> getAll() {
        return showroomImageDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomImageDto createOne (ShowroomImageFilter filter) {
        ShowroomImageDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return showroomImageDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public ShowroomImageDto updateOne(ShowroomImageFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        ShowroomImageDto preparingDto = filter.getCriteria();
        return showroomImageDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        showroomImageDao.deleteById(id);
        return true;
    }

}
