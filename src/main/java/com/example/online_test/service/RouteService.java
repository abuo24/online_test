package com.example.online_test.service;

import com.example.online_test.entity.Route;
import com.example.online_test.payload.RouteRequest;
import com.example.online_test.payload.RouteResponse;

import java.util.List;
import java.util.Map;

public interface RouteService {
    public boolean create(RouteRequest routeRequest);
    public boolean edit(String id, RouteRequest routeRequest);
    public List<RouteResponse> getAllRouteList();
    public RouteResponse getOneById(String id);
    public List<RouteResponse> getRoutesByFirstSubjectId(String id);
    public List<RouteResponse> getRoutesByFirstSubjectIdAndSecondSubjectIds(String id1,String id2);
    public List<RouteResponse> getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(String id1,String id2, String id3);
    public boolean delete(String id);
    public Map getAllRouteListByPagealable(int page, int size);
}
