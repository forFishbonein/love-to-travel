package com.lovetotravel.travel.service;

import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.entity.vo.team.TeamInviteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {

    final JavaMailSender mailSender;
    final TemplateEngine templateEngine;
    // 邮件发送人
    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * @param to      邮件接收人
     * @param subject 标题
     * @param content 内容
     */
    public ResponseEntity<String> sendMimeMail(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            Context context = new Context();
            //设置传入模板的页面的参数 参数名为:id 参数随便写一个就行
            context.setVariable("message", content);
            System.out.println(content);
            //emailTemplate是你要发送的模板我这里用的是Thymeleaf
            String process = templateEngine.process("emailDemo", context);
            //邮件内容，html格式
            helper.setText(process, true);
            mailSender.send(mimeMessage);
            return ResponseEntity.status(HttpStatus.CREATED).body("发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("e.getMessage()");
        }
    }

    public void sendEmail(TeamInviteVo teamInviteVo, User inviter, User user) {
        String email = teamInviteVo.getUserEmail();

        String subject = "来自团队" + teamInviteVo.getTeamName() + "的邀请函";

        String content = "亲爱的";

        if (user.getName() != null) {
            content = content + user.getName();

        } else {
            content = content + user.getEmail();
        }

        content = content + "，您收到了来自团队" + teamInviteVo.getTeamName() + "的邀请函，赶快访问爱旅游官网并加入吧！";

        sendMimeMail(email, subject, content);

        log.info("发送成功");
    }

}
