package com.example.online_test.service;

import com.example.online_test.entity.Blok;
import com.example.online_test.entity.History;
import com.example.online_test.payload.BlokRequest;
import com.example.online_test.payload.VerifingRequest;

public interface BlokService {
    public Blok create(String userId, BlokRequest blokRequest);
    public Blok isProcessingBlokWithUserId(String userId);
    public History verifyAllTests(String userId, String blokId, VerifingRequest verifingRequest);
}
