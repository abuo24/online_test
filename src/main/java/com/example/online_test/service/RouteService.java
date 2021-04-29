package com.example.online_test.service;

import com.example.online_test.entity.Route;
import com.example.online_test.payload.RouteRequest;

import java.util.List;

public interface RouteService {
    public Route create(RouteRequest routeRequest);
    public Route edit(String id, RouteRequest routeRequest);
    public List<Route> getAllRouteList();
    public Route getOneById(String id);
    public List<Route> getRoutesByFirstSubjectId(String id);
    public List<Route> getRoutesByFirstSubjectIdAndSecondSubjectIds(String id1,String id2);
    public List<Route> getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(String id1,String id2, String id3);
    public boolean delete(String id);
}
