package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.request.WishlistRequest;
import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.WishlistResponse;
import com.skillshare.skillshare_backend.service.interfaces.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WishlistController {

    private final WishlistService wishlistService;

    // ==========================================
    // Student APIs
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<WishlistResponse>> addToWishlist(
            @Valid @RequestBody WishlistRequest request) {

        WishlistResponse response = wishlistService.addToWishlist(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<WishlistResponse>builder()
                        .success(true)
                        .message("Course added to wishlist successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeFromWishlist(
            @PathVariable String id) {

        wishlistService.removeFromWishlist(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Course removed from wishlist successfully")
                        .data("Wishlist item removed successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getMyWishlist() {

        List<WishlistResponse> response = wishlistService.getMyWishlist();

        return ResponseEntity.ok(
                ApiResponse.<List<WishlistResponse>>builder()
                        .success(true)
                        .message("Wishlist retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WishlistResponse>> getWishlistById(
            @PathVariable String id) {

        WishlistResponse response = wishlistService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<WishlistResponse>builder()
                        .success(true)
                        .message("Wishlist item retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/check/{courseId}")
    public ResponseEntity<ApiResponse<Boolean>> isInWishlist(
            @PathVariable String courseId) {

        Boolean response = wishlistService.isInWishlist(courseId);

        return ResponseEntity.ok(
                ApiResponse.<Boolean>builder()
                        .success(true)
                        .message("Wishlist status retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Admin APIs
    // ==========================================

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getAllWishlist() {

        List<WishlistResponse> response = wishlistService.getAllWishlist();

        return ResponseEntity.ok(
                ApiResponse.<List<WishlistResponse>>builder()
                        .success(true)
                        .message("All wishlist items retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}