package buysellmoto.service;

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
        PostDto preparingDto = postMapper.filterToDto(filter);
        return postDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public PostDto updateOne(Long id, PostFilter filter) {
        PostDto preparingDto = postMapper.filterToDto(filter);
        preparingDto.setId(id);
        return postDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        postDao.deleteById(id);
        return true;
    }

}
