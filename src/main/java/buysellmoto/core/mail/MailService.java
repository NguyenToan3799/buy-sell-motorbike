package buysellmoto.core.mail;


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

}
