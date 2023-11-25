package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.PostDao;
import buysellmoto.model.dto.PostDto;
import buysellmoto.model.filter.PostFilter;
import buysellmoto.model.mapper.PostMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    @Autowired
    private PostDao postDao;
    @Autowired
    private PostMapper postMapper;

    public PostDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return postDao.getById(id);
    }
    
    public List<PostDto> getAll() {
        return postDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public PostDto createOne (PostFilter filter) {
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

}
