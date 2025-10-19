package com.cucu.test.utility;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import java.util.Properties;

public class JavaEmail {
	
	public static void main(String[] args) throws MessagingException {
		JavaEmail javaMail = new JavaEmail();
		final String username = "shivam9ronaldo7@gmail.com";
		final String password = "";
		javaMail.sendEmail(username, password);
		javaMail.checkMail(username, password);
	}

	public void checkMail(String username,String password) throws MessagingException {
		Session session = Session.getDefaultInstance(new Properties( ));
		Store store = session.getStore("imaps");
		store.connect("imap.googlemail.com", 993, username, password);
		Folder inbox = store.getFolder( "INBOX" );
		inbox.open( Folder.READ_ONLY );
		Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.RECENT), false));		
		for (int i = messages.length - 1; i >= messages.length - 5; i--) {
		    Message message = messages[i];
			System.out.println("----------------------------------------");
			System.out.println("MessageNumber: " + message.getMessageNumber());
			System.out.println("SendDate: " + message.getSentDate());
			System.out.println("ReceivedDate: " + message.getReceivedDate());
			System.out.println("Subject:" + message.getSubject());
			System.out.println("ContentType:" + message.getContentType());
		}
	}

	public void sendEmail(String username, String password) throws AddressException, MessagingException {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		Session session = Session.getInstance(prop,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("shivam9ronaldo7@gmail.com"));
		message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse("shalinisinsha@gmail.com")
				);
		message.setSubject("Testing Gmail SSL");
		message.setText("Dear Mail Crawler,"
				+ "\n\n Please do not spam my email!");
		Transport.send(message);
		System.out.println("Done");
	}
}
