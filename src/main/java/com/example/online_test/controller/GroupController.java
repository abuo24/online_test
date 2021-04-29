package com.example.online_test.controller;

import com.example.online_test.payload.ReqCourse;
import com.example.online_test.payload.ReqGroup;
import com.example.online_test.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("/get/groupsList")
    public HttpEntity<?> getGroupList(){
        return groupService.getAllGroups();
    }
    @GetMapping("/get/groups/{id}")
    public HttpEntity<?> getGroupsById(@PathVariable String id){
        return groupService.getOneGroupsById(id);
    }
    @PostMapping("/add/groups")
    public HttpEntity<?> addGroups(@RequestBody ReqGroup reqGroup){
        return groupService.addGroups(reqGroup);
    }
    @PutMapping("/edit/groups/{id}")
    public HttpEntity<?> editGroups(@RequestBody ReqGroup reqGroup, @PathVariable String id){
        return groupService.editGroup(reqGroup,id);
    }
    @DeleteMapping("/delete/groups/{id}")
    public HttpEntity<?> deleteGroups(@PathVariable String id){
        return groupService.deleteGroup(id);
    }

}
