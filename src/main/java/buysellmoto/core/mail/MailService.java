package buysellmoto.core.mail;


import buysellmoto.model.vo.BuyRequestVo;
import buysellmoto.model.vo.SellRequestVo;
import buysellmoto.model.vo.UserVo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void resetPassword(UserVo userVo, String newPassword) throws MessagingException {
        String subject = "Mật khẩu mới từ BuySellMotorbike";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the recipient, subject, and the template
        helper.setTo(userVo.getEmail());
        helper.setSubject(subject);

        // Create a Thymeleaf context
        Context context = new Context();

        context.setVariable("hello", "Xin chào, " + userVo.getCustomerDto().getFullName());
        context.setVariable("newpassword", newPassword);

        // Process the Thymeleaf template with the context
        String emailContent = templateEngine.process("email-template", context);

        // Set the email content
        helper.setText(emailContent, true);

        // Send the email
        javaMailSender.send(mimeMessage);
    }

    public void approveSellRequest(SellRequestVo sellRequestVo) throws MessagingException {
        String subject = "Xác nhận Yêu Cầu Bán Xe và Hướng Dẫn Tiếp Theo";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the recipient, subject, and the template
        helper.setTo(sellRequestVo.getUserDto().getEmail());
        helper.setSubject(subject);

        // Create a Thymeleaf context
        Context context = new Context();

        context.setVariable("customerName", sellRequestVo.getCustomerVo().getFullName());
        context.setVariable("showroomName", sellRequestVo.getShowroomDto().getName());
        context.setVariable("address", sellRequestVo.getShowroomDto().getAddress());
        context.setVariable("phone", sellRequestVo.getShowroomDto().getPhone());
        context.setVariable("email", sellRequestVo.getShowroomDto().getEmail());

        // Process the Thymeleaf template with the context
        String emailContent = templateEngine.process("email-template-2", context);

        // Set the email content
        helper.setText(emailContent, true);

        // Send the email
        javaMailSender.send(mimeMessage);
    }

    public void checkedSellRequest(SellRequestVo sellRequestVo) throws MessagingException {
        String subject = "Thông Báo: Tiếp Nhận Thành Công Xe Của Bạn";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the recipient, subject, and the template
        helper.setTo(sellRequestVo.getUserDto().getEmail());
        helper.setSubject(subject);

        // Create a Thymeleaf context
        Context context = new Context();

        context.setVariable("customerName", sellRequestVo.getCustomerVo().getFullName());
        context.setVariable("showroomName", sellRequestVo.getShowroomDto().getName());
        context.setVariable("sellRequestId", sellRequestVo.getId());
        context.setVariable("motorbikeName", sellRequestVo.getMotorbikeDto().getName());
        context.setVariable("checkedDate", sellRequestVo.getCheckedSellRequestDto()
                .getCheckedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("phone", sellRequestVo.getShowroomDto().getPhone());
        context.setVariable("email", sellRequestVo.getShowroomDto().getEmail());

        // Process the Thymeleaf template with the context
        String emailContent = templateEngine.process("email-template-3", context);

        // Set the email content
        helper.setText(emailContent, true);

        // Send the email
        javaMailSender.send(mimeMessage);
    }

    public void rejectSellRequest(SellRequestVo sellRequestVo) throws MessagingException {
        String subject = "Thông Báo: Từ Chối Yêu Cầu Bán Xe Của Bạn";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the recipient, subject, and the template
        helper.setTo(sellRequestVo.getUserDto().getEmail());
        helper.setSubject(subject);

        // Create a Thymeleaf context
        Context context = new Context();

        context.setVariable("customerName", sellRequestVo.getCustomerVo().getFullName());
        context.setVariable("showroomName", sellRequestVo.getShowroomDto().getName());
        context.setVariable("rejectedReason", sellRequestVo.getRejectRequestDto().getRejectedReason());
        context.setVariable("phone", sellRequestVo.getShowroomDto().getPhone());
        context.setVariable("email", sellRequestVo.getShowroomDto().getEmail());

        // Process the Thymeleaf template with the context
        String emailContent = templateEngine.process("email-template-4", context);

        // Set the email content
        helper.setText(emailContent, true);

        // Send the email
        javaMailSender.send(mimeMessage);
    }

    public void approveBuyRequest(BuyRequestVo buyRequestVo) throws MessagingException {
        String subject = "Thông báo: Lịch Hẹn Xem Xe Tại Showroom";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Set the recipient, subject, and the template
        helper.setTo(buyRequestVo.getUserDto().getEmail());
        helper.setSubject(subject);

        // Create a Thymeleaf context
        Context context = new Context();

        context.setVariable("customerName", buyRequestVo.getCustomerVo().getFullName());
        context.setVariable("showroomName", buyRequestVo.getShowroomDto().getName());
        context.setVariable("address", buyRequestVo.getShowroomDto().getAddress());
        context.setVariable("time", buyRequestVo.getCheckingAppointmentDto().getAppointmentDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        context.setVariable("phone", buyRequestVo.getShowroomDto().getPhone());
        context.setVariable("email", buyRequestVo.getShowroomDto().getEmail());

        // Process the Thymeleaf template with the context
        String emailContent = templateEngine.process("email-template-5", context);

        // Set the email content
        helper.setText(emailContent, true);

        // Send the email
        javaMailSender.send(mimeMessage);
    }

}
