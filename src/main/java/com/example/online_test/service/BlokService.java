package com.example.online_test.service;

import com.example.online_test.entity.Blok;
import com.example.online_test.entity.History;
import com.example.online_test.entity.User;
import com.example.online_test.payload.BlokRequest;

import java.util.Map;

public interface BlokService {
    public Blok create(String userId, BlokRequest blokRequest);
    public Map isProcessingBlokWithUserId(String userId);
    public History verifyAllTests(String userId, String blokId);
    public boolean saveAnswerReq(User user, String qId, String correctAnswerId);
}
