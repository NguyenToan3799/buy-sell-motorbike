package buysellmoto.dao;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.model.dto.UserDto;
import buysellmoto.model.mapper.UserMapper;
import buysellmoto.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public UserDto getById(Long id) {
        return mapper.toDto(userRepository.findById(id).orElseThrow());
    }

    public List<UserDto> getAll() {
        return mapper.toDto(userRepository.findAll());
    }

    public UserDto checkLogin(String account, String password) {
        return mapper.toDto(userRepository.checkLogin(account, password));
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserDto createOne(UserDto dto) {
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserDto updateOne(UserDto dto) {
        return mapper.toDto(userRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow());
        return true;
    }

}