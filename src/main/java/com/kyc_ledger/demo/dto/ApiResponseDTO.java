package com.kyc_ledger.demo.dto;
import java.time.LocalDateTime;

public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponseDTO() {}

    public ApiResponseDTO(boolean success, String message, T data,
                          LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() {return timestamp; }

    public void setSuccess(boolean success) { this.success = success ;}
    public void setMessage(String message) { this.message = message;}
    public void setData(T data) { this.data = data ;}
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp;}


    public static <T> ApiResponseDTO<T> success (String message, T data) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());
        return response;

    }
    public static <T> ApiResponseDTO<T> error(String message) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

}
