package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.FileUploadResponse;
import com.skillshare.skillshare_backend.service.interfaces.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    // ==========================================
    // Upload Image
    // ==========================================

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadImage(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        FileUploadResponse response =
                fileUploadService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FileUploadResponse>builder()
                        .success(true)
                        .message("Image uploaded successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ==========================================
    // Upload Video
    // ==========================================

    @PostMapping("/video")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadVideo(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        FileUploadResponse response =
                fileUploadService.uploadVideo(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FileUploadResponse>builder()
                        .success(true)
                        .message("Video uploaded successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ==========================================
    // Upload Document
    // ==========================================

    @PostMapping("/document")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadDocument(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        FileUploadResponse response =
                fileUploadService.uploadDocument(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FileUploadResponse>builder()
                        .success(true)
                        .message("Document uploaded successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // ==========================================
    // Delete File
    // ==========================================

    @DeleteMapping("/{publicId}")
    public ResponseEntity<ApiResponse<String>> deleteFile(
            @PathVariable String publicId
    ) throws IOException {

        fileUploadService.deleteFile(publicId);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("File deleted successfully")
                        .data(publicId)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}