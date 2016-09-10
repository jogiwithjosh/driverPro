/**
 * 
 */
package com.handbrakers.service;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Jogireddy
 *
 */
@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;  
	
	public boolean sendMail(String from, String to, String subject, String msg) {
		boolean sent = false;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false,"utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);
			message.setContent(msg, "text/html");
			helper.setSentDate(new Date());
			helper.setReplyTo(to);
			mailSender.send(message);
			sent = true;
		} catch (Exception e) {
			e.printStackTrace();
			sent = false;
		}
		return sent;
	}

	public boolean sendMultipleMails(String[] to, String from, String subject,
			String msg) {
		boolean sent = false;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false,"utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);
			message.setContent(msg, "text/html");
			helper.setSentDate(new Date());
			//helper.setReplyTo(to);
			mailSender.send(message);
			sent = true;
		} catch (Exception e) {
			e.printStackTrace();
			sent = false;
		}
		return sent;
	}

}
