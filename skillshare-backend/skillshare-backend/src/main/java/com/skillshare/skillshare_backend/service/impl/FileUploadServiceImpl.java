package com.skillshare.skillshare_backend.service.impl;

import com.cloudinary.Cloudinary;
import com.skillshare.skillshare_backend.dto.response.FileUploadResponse;
import com.skillshare.skillshare_backend.service.interfaces.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final Cloudinary cloudinary;

    @Override
    public FileUploadResponse uploadImage(MultipartFile file)
            throws IOException {

        return uploadFile(
                file,
                "skillshare/images",
                "image"
        );
    }

    @Override
    public FileUploadResponse uploadVideo(MultipartFile file)
            throws IOException {

        return uploadFile(
                file,
                "skillshare/videos",
                "video"
        );
    }

    @Override
    public FileUploadResponse uploadDocument(MultipartFile file)
            throws IOException {

        return uploadFile(
                file,
                "skillshare/documents",
                "raw"
        );
    }
        @Override
    @SuppressWarnings("unchecked")
    public FileUploadResponse uploadFile(
            MultipartFile file,
            String folder,
            String resourceType
    ) throws IOException {

        var result = cloudinary.uploader().upload(
                file.getBytes(),
                com.cloudinary.utils.ObjectUtils.asMap(
                        "folder", folder,
                        "resource_type", resourceType
                )
        );

        return mapToResponse(file, result);
    }

    @Override
    public void deleteFile(String publicId)
            throws IOException {

        cloudinary.uploader().destroy(
                publicId,
                com.cloudinary.utils.ObjectUtils.emptyMap()
        );
    }

    private FileUploadResponse mapToResponse(
            MultipartFile file,
            java.util.Map<?, ?> result
    ) {

        Object width = result.get("width");
        Object height = result.get("height");
        Object bytes = result.get("bytes");

        return FileUploadResponse.builder()
                .publicId((String) result.get("public_id"))
                .originalFileName(file.getOriginalFilename())
                .fileUrl((String) result.get("secure_url"))
                .fileFormat((String) result.get("format"))
                .resourceType((String) result.get("resource_type"))
                .fileSize(
                        bytes == null ? null :
                                ((Number) bytes).longValue()
                )
                .width(
                        width == null ? null :
                                ((Number) width).intValue()
                )
                .height(
                        height == null ? null :
                                ((Number) height).intValue()
                )
                .uploadedAt(java.time.LocalDateTime.now())
                .build();
    }

}