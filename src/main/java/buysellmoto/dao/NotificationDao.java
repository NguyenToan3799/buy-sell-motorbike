package buysellmoto.dao;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.model.dto.NotificationDto;
import buysellmoto.model.entity.NotificationEntity;
import buysellmoto.model.mapper.NotificationMapper;
import buysellmoto.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class NotificationDao {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper mapper;
    
    @Transactional(rollbackOn = {Exception.class})
    public NotificationDto createOne(NotificationDto dto) {
        dto.setId(null);
        dto.setNotificationDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        dto.setIsSeen(false);
        dto.setIsNotified(false);
        return mapper.toDto(notificationRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public NotificationDto updateOne(NotificationDto dto) {
        return mapper.toDto(notificationRepository.save(mapper.toEntity(dto)));
    }

    public NotificationDto getById(Long id) {
        return mapper.toDto(notificationRepository.findById(id).orElseThrow(
                () -> new BusinessException(ApiMessageCode.NOTIFICATION_NOT_EXIST)
        ));
    }

    public List<NotificationDto> getByCustomerId(Long customerId) {
        return mapper.toDto(notificationRepository.findByCustomerId(customerId));
    }

    public List<NotificationDto> getNotificationNotYetAnnouncedByCustomerId(Long customerId, Boolean isNotified) {
        return mapper.toDto(notificationRepository.findByCustomerIdAndIsNotified(customerId, isNotified));
    }

}
