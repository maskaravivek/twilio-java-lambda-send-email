package com.maskaravivek;

public class SendEmailRequest {
    private String fromEmailId;
    private String toEmailId;
    private String title;
    private String body;

    public SendEmailRequest(String fromEmailId,
                            String toEmailId,
                            String title,
                            String body) {
        this.fromEmailId = fromEmailId;
        this.toEmailId = toEmailId;
        this.title = title;
        this.body = body;
    }

    public String getFromEmailId() {
        return fromEmailId;
    }
    public String getToEmailId() {
        return toEmailId;
    }
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
}
