package come.gomeplus.mailCenter.tool;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestEmailTool {

	public static boolean sendEmail(
			String protocal, 
			String host, 
			String user,
			String pwd, 
			String from, 
			String[] replyTo, 
			String[] cc,
			String[] bcc, 
			String subject, 
			String content, 
			String[] attachPaths) {
		
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocal);
		props.setProperty("mail.smtp.host", host);
		props.put("mail.smtp.auth", "false"); //启用安全认证
		
		Session session = Session.getInstance(props);
		Message msg = new MimeMessage(session);
		Transport transport = null;
		try {
			msg.setSubject(subject);
			msg.setText(content);
			msg.setFrom(new InternetAddress(from));
			
			if(replyTo != null){
				InternetAddress[] addrs = new InternetAddress[replyTo.length];
				for(int i = 0 ; i < replyTo.length ; i++){
					addrs[i] = new InternetAddress(replyTo[i]);
				}
				
				msg.setReplyTo(addrs);
				msg.setRecipients(Message.RecipientType.TO, addrs);
				
			}
			
			if(cc != null){
				InternetAddress[] addrs = new InternetAddress[cc.length];
				for(int i = 0 ; i < cc.length ; i++){
					addrs[i] = new InternetAddress(cc[i]);
				}
				
				msg.setRecipients(Message.RecipientType.CC, addrs);
			}
			
			if(bcc != null){
				InternetAddress[] addrs = new InternetAddress[bcc.length];
				for(int i = 0 ; i < bcc.length ; i++){
					addrs[i] = new InternetAddress(bcc[i]);
				}
				
				msg.setRecipients(Message.RecipientType.BCC, addrs);
			}
			
			MimeMultipart msgMultipart = new MimeMultipart("mixed");
			msg.setContent(msgMultipart);
			
			if(content == null){
				content = "";
			}
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/html;charset=GBK");
			msgMultipart.addBodyPart(messageBodyPart);
			
			if(attachPaths != null){
				for(String path : attachPaths){
					MimeBodyPart attch = new MimeBodyPart();
					msgMultipart.addBodyPart(attch);
					
					File attachement = new File(path);
					DataSource ds = new FileDataSource(attachement);
					DataHandler dh = new DataHandler(ds);
					attch.setDataHandler(dh);
					
					attch.setFileName(attachement.getName());
				}
			}
			
			transport = session.getTransport();
			transport.connect(user,pwd);
			transport.sendMessage(msg,msg.getAllRecipients());
			
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}finally {
			if (null != transport){
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String protol = "smtp";
		String host = "mail.gomeplus.com";
		String user = "bigdata_report@gomeplus.com";
		String pwd = "8264,akqj";
		String from = "bigdata_report@gomeplus.com";
		String[] replyTo = new String[]{"mijia@gomeplus.com","12471439@qq.com"};
		String[] cc = null;
		String[] bcc = null;
		String subject = "test邮件";
		String content = "hello";
		String[] attachPaths = null;
		
		boolean su  = sendEmail(protol, host, user, pwd, from, replyTo, cc, bcc, subject, content, attachPaths);
		System.out.println(su);
	}

}
