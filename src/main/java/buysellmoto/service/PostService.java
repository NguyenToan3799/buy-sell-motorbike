package buysellmoto.service;

import buysellmoto.core.enumeration.PostStatusEnum;
import buysellmoto.core.enumeration.RequestTypeEnum;
import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.*;
import buysellmoto.model.dto.*;
import buysellmoto.model.filter.PostFilter;
import buysellmoto.model.mapper.PostMapper;
import buysellmoto.model.vo.PostProjection;
import buysellmoto.model.vo.PostVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    private MotorbikeImageDao motorbikeImageDao;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private RequestHistoryDao requestHistoryDao;

    @Autowired
    private PostMapper postMapper;

    public PostVo getById(Long id) {
        PostVo postVo = postMapper.dtoToVo(postDao.getById(id));
        postVo.setMotorbikeDto(motorbikeDao.getById(postVo.getMotorbikeId()));
        postVo.setSellRequestDto(sellRequestDao.getById(postVo.getSellRequestId()));
        postVo.setShowroomDto(showroomDao.getById(postVo.getShowroomId()));
        postVo.setMotorbikeImageDtos(motorbikeImageDao.getByMotorbikeId(postVo.getMotorbikeId()));
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
        preparingDto.setStatus(PostStatusEnum.ACTIVE.getCode());

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(sellRequestDto.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        notificationDto.setRequestId(sellRequestDto.getId());
        notificationDto.setNotificationContent(
                "Xe #" + sellRequestDto.getId() + " của bạn đã được đăng bài!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(sellRequestDto.getId());
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Đã đăng bài thành công");
        requestHistoryDao.createOne(requestHistoryDto);

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

    public List<PostProjection> getPostByShowroomId(Long showroomId, String status) {
        if (Objects.equals(PostStatusEnum.of(status), PostStatusEnum.INVALID)) {
            throw new BusinessException(ApiMessageCode.INVALID_STATUS);
        }
        return postDao.getPostByShowroomId(showroomId, status);
    }

}
