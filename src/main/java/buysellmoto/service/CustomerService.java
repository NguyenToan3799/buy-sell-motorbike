package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.UserDao;
import buysellmoto.model.dto.UserDto;
import buysellmoto.model.filter.UserFilter;
import buysellmoto.model.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;

    public UserDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return userDao.getById(id);
    }
    
    public List<UserDto> getAll() {
        return userDao.getAll();
    }

    public UserDto checkLogin(String account, String password) {
        UserDto loadingUser = userDao.checkLogin(account, password);
        if(Objects.isNull(loadingUser)){
            throw new BusinessException(ApiMessageCode.USER_NOT_EXIST);
        }
        if(!loadingUser.getStatus()){
            throw new BusinessException(ApiMessageCode.DEACTIVATED_USER);
        }
        return loadingUser;
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserDto createOne (UserFilter filter) {
        UserDto preparingDto = userMapper.filterToDto(filter);
        return userDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserDto updateOne(Long id, UserFilter filter) {
        UserDto preparingDto = userMapper.filterToDto(filter);
        preparingDto.setId(id);
        return userDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        userDao.deleteById(id);
        return true;
    }

}
