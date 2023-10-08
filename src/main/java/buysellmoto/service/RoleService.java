package buysellmoto.dao;

import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.mapper.RoleMapper;
import buysellmoto.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleDao {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper mapper;

    public RoleDto getById(Long id) {
        return mapper.toDto(roleRepository.findById(id).orElseThrow());
    }

    public List<RoleDto> getByIds(List<Long> ids) {
        return mapper.toDto(roleRepository.findAllByIdIn(ids));
    }

    public List<RoleDto> getAll() {
        return mapper.toDto(roleRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public RoleDto createOne(RoleDto dto) {
        return mapper.toDto(roleRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public RoleDto updateOne(RoleDto dto) {
        return mapper.toDto(roleRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        roleRepository.delete(roleRepository.findById(id).orElseThrow());
        return true;
    }

}
