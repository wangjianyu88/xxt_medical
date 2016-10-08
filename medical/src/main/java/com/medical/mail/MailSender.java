package com.medical.mail;
import java.util.List;
import java.util.Properties;
 
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
/**
 * 邮件发送类
 * @author 
 * @time 下午6:57:15
 */
public class MailSender {
	  /**
     * 发送邮件的props文件
     */
    private final transient Properties props = System.getProperties();
    /**
     * 邮件服务器登录验证
     */
    private transient MailAuthenticator authenticator;
 
    /**
     * 邮箱session
     */
    private transient Session session;
    /**
     * 注册时邮件发送人的email
     */
    private static String registerEmailAddress = "wjy156442184@163.com";
    //private static String registerEmailAddress = "familyhealthtime@sina.com";
    /**
     * 注册时邮件发送人的email的密码
     */
    private static String registerEmailPassword = "wangjianyu88";
    //private static String registerEmailPassword = "anny198354";
    /**
     * 邮件主题
     */
    public static String mailSubject = "来自家用医药助手注册验证邮件";
    
    public static String englishMailSubject = "From household registered medical assistant validation email";
  
    /**
     * 初始化邮件发送器
     * 
     * @param smtpHostName
     *                SMTP邮件服务器地址
     * @param username
     *                发送邮件的用户名(地址)
     * @param password
     *                发送邮件的密码
     */
    public MailSender(final String smtpHostName, final String username,final String password) {
    	init(username, password, smtpHostName);
    }
 
    /**
     * 初始化邮件发送器
     * 
     * @param username
     *                发送邮件的用户名(地址)，并以此解析SMTP服务器地址
     * @param password
     *                发送邮件的密码
     */
    public MailSender(final String username, final String password) {
    	//通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
    	String[] usernameArray = username.split("@");
    	String smtpHostName = null;
    	if(usernameArray[1].equals("qq.com")){
    		smtpHostName = "smtp.exmail." + usernameArray[1];
    	}else{
    		smtpHostName = "smtp." + usernameArray[1];
    	}
    	//smtpHostName = "smtp.exmail.qq.com";
    	init(username, password, smtpHostName);
 
    }
    public MailSender(){
    	this(registerEmailAddress,registerEmailPassword);
    }
    /**
     * 初始化
     * 
     * @param username
     *                发送邮件的用户名(地址)
     * @param password
     *                密码
     * @param smtpHostName
     *                SMTP主机地址
     */
    private void init(String username, String password, String smtpHostName) {
    	// 初始化props
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.host", smtpHostName);
    	// 验证
    	authenticator = new MailAuthenticator(username, password);
    	// 创建session
    	session = Session.getInstance(props, authenticator);
    }
 
    /**
     * 发送邮件
     * 
     * @param recipient
     *                收件人邮箱地址
     * @param subject
     *                邮件主题
     * @param content
     *                邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, String subject, Object content)
        throws AddressException, MessagingException {
    	// 创建mime类型邮件
    	final MimeMessage message = new MimeMessage(session);
    	// 设置发信人
    	message.setFrom(new InternetAddress(authenticator.getUsername()));
	    // 设置收件人
	    message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
	    // 设置主题
	    message.setSubject(subject);
	    // 设置邮件内容
	    message.setContent(content.toString(), "text/html;charset=utf-8");
	    // 发送
	    Transport.send(message);
    }
 
    /**
     * 群发邮件
     * 
     * @param recipients
     *                收件人们
     * @param subject
     *                主题
     * @param content
     *                内容
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Object content, String languageTag)
        throws AddressException, MessagingException {
	    // 创建mime类型邮件
	    final MimeMessage message = new MimeMessage(session);
	    // 设置发信人
	    message.setFrom(new InternetAddress(authenticator.getUsername()));
	    // 设置收件人们
	    final int num = recipients.size();
	    InternetAddress[] addresses = new InternetAddress[num];
	    for (int i = 0; i < num; i++) {
	        addresses[i] = new InternetAddress(recipients.get(i));
	    }
	    message.setRecipients(RecipientType.TO, addresses);
	    // 设置主题
	    message.setSubject(subject);
	    // 设置邮件内容
	    message.setContent(content.toString(), "text/html;charset=utf-8");
	    // 发送
	    Transport.send(message);
    }
    /**
     * 获取邮件内容
     * @param userId
     * @return
     */
    public String getActivateAccountContent(String serverUrl, String token, String languageTag){
    	if(languageTag.equals("zh")){
	    	return  "您正在进行家用医药助手注册，点击以下链接完成注册。<br/>"
	        		+ serverUrl + "/register/activateRegister.do?token=" + token 
	        		+ "<br/>（该链接在24小时内有效，24小时后需要重新获取验证邮件）";
    	}else{
    		return "You are household registered medical assistant, click the following link to complete registration.<br/>"
    				+ serverUrl + "/register/activateRegister.do?token=" + token 
	        		+ "<br/>(The link is valid within 24 hours, need to get verification email after 24 hours)";
    	}
    }
    
    
     
    /**
     * 获取邮件内容
     * @param userId
     * @return
     */
    public String getForgetPasswordContent(String serverUrl, String username,String token, String languageTag){
    	if(languageTag.equals("zh")){
	    	return  "您正在对家用医药助手的<red>" + username + "</red>账号，进行密码重置修改，请点击链接激活.<br/>"
	        		+ serverUrl + "/register/activateForgetPassword.do?token=" + token 
	        		+ "<br/>（该链接在24小时内有效，24小时后需要重新获取验证邮件）";
    	}else{
    		return "You are account for home medical assistant<red>(" + username + ")</red>, modifying password reset, please click the link to activate.<br/>"
	        		+ serverUrl + "/register/activateForgetPassword.do?token=" + token 
	        		+ "<br/>(The link is valid within 24 hours, need to get verification email after 24 hours)";
    	}
    }
    public static void main(String[] args) throws Exception{
	   //发件人必须是非qq邮箱，否则过不去
	   MailSender mailSender = new MailSender(registerEmailAddress,registerEmailPassword);
	   //MailSender mailSender = new MailSender("156442184@qq.com","wangjianyu88");
	   mailSender.send("156442184@qq.com", "自己写的测试邮件主题", "自己写的测试邮件内容");
    }
	
}
