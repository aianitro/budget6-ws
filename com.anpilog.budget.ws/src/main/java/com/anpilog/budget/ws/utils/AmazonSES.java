package com.anpilog.budget.ws.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.anpilog.budget.ws.exceptions.EmailVerificationException;
import com.anpilog.budget.ws.shared.dto.UserDTO;

public class AmazonSES {

	// This email must be verified with Amazon SES
	final String FROM = "aianitro@gmail.com";

	// The subject line for the email
	final String SUBJECT = "Today's budget snippet. Enjoy the day :)";

	// The HTML body for the email.
	final String HTMLBODY = "<h1>Here is what you have for today</h1>"
			+ "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
			+ " click on the following link: "
			+ "<a href='http://localhost:8080/com.anpilog.budget.ws/verify-email.jsp?token=$tokenValue'>"
			+ "Final step to complete your registration" + "</a><br/><br/>"
			+ "Thank you! And we are waiting for you inside!";

	// The email body for recipients with non-HTML email clients.
	final String TEXTBODY = "Please verify your email address. "
			+ "Thank you for registering with our mobile app. To complete registration process and be able to log in,"
			+ " open then the following URL in your browser window: "
			+ " http://localhost:8080/com.anpilog.budget.ws/verify-email.jsp?token=$tokenValue"
			+ " Thank you! And we are waiting for you inside!";

	public void verifyEmail(UserDTO userDto) {
		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_WEST_2).build();

			String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
			String textBodyWithToken = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

			SendEmailRequest request = new SendEmailRequest()
					.withDestination(
							new Destination().withToAddresses(userDto.getEmail()))
					.withMessage(new Message()
							.withBody(
									new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
											.withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
							.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM);

			client.sendEmail(request);

			System.out.println("Email sent!");

		} catch (Exception ex) {
			throw new EmailVerificationException(ex.getMessage());
		}
	}

	public static void main(String args[]) {
		UserDTO userDto = new UserDTO();
		userDto.setEmail("aianitro@gmail.com");
		userDto.setEmailVerificationToken("askdjaskjhckj21897ds");
		new AmazonSES().verifyEmail(userDto);
	}
}
