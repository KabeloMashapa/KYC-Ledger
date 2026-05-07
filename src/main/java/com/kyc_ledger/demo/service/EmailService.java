package com.kyc_ledger.demo.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendKycSubmissionEmail(String to,String fullName, String kycId) {
        sendEmail(
                to,
                "KYC Submission Received - "+ kycId,
                "Dear "+ fullName + ",\n\n"+
                        "Your KYC Submission has been received successfully.\n"+
                        "KYC ID: "+kycId + "\n"+
                        "Status: PENDING REVIEW\n\n"+
                        "We will review your documents and notify you shortly.\n\n"+
                        "Regards,\nKYC Blockchain Team"
        );
    }
    public void sendKycApprovedEmail(String to,String fullName,String kycId) {
        sendEmail(
                to,
                "KYC Approved Received - "+ kycId,
                "Dear "+ fullName + ",\n\n"+
                        "Your KYC Submission has been approved.\n"+
                        "KYC ID: "+kycId + "\n"+
                        "Status: APPROVED\n\n"+
                        "Regards,\nKYC Blockchain Team"
        );
    }
    // KYC Rejected
    public void sendKycRejectedEmail(String to, String fullName,String kycId,String reason) {
        sendEmail(
                to,
                "KYC Rejected - " + kycId,
                "Dear " + fullName + ",\n\n" +
                        "Unfortunately your KYC has been rejected.\n" +
                        "KYC ID: " + kycId + "\n" +
                        "Reason: " + reason + "\n\n" +
                        "Please resubmit with correct documents.\n\n" +
                        "Regards,\nKYC Blockchain Team"
        );
    }
    // Generic email sender
    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
