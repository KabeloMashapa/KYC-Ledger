package com.kyc_ledger.demo.util;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class FileUtil {

    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/jpg",
            "application/pdf"
    );
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024 ;
    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() +""+extension;

        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }
    public static boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        }
        catch(IOException e) {
            return false;
        }
    }
    public static boolean isValidFileType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && ALLOWED_TYPES.contains(contentType);

    }
    public static boolean isValidFileSize(MultipartFile file) {
        return file.getSize() <= MAX_FILE_SIZE;
    }
    public static String getFileExtension(String filename) {
        if(filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
    public static String getReadableFileSize(long size) {
        if(size < 1024) return size + "B";
        if(size < 1024) return (size / 1024) + "KB";
        return (size / (1024 * 1024)) + "MB";
    }
    public static boolean isEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

}
