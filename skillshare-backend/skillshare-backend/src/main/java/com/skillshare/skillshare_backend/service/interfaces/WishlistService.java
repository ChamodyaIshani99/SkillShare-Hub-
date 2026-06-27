package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.WishlistRequest;
import com.skillshare.skillshare_backend.dto.response.WishlistResponse;

import java.util.List;

public interface WishlistService {

    // ==========================================
    // Student APIs
    // ==========================================

    // Add course to wishlist
    WishlistResponse addToWishlist(WishlistRequest request);

    // Remove from wishlist
    void removeFromWishlist(String wishlistId);

    // Get my wishlist
    List<WishlistResponse> getMyWishlist();

    // Check whether a course is in my wishlist
    boolean isInWishlist(String courseId);

    // ==========================================
    // Public APIs
    // ==========================================

    // Get wishlist item by ID
    WishlistResponse getById(String wishlistId);

    // ==========================================
    // Admin APIs
    // ==========================================

    // Get all wishlist items
    List<WishlistResponse> getAllWishlist();

}