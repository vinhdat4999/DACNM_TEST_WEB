package com.blas.fish.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail implements Runnable{
	private String email;
	private String title;
	private String content;
	
    public SendEmail(String email, String title, String content) {
		super();
		this.email = email;
		this.title = title;
		this.content = content;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String HOST_NAME = "smtp.gmail.com";

        final int SSL_PORT = 465; // Port for SSL

        final int TSL_PORT = 587; // Port for TLS/STARTTLS

        final String APP_EMAIL = "blasecommerce@gmail.com"; // your email

        final String APP_PASSWORD = "0546550566Qq"; // your password
        String RECEIVE_EMAIL = this.email;
        // 1) get the session object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.socketFactory.port", SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", SSL_PORT);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });

        // 2) compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(APP_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(RECEIVE_EMAIL));
            message.setSubject(this.title, "utf8");

            // 3) create MimeBodyPart object and set your message text
            MimeBodyPart messageBodyPart1 = new MimeBodyPart();
//            messageBodyPart1.setText(content, "utf8");
            messageBodyPart1.setContent(this.content, "text/html; charset=utf-8");
            System.out.println(this.content);

            // 4) create new MimeBodyPart object and set DataHandler object to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
	}
}
