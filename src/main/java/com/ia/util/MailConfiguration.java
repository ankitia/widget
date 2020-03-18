package com.ia.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailConfiguration {
	
	
	static final String username = "developers@infoanalytica.com";
	static final String passwd = "Admin123*+@"; 
	static Session session  = null;
	 
 	public static Session mailConfiguration() {
 		
 		Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
 		
        return session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, passwd);
            }
        });
 	}
	 
    public static void sendMail(String subject,String body) {
        final String from = "developers@infoanalytica.com";
        session = mailConfiguration();
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,"ankit.s@infoanalytica.com");
            msg.setRecipients(Message.RecipientType.BCC,"ankit.s@infoanalytica.com");
            //msg.setReplyTo(new InternetAddress[]{new InternetAddress("noreply@infoanalytica.com")});
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(body, "text/html; charset=utf-8");
            Transport.send(msg);
            System.out.println("send failed, done: " );
        } catch (MessagingException e) {
            System.out.println("send failed, exception: " + e);
        }
        System.out.println("Sent Ok") ;
    }
}
