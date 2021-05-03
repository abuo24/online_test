package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Route;
import com.example.online_test.payload.RouteRequest;
import com.example.online_test.repository.QuestionRepository;
import com.example.online_test.repository.RouteRepository;
import com.example.online_test.repository.SubjectsRepository;
import com.example.online_test.repository.UniversityRepository;
import com.example.online_test.service.RouteService;
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
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    SubjectsRepository subjectsRepository;

    @Override
    public Route create(RouteRequest routeRequest) {
        try {
            Route route = new Route();
            route.setSubjectFirst(subjectsRepository.getOne(routeRequest.getSubjectFirst()));
            route.setSubjectSecond(subjectsRepository.getOne(routeRequest.getSubjectSecond()));
            route.setSubjectThird(subjectsRepository.getOne(routeRequest.getSubjectThird()));
            route.setName(routeRequest.getName());
route.setCode(routeRequest.getCode());
            return routeRepository.save(route);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Route edit(String id, RouteRequest routeRequest) {
        try {
            Route route = routeRepository.findById(id).get();
            if (route == null) {
                return null;
            }
            route.setSubjectFirst(subjectsRepository.getOne(routeRequest.getSubjectFirst()));
            route.setSubjectSecond(subjectsRepository.getOne(routeRequest.getSubjectSecond()));
            route.setSubjectThird(subjectsRepository.getOne(routeRequest.getSubjectThird()));
            route.setName(routeRequest.getName());

            route.setCode(routeRequest.getCode());
            return routeRepository.save(route);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Route> getAllRouteList() {
        try {
            return routeRepository.findAll();
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public Map getAllRouteListByPagealable(int page, int size) {
        try {
            List<Route> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Route> pageTuts = routeRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("routes", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Route getOneById(String id) {
        try {
            return routeRepository.findById(id).get();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<Route> getRoutesByFirstSubjectId(String id) {
        try {
            List<Route> routes = routeRepository.findAllBySubjectFirstId(id);
            return routes;
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public List<Route> getRoutesByFirstSubjectIdAndSecondSubjectIds(String id1, String id2) {
        try {
            List<Route> routes = routeRepository.findAllBySubjectFirstIdAndSubjectSecondId(id1,id2);
            return routes;
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public List<Route> getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(String id1, String id2, String id3) {
        try {
            List<Route> routes = routeRepository.findAllBySubjectFirstIdAndSubjectSecondIdAndSubjectThirdId(id1,id2, id3);
            return routes;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        try {
            routeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}

