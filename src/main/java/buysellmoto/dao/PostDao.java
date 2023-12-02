package buysellmoto.dao;

import buysellmoto.core.enumeration.NamingConventionEnum;
import buysellmoto.model.dto.PostDto;
import buysellmoto.model.filter.PostFilter;
import buysellmoto.model.mapper.PostMapper;
import buysellmoto.model.vo.PostProjection;
import buysellmoto.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostDao {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper mapper;

    public PostDto getById(Long id) {
        return mapper.toDto(postRepository.findById(id).orElseThrow());
    }

    public List<PostDto> getByIds(List<Long> ids) {
        return mapper.toDto(postRepository.findAllByIdIn(ids));
    }


    public List<PostDto> getAll() {
        return mapper.toDto(postRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public PostDto createOne(PostDto dto) {
        return mapper.toDto(postRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public PostDto updateOne(PostDto dto) {
        return mapper.toDto(postRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        postRepository.delete(postRepository.findById(id).orElseThrow());
        return true;
    }

    public Page<PostProjection> getPaging(PostFilter postFilter) {
        postFilter.beautify();
        return postRepository.getPaging(postFilter.getPageable(),
                postFilter.getSearchValue(),
                postFilter.getBrandName(),
                postFilter.getProvince());
    }

    public List<PostProjection> getPostByShowroomId(Long showroomId) {
        return postRepository.getPostByShowroomId(showroomId);
    }

}
