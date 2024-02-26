package com.example.publictransportation.Services;

import com.example.publictransportation.Model.PublicTransportRoute;
import com.example.publictransportation.Repository.PTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PTServices {

    @Autowired
    private PTRepository publicTransportRepository;

    public List<PublicTransportRoute>getAllRouts(){
        return publicTransportRepository.findAll();
    }
    public void createARoute(PublicTransportRoute transportRoute){
         publicTransportRepository.save(transportRoute);
         System.out.println(transportRoute);
    }

    /*public List<PublicTransportRoute>searchTransportation(String startPoint, String endPoint, boolean isAStation, LocalTime departureTime){

       publicTransportRepository.findPublicTransportRouteByTravelFromAndTravelTo(startPoint,endPoint);
        return PublicTransportRoute.class;

    }*/

    public PublicTransportRoute getTransportById(Long id){
        return publicTransportRepository.findPublicTransportRouteById(id);
    }

    public PublicTransportRoute getRouteByTravelFromAndTravelTo(String from, String to){
        if (!isStation(from) && !isStation(to)){
        PublicTransportRoute startDestinationRoute =getRouteToStation(from);
            PublicTransportRoute finalDestinationRoute =getRouteFromStation(to);

           // int totalDuration= routeCalculation(startDestinationRoute, finalDestinationRoute);

        }
        return  publicTransportRepository.findPublicTransportRouteByTravelFromAndTravelTo(from, to);
    }


    public List<PublicTransportRoute>getFavoriteRouts(){
        return publicTransportRepository.findPublicTransportRouteByFavorite(true);
    }

    public void updateFavoriteStatus(Long id, boolean status){
        PublicTransportRoute currentFavoriteRoute = publicTransportRepository.findPublicTransportRouteById(id);
        if (currentFavoriteRoute != null){
            currentFavoriteRoute.setFavorite(status);
            publicTransportRepository.save(currentFavoriteRoute);
        }
    }

    private PublicTransportRoute getRouteToStation(String from) {
        return null;
    }

    private PublicTransportRoute getRouteFromStation(String to) {
        return null;
    }
    public PublicTransportRoute getRouteByTravelFrom(String from, String to){
        PublicTransportRoute route= publicTransportRepository.findPublicTransportRouteByTravelFrom(from);
        if (!isStation(from)){
            return routeCalculation(from, to);
        }
        return route;
    }
    private boolean isStation(String location){
        return false;
    }
    //Needs to modify and implement calculation logik
    private PublicTransportRoute routeCalculation(String from, String to){
        return getRouteByTravelFrom(from, to);
    }
    public PublicTransportRoute getRouteByStarAndEndPoint(String start, String end){
       List<PublicTransportRoute> routes= publicTransportRepository.findPublicTransportRouteByStartPointAndEndPoint(start,end);
        return (PublicTransportRoute) routes;
    }
    public PublicTransportRoute getRouteByStart( String start){
        return publicTransportRepository.findPublicTransportRouteByStartPoint(start);
    }
    public PublicTransportRoute getRouteByEndpoint( String end){
        return publicTransportRepository.findPublicTransportRouteByEndPoint(end);
    }

    public PublicTransportRoute getRouteByTyp(String typ){
        return publicTransportRepository.findPublicTransportRouteByRouteTyp(typ);
    }

    public void  reportAnIssue(Long id, String issue, int estimatedDilation){
        PublicTransportRoute route = publicTransportRepository.findPublicTransportRouteById(id);
        if (route!=null){

            int newDuration=estimatedDilation + route.getDuration();
            route.setDelayingInfo(issue);
            route.setDuration(newDuration);
            publicTransportRepository.save(route);
        }
    }
    public List<PublicTransportRoute> getDelaysAndIssueInfo(){
        return publicTransportRepository.findPublicTransportRouteByDelayingInfoNotNull();
    }

}
