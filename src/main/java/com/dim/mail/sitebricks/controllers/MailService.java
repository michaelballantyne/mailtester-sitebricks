package com.dim.mail.sitebricks.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

@At("/mailservice") @Service
public class MailService {
	public String address;
	public String body;
	public String subject;

	@Post 
	public Reply<String> sendMessage() {
		String mailServiceJNDIName = "java:/comp/env/mail/FreebirdMailService";

		javax.mail.Session session;
		try
		{
			Object lookupResult = new InitialContext().lookup(mailServiceJNDIName); // Lookup result can't be null
			session = (javax.mail.Session) lookupResult;
		}
		catch (NamingException e)
		{
			return Reply.with("Couldn't connect to email resource: " + getStacktrace(e));
		}

		try {
			Transport transport = session.getTransport();

			MimeMessage message = new MimeMessage( session );
			
			message.addRecipient(
					Message.RecipientType.TO, new InternetAddress(address)
					);
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			transport.close();

		}

		catch (MessagingException e){
			return Reply.with("Email send failed" + getStacktrace(e));
		}

		return Reply.with("Message Sent");
	}
	
	private String getStacktrace(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
