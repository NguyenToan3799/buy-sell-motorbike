package buysellmoto.service;

import buysellmoto.dao.RoleDao;
import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.mapper.RoleMapper;
import buysellmoto.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public RoleDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return roleDao.getById(id);
    }

//    public List<RoleDto> getByIds(List<Long> ids) {
//        return mapper.toDto(roleRepository.findAllByIdIn(ids));
//    }
//
//    public List<RoleDto> getAll() {
//        return mapper.toDto(roleRepository.findAll());
//    }
//
//    @Transactional(rollbackOn = {Exception.class})
//    public RoleDto createOne(RoleDto dto) {
//        return mapper.toDto(roleRepository.save(mapper.toEntity(dto)));
//    }
//
//    @Transactional(rollbackOn = {Exception.class})
//    public RoleDto updateOne(RoleDto dto) {
//        return mapper.toDto(roleRepository.save(mapper.toEntity(dto)));
//    }
//
//    @Transactional(rollbackOn = {Exception.class})
//    public boolean deleteById(Long id) {
//        roleRepository.delete(roleRepository.findById(id).orElseThrow());
//        return true;
//    }

}
