
package edu.ulatina.Mail;

import java.util.Date;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServicioEmailUtil {
    

    
    public static void sendEmail(Session session, String toEmail, String subject, String body){
	      try
	    {
	      MimeMessage msg = new MimeMessage(session);
              
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("tornasolbycinthya@gmail.com", "TORNASOL BY CIN"));

              msg.setReplyTo(InternetAddress.parse("tornasolbycinthya@gmail.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	           Transport.send(msg);

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
    
	
    
}
     

