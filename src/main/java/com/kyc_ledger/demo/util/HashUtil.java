package com.kyc_ledger.demo.util;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String hashFile(MultipartFile file) throws IOException{
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            InputStream inputStream = file.getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;

            while((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer,0,bytesRead);
            }
            return bytesToHex(digest.digest());
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found",e);
        }
    }
    public static String hashString(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());
            return bytesToHex(hashBytes);
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found",e);
        }
    }
    public static String hashKycData(String firstName,String lastName,
                                     String idNumber,String dateOfBirth,
                                     String nationality) {
        String combined = firstName + lastName + idNumber + dateOfBirth + nationality ;
        return hashString(combined);
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for(byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static boolean verifyFileHash(MultipartFile file,String existingHash) throws IOException {
        String newHash = hashFile(file);
        return newHash.equals(existingHash);
    }
    public static boolean verifyStringHash(String data,String existingHash) {
        String newHash = hashString(data);
        return newHash.equals(existingHash);
    }
}
