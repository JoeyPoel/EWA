package teamx.app.backend.services;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import teamx.app.backend.models.dto.MailRequest;
import teamx.app.backend.models.dto.MailResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Email service
 *
 * @author Joey van der Poel
 */
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration config;

    public MailResponse sendEmail(MailRequest request, Map<String, Object> model,String templateFileName) {
        MailResponse response = new MailResponse();
        MimeMessage message = emailSender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate(templateFileName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(fromEmail);
            emailSender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : "+ e.getMessage());
            response.setStatus(Boolean.FALSE);
        }

        return response;
    }
}

