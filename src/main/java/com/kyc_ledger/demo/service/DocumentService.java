package com.kyc_ledger.demo.service;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.dto.DocumentDTO;
import com.kyc_ledger.demo.exception.InvalidFileException;
import com.kyc_ledger.demo.exception.KycNotFoundException;
import com.kyc_ledger.demo.model.Document;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.repository.DocumentRepository;
import com.kyc_ledger.demo.repository.KycRepository;
import com.kyc_ledger.demo.util.FileUtil;
import com.kyc_ledger.demo.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
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
        KycRecord kycRecord = kycRepository.findByKycId(kycId
        ).orElseThrow(()-> new KycNotFoundException("KycId",kycId));
        try {
            String fileHash = HashUtil.hashFile(file);
            if(documentRepository.existsByFileHash(fileHash)) {
                throw new InvalidFileException("This document has already been uploaded");
            }
            String filePath = FileUtil.saveFile(file,uploadDir);
            Document document = new Document();
            document.setKycRecord(kycRecord);
            document.setDocumentType(Document.DocumentType.valueOf(documentType.toUpperCase()));
            document.setFileName(file.getOriginalFilename());
            document.setFilePath(filePath);
            document.setFileHash(fileHash);
            document.setMimeType(file.getContentType());
            document.setFileSize(file.getSize());
            documentRepository.save(document);
            return ApiResponseDTO.success("Document uploaded successfully",mapToDTO(document));

        }
        catch (IOException e) {
            throw new RuntimeException("Failed to upload document: "+e.getMessage());
        }
    }
    public ApiResponseDTO<List<DocumentDTO>> getDocumentsByKycId(String kycId) {

        KycRecord kycRecord = kycRepository.findByKycId(kycId
        ).orElseThrow(()-> new KycNotFoundException("kycId",kycId));
        List<Document> documents = documentRepository.findByKycRecord(kycRecord);
        List<DocumentDTO> dtos = documents.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ApiResponseDTO.success("Documents retrieved",dtos);
    }

}
