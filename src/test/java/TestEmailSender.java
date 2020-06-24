import com.xagu.blog.XaguBlogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@SpringBootTest(classes = XaguBlogApplication.class)
public class TestEmailSender {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void testMailSender() {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("XAGU的个人博客<xagu_qc@foxmail.com>");
        simpleMailMessage.setTo("xagu_qc@foxmail.com");
        simpleMailMessage.setSubject("ceshi");
        simpleMailMessage.setText("验证码：990011");
        javaMailSender.send(simpleMailMessage);
    }
}
