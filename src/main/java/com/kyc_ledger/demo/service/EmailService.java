package com.kyc_ledger.demo.service;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

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
}
