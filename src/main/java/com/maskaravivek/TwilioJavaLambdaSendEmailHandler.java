package com.maskaravivek;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.util.List;

public class TwilioJavaLambdaSendEmailHandler implements RequestHandler<SNSEvent, Void> {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Void handleRequest(SNSEvent event, Context context) {
        System.out.println(event);
        List<SNSEvent.SNSRecord> records = event.getRecords();

        for(SNSEvent.SNSRecord record: records) {
            String message = record.getSNS().getMessage();
            SendEmailRequest sendEmailRequest = gson.fromJson(message, SendEmailRequest.class);

            Email from = new Email(sendEmailRequest.getFromEmailId());
            Email to = new Email(sendEmailRequest.getToEmailId());

            Content content = new Content("text/html", sendEmailRequest.getBody());

            Mail mail = new Mail(from, sendEmailRequest.getTitle(), to, content);

            SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            try {
                request.setBody(mail.build());
                Response response = sg.api(request);

                System.out.println(response.getStatusCode());
                System.out.println(response.getHeaders());
                System.out.println(response.getBody());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
