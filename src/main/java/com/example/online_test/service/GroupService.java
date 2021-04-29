package com.example.online_test.service;

import com.example.online_test.payload.ReqGroup;
import org.springframework.http.HttpEntity;

public interface GroupService {

    public HttpEntity<?> getAllGroups();

    public HttpEntity<?> getOneGroupsById(String id);

    public HttpEntity<?> addGroups(ReqGroup reqGroup);

    public HttpEntity<?> editGroup(ReqGroup reqGroup, String id);

    public HttpEntity<?> deleteGroup(String id);

}
