package com.example.online_test.service;

import com.example.online_test.payload.ReqGroup;

import java.util.Map;

public interface GroupService {
    public Map getAllByPages(int page, int size);

    public Map getUserByGroupId(String id, int page, int size);

    public boolean addGroups(ReqGroup reqGroup);

    public boolean editGroup(ReqGroup reqGroup, String id);

    public boolean deleteGroup(String id);
}
