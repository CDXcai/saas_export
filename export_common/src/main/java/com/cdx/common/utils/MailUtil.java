package com.cdx.common.utils;


import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

	/**
	 * 发送邮件
	 *      to：邮件接收人的邮箱地址
	 *      subject：主题
	 *      content：内容
	 */
	public static void sendMsg(String to ,String subject ,String content) throws Exception{
		//1.设置参数
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.163.com");  //设置主机地址   smtp.qq.com    smtp.163.com
		props.setProperty("mail.smtp.auth", "true");//认证

		//2.产生一个用于邮件发送的Session对象
		Session session = Session.getInstance(props);

		//3.产生一个邮件的消息对象
		MimeMessage message = new MimeMessage(session);

		//4.设置消息的发送者
		Address fromAddr = new InternetAddress("cfyccffyy@163.com");
		message.setFrom(fromAddr);

		//5.设置消息的接收者
		Address toAddr = new InternetAddress(to);
		//TO 直接发送  CC抄送    BCC密送
		message.setRecipient(MimeMessage.RecipientType.TO, toAddr);

		//6.设置主题
		message.setSubject(subject);
		//7.设置正文
		message.setContent(content,"text/html;charset=utf-8");

		//8.准备发送，得到火箭
		Transport transport = session.getTransport("smtp");
		//9.设置火箭的发射目标
		transport.connect("smtp.163.com", "cfyccffyy@163.com", "cfyccffyy666");
		//10.发送
		transport.sendMessage(message, message.getAllRecipients());

		//11.关闭
		transport.close();
	}
	public static void main(String[] args) throws Exception {
		// 目标邮箱地址
		String to = "caidongxingcdx@foxmail.com";
		// 右键主题
		String subject = "这是右键的主题";
		// 发送的内容
		String test = "<a href = 'http://www.baidu.com'>这是右键的内容</a>";
		sendMsg(to,subject,test);
		System.out.println("右键发送");
	}

}
