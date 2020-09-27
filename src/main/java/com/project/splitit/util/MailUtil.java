package com.project.splitit.util;

import com.project.splitit.view.SuccessResponse;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public class MailUtil {

    // for single To and multiple CC
    public static SuccessResponse sendEmail(String senderGridAPIKey, String fromEmailId, String toEmailId,
                                            List<String> ccEmailIds, String subject, Content content) throws IOException {
        Email from = new Email(fromEmailId);
        Email to = new Email(toEmailId);
        Mail mail = new Mail(from, subject, to, content);
        if (ccEmailIds != null) {
            for (int i = 0; i < ccEmailIds.size(); i++) {
                mail.personalization.get(0).addCc(new Email(ccEmailIds.get(i)));
            }
        }
        SendGrid sg = new SendGrid(senderGridAPIKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
        return new SuccessResponse(HttpStatus.OK.value(), "mail sent successfully");

    }

    // for multiple TO and multiple CC
    public static SuccessResponse sendEmail(String senderGridAPIKey, String fromEmailId, List<String> toEmailIds,
                                            List<String> ccEmailIds, String subject, Content content) throws IOException {
        Email from = new Email(fromEmailId);
        Mail mail = new Mail();
        mail.setSubject(subject);
        mail.setFrom(from);
        mail.addContent(content);
        mail.addPersonalization(new Personalization());
        if (toEmailIds != null) {
            for (int i = 0; i < toEmailIds.size(); i++) {
                mail.personalization.get(0).addTo(new Email(toEmailIds.get(i)));
            }
        }
        if (ccEmailIds != null) {
            for (int i = 0; i < ccEmailIds.size(); i++) {
                mail.personalization.get(0).addCc(new Email(ccEmailIds.get(i)));
            }
        }
        SendGrid sg = new SendGrid(senderGridAPIKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
        return new SuccessResponse(HttpStatus.OK.value(), "mail sent successfully");

    }

}
