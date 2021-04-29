package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Route;
import com.example.online_test.entity.SubHelp;
import com.example.online_test.entity.Subjects;
import com.example.online_test.payload.RouteRequest;
import com.example.online_test.payload.SubjectRequest;
import com.example.online_test.repository.*;
import com.example.online_test.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            route.setUniversity(universityRepository.findById(routeRequest.getUniversityId()).get());

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
            route.setUniversity(universityRepository.findById(routeRequest.getUniversityId()).get());

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
    public List<Route> getRoutesByFirstSubjectIdAndSecondSubjectIds(String id1,String id2) {
        try {
            List<Route> routes = routeRepository.findAllBySubjectFirstIdAndSubjectSecondId(id1,id2);
            return routes;
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    public List<Route> getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(String id1,String id2, String id3) {
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

