package familyapp.service;

import java.util.Arrays;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AmazonEmailerService {

	private String awsKey;
	private String awsSecret;
	
	
	public String getAwsKey() {
		return awsKey;
	}

	public void setAwsKey(String awsKey) {
		this.awsKey = awsKey;
	}

	public String getAwsSecret() {
		return awsSecret;
	}

	public void setAwsSecret(String awsSecret) {
		this.awsSecret = awsSecret;
	}

	public AmazonEmailerService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final AmazonSimpleEmailServiceClient aesc = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials("", ""));
		final SendEmailRequest ser = new SendEmailRequest();
		// setting from
			ser.setSource("");
		final Destination dest = new Destination();
			dest.setToAddresses(Arrays.asList("" , ""));
		ser.setDestination(dest);
		ser.setMessage(compose("SES email works","Hi Peter, SES email works from application!!!!"));
		
		try {
			aesc.sendEmail(ser);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
}
		
	
		private static com.amazonaws.services.simpleemail.model.Message compose(String subject, String message) {
			final com.amazonaws.services.simpleemail.model.Message msg = new com.amazonaws.services.simpleemail.model.Message();

			// Set the subject
			final Content subjectContent = new Content();
			subjectContent.setData(subject);
			msg.setSubject(subjectContent);

			// Set the body of the content
			final Body body = new Body();
			if (message != null) {
				final Content c = new Content();
				c.setData(message);
				body.setHtml(c);
			}
			msg.setBody(body);
			return msg;
		}

}
