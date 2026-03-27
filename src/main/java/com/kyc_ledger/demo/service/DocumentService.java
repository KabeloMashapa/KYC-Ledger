package com.kyc_ledger.demo.service;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.dto.DocumentDTO;
import com.kyc_ledger.demo.exception.InvalidFileException;
import com.kyc_ledger.demo.exception.KycNotFoundException;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.repository.DocumentRepository;
import com.kyc_ledger.demo.repository.KycRepository;
import com.kyc_ledger.demo.util.FileUtil;
import com.kyc_ledger.demo.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final KycRepository kycRepository;

    @Value("${kyc.document.upload-dir}")
    private String uploadDir;

    public ApiResponseDTO<DocumentDTO> uploadDocument(String kycId,MultipartFile file,String documentType) {

        if(FileUtil.isEmpty(file)) {
            throw new InvalidFileException("File is empty");
        }
        if(!FileUtil.isValidFileType(file)) {
            throw new InvalidFileException("Invalid file type. Allowed: JPG, PNG, PDF ");
        }
        if(!FileUtil.isValidFileSize(file)) {
            throw new InvalidFileException("File size exceeds 10mb limit");
        }
    }
}
