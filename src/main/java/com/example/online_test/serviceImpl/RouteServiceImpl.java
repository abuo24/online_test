package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Route;
import com.example.online_test.entity.SubHelp;
import com.example.online_test.entity.Subjects;
import com.example.online_test.payload.RouteRequest;
import com.example.online_test.payload.RouteResponse;
import com.example.online_test.payload.SubjectRequest;
import com.example.online_test.payload.SubjectResHelp;
import com.example.online_test.repository.*;
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
    SubjectsRepository subjectsRepository;

    @Override
    public boolean create(RouteRequest routeRequest) {
        try {
            Route route = new Route();
            route.setSubjectFirst(subjectsRepository.getOne(routeRequest.getSubjectFirst()));
            route.setSubjectSecond(subjectsRepository.getOne(routeRequest.getSubjectSecond()));
            route.setSubjectThird(subjectsRepository.getOne(routeRequest.getSubjectThird()));
            route.setName(routeRequest.getName());
            route.setCode(routeRequest.getCode());
            return routeRepository.save(route) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Map getAllRouteListByPagealable(int page, int size) {
        try {
            List<Route> routeList = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Route> pageTuts = routeRepository.findAllByOrderByCreateAtDesc(paging);
            routeList = pageTuts.getContent();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<RouteResponse> routeResponses = new ArrayList<>();
            RouteResponse routeResponse = new RouteResponse();
            for (int i = 0; i < routeList.size(); i++) {
                routeResponse = new RouteResponse();
                routeResponse.setCode(routeList.get(i).getCode());
                routeResponse.setId(routeList.get(i).getId());
                routeResponse.setName(routeList.get(i).getName());
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectFirst().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectFirst().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectFirst().getId());
                routeResponse.setSubjectFirst(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectSecond().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectSecond().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectSecond().getId());
                routeResponse.setSubjectSecond(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectThird().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectThird().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectThird().getId());
                routeResponse.setSubjectThird(subjectResHelp);
                routeResponses.add(routeResponse);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("routes", routeResponses);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean edit(String id, RouteRequest routeRequest) {
        try {
            Route route = routeRepository.findById(id).get();
            if (route == null) {
                return false;
            }
            route.setSubjectFirst(subjectsRepository.getOne(routeRequest.getSubjectFirst()));
            route.setSubjectSecond(subjectsRepository.getOne(routeRequest.getSubjectSecond()));
            route.setSubjectThird(subjectsRepository.getOne(routeRequest.getSubjectThird()));
            route.setName(routeRequest.getName());
            route.setCode(routeRequest.getCode());

            return routeRepository.save(route) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<RouteResponse> getAllRouteList() {
        try {
            List<Route> routeList = routeRepository.findAll();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<RouteResponse> routeResponses = new ArrayList<>();
            RouteResponse routeResponse = new RouteResponse();
            for (int i = 0; i < routeList.size(); i++) {
                routeResponse = new RouteResponse();
                routeResponse.setCode(routeList.get(i).getCode());
                routeResponse.setId(routeList.get(i).getId());
                routeResponse.setName(routeList.get(i).getName());
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectFirst().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectFirst().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectFirst().getId());
                routeResponse.setSubjectFirst(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectSecond().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectSecond().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectSecond().getId());
                routeResponse.setSubjectSecond(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectThird().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectThird().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectThird().getId());
                routeResponse.setSubjectThird(subjectResHelp);
                routeResponses.add(routeResponse);
            }
            return routeResponses;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public RouteResponse getOneById(String id) {
        try {
            Route routeList = routeRepository.findById(id).get();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            RouteResponse routeResponse = new RouteResponse();
            routeResponse = new RouteResponse();
            routeResponse.setCode(routeList.getCode());
            routeResponse.setName(routeList.getName());
            routeResponse.setId(routeList.getId());
            subjectResHelp = new SubjectResHelp();
            subjectResHelp.setNameRu(routeList.getSubjectFirst().getNameRu());
            subjectResHelp.setNameUz(routeList.getSubjectFirst().getNameUz());
            subjectResHelp.setId(routeList.getSubjectFirst().getId());
            routeResponse.setSubjectFirst(subjectResHelp);
            subjectResHelp = new SubjectResHelp();
            subjectResHelp.setNameRu(routeList.getSubjectSecond().getNameRu());
            subjectResHelp.setNameUz(routeList.getSubjectSecond().getNameUz());
            subjectResHelp.setId(routeList.getSubjectSecond().getId());
            routeResponse.setSubjectSecond(subjectResHelp);
            subjectResHelp = new SubjectResHelp();
            subjectResHelp.setNameRu(routeList.getSubjectThird().getNameRu());
            subjectResHelp.setNameUz(routeList.getSubjectThird().getNameUz());
            subjectResHelp.setId(routeList.getSubjectThird().getId());
            routeResponse.setSubjectThird(subjectResHelp);

            return routeResponse;
        } catch(
                Exception e)

        {
        }
        return null;
    }

    @Override
    public List<RouteResponse> getRoutesByFirstSubjectId(String id) {
        try {
            List<Route> routeList = routeRepository.findAllBySubjectFirstId(id);
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<RouteResponse> routeResponses = new ArrayList<>();
            RouteResponse routeResponse = new RouteResponse();
            for (int i = 0; i < routeList.size(); i++) {
                routeResponse = new RouteResponse();
                routeResponse.setCode(routeList.get(i).getCode());
                routeResponse.setId(routeList.get(i).getId());
                routeResponse.setName(routeList.get(i).getName());
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectFirst().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectFirst().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectFirst().getId());
                routeResponse.setSubjectFirst(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectSecond().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectSecond().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectSecond().getId());
                routeResponse.setSubjectSecond(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectThird().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectThird().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectThird().getId());
                routeResponse.setSubjectThird(subjectResHelp);
                routeResponses.add(routeResponse);
            }
            return routeResponses;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<RouteResponse> getRoutesByFirstSubjectIdAndSecondSubjectIds(String id1, String id2) {
        try {
            List<Route> routeList = routeRepository.findAllBySubjectFirstIdAndSubjectSecondId(id1, id2);
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<RouteResponse> routeResponses = new ArrayList<>();
            RouteResponse routeResponse = new RouteResponse();
            for (int i = 0; i < routeList.size(); i++) {
                routeResponse = new RouteResponse();
                routeResponse.setCode(routeList.get(i).getCode());
                routeResponse.setId(routeList.get(i).getId());
                routeResponse.setName(routeList.get(i).getName());
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectFirst().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectFirst().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectFirst().getId());
                routeResponse.setSubjectFirst(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectSecond().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectSecond().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectSecond().getId());
                routeResponse.setSubjectSecond(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectThird().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectThird().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectThird().getId());
                routeResponse.setSubjectThird(subjectResHelp);
                routeResponses.add(routeResponse);
            }
            return routeResponses;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<RouteResponse> getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(String id1, String id2, String id3) {
        try {
            List<Route> routeList = routeRepository.findAllBySubjectFirstIdAndSubjectSecondIdAndSubjectThirdId(id1, id2, id3);
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<RouteResponse> routeResponses = new ArrayList<>();
            RouteResponse routeResponse = new RouteResponse();
            for (int i = 0; i < routeList.size(); i++) {
                routeResponse = new RouteResponse();
                routeResponse.setCode(routeList.get(i).getCode());
                routeResponse.setId(routeList.get(i).getId());
                routeResponse.setName(routeList.get(i).getName());
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectFirst().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectFirst().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectFirst().getId());
                routeResponse.setSubjectFirst(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectSecond().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectSecond().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectSecond().getId());
                routeResponse.setSubjectSecond(subjectResHelp);
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setNameRu(routeList.get(i).getSubjectThird().getNameRu());
                subjectResHelp.setNameUz(routeList.get(i).getSubjectThird().getNameUz());
                subjectResHelp.setId(routeList.get(i).getSubjectThird().getId());
                routeResponse.setSubjectThird(subjectResHelp);
                routeResponses.add(routeResponse);
            }
            return routeResponses;
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

