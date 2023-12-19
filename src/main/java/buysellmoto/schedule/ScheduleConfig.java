package buysellmoto.schedule;

import buysellmoto.core.enumeration.PostStatusEnum;
import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.dao.CheckingAppointmentDao;
import buysellmoto.dao.PostDao;
import buysellmoto.dao.SellRequestDao;
import buysellmoto.model.dto.PostDto;
import buysellmoto.model.vo.SellRequestVo;
import buysellmoto.service.CheckingAppointmentService;
import buysellmoto.service.PostService;
import buysellmoto.service.SellRequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@Log4j2
public class ScheduleConfig {

    @Autowired
    SellRequestService sellRequestService;
    @Autowired
    SellRequestDao sellRequestDao;

    @Autowired
    PostService postService;
    @Autowired
    PostDao postDao;

    @Autowired
    CheckingAppointmentService checkingAppointmentService;
    @Autowired
    CheckingAppointmentDao checkingAppointmentDao;

//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 0 0 * * *")
    public void expiredSellRequestAfterApproved() {
        List<SellRequestVo> sellRequestVos = sellRequestService
                .getListSellRequestByStatus(SellRequestEnum.APPROVED.getCode());
        LocalDateTime today = LocalDateTime.now();

        // Lọc ra các SellRequestVo có approvedDate lớn hơn 7 ngày so với hôm nay
        List<SellRequestVo> sellRequestFilterVos = sellRequestVos.stream()
                .filter(vo -> vo.getApprovedDate() != null &&
                        vo.getApprovedDate().plusDays(7).isBefore(today))
                .toList();

        // Update lại status của SellRequest là hết hạn
        sellRequestFilterVos.forEach(sellRequestFilterVo -> {
            sellRequestFilterVo.setStatus(SellRequestEnum.EXPIRED.getCode());
        });
        sellRequestDao.updateAll(sellRequestVos);

        // Gửi mail thông báo cho khách hàng
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void expiredPost() {
        List<PostDto> postDtos = postDao.getAllPostActive();
        LocalDateTime today = LocalDateTime.now();

        List<PostDto> expiredPosts = postDtos.stream()
                .filter(post -> post.getExpiredDate() != null && post.getExpiredDate().isBefore(today))
                .toList();

        // Update lại status của Post là hết hạn
        expiredPosts.forEach(expiredPost -> {
            expiredPost.setStatus(PostStatusEnum.EXPIRED.getCode());
        });
        postDao.updateAll(expiredPosts);

        // Update lại Status của Sell-request đã hết hạn
        List<Long> sellRequestIds = expiredPosts.stream().map(PostDto::getSellRequestId).toList();
        List<SellRequestVo> sellRequestVos = sellRequestService.getListSellRequestByIds(sellRequestIds);
        sellRequestVos.forEach(sellRequestVo -> sellRequestVo.setStatus(SellRequestEnum.EXPIRED.getCode()));

        // Gửi mail thông báo cho khách hàng xe đã hết hạn

    }

    @Scheduled(cron = "0 0 0 * * *")
    public void expireCheckingAppointment() {

    }


}
