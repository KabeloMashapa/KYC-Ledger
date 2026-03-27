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
}
