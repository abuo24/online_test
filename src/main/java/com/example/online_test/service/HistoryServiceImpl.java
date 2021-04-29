package com.example.online_test.service;

import com.example.online_test.entity.History;
import com.example.online_test.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public Map getAllByPages(int page, int size){
            try {
                List<History> tutorials = new ArrayList<>();
                Pageable paging = PageRequest.of(page, size);
                Page<History> pageTuts = historyRepository.getAllByOrderByCreateAtDesc( paging);
                tutorials = pageTuts.getContent();
                Map<String, Object> response = new HashMap<>();
                response.put("histories", tutorials);
                response.put("currentPage", pageTuts.getNumber());
                response.put("totalItems", pageTuts.getTotalElements());
                response.put("totalPages", pageTuts.getTotalPages());
                return response;
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;

    }
}
