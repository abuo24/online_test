package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Groups;
import com.example.online_test.payload.ReqGroup;
import com.example.online_test.repository.GroupsRepository;
import com.example.online_test.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

     @Autowired
     GroupsRepository groupsRepository;

    @Override
    public HttpEntity<?> getAllGroups() {
        return new ResponseEntity<>(groupsRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public HttpEntity<?> getOneGroupsById(String id) {
        Optional<Groups> byId = groupsRepository.findById(id);
        if (!byId.isPresent()){
            return new ResponseEntity<>("Groups not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId.get(), HttpStatus.OK);
    }

    @Override
    public HttpEntity<?> addGroups(ReqGroup reqGroup) {
        Groups groups=new Groups();
        groups.setNameUz(reqGroup.getNameUz());
        groups.setNameRu(reqGroup.getNameRu());
        return new ResponseEntity<>(groupsRepository.save(groups), HttpStatus.OK);
    }

    @Override
    public HttpEntity<?> editGroup(ReqGroup reqGroup, String id) {
        Optional<Groups> byId = groupsRepository.findById(id);
        if (!byId.isPresent()){
            return new ResponseEntity<>("Groups not found", HttpStatus.NOT_FOUND);
        }
        Groups groups = byId.get();
        groups.setId(id);
        groups.setNameUz(reqGroup.getNameUz());
        groups.setNameRu(reqGroup.getNameRu());
        return new ResponseEntity<>(groupsRepository.save(groups), HttpStatus.OK);
    }

    @Override
    public HttpEntity<?> deleteGroup(String id) {
        Optional<Groups> byId = groupsRepository.findById(id);
        if (!byId.isPresent()){
            return new ResponseEntity<>("Groups not found", HttpStatus.NOT_FOUND);
        }
        groupsRepository.delete(byId.get());
        return new ResponseEntity<>("Successfully deleted groups", HttpStatus.NO_CONTENT);
    }
}
