package buysellmoto.service;

import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.*;
import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.dto.PostDto;
import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.PostFilter;
import buysellmoto.model.mapper.PostMapper;
import buysellmoto.model.vo.PostProjection;
import buysellmoto.model.vo.PostVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    @Autowired
    private PostDao postDao;
    @Autowired
    private ShowroomDao showroomDao;
    @Autowired
    private SellRequestDao sellRequestDao;
    @Autowired
    private MotorbikeDao motorbikeDao;

    @Autowired
    private PostMapper postMapper;

    public PostVo getById(Long id) {
        PostVo postVo = postMapper.dtoToVo(postDao.getById(id));
        postVo.setMotorbikeDto(motorbikeDao.getById(postVo.getMotorbikeId()));
        postVo.setSellRequestDto(sellRequestDao.getById(postVo.getSellRequestId()));
        postVo.setShowroomDto(showroomDao.getById(postVo.getShowroomId()));
        return postVo;
    }
    
    public List<PostDto> getAll() {
        return postDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public PostDto createOne (PostFilter filter) {
        if (Objects.isNull(filter.getCriteria().getSellRequestId())) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_ID_REQUIRED);
        }
        SellRequestDto sellRequestDto = sellRequestDao.getById(filter.getCriteria().getSellRequestId());
        sellRequestDto.setStatus(SellRequestEnum.POSTED.getCode());
        sellRequestDao.updateOne(sellRequestDto);

        PostDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return postDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public PostDto updateOne(PostFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        PostDto preparingDto = filter.getCriteria();
        return postDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        postDao.deleteById(id);
        return true;
    }

    public Page<PostProjection> getPaging(PostFilter postFilter) {
        return postDao.getPaging(postFilter);
    }

    public List<PostProjection> getPostByShowroomId(Long showroomId) {
        return postDao.getPostByShowroomId(showroomId);
    }

}
