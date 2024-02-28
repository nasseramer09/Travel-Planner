package com.example.publictransportation.Controller;

import com.example.publictransportation.Model.FakeCity;
import com.example.publictransportation.Services.PTServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Controller
@RequestMapping("api/v1/publicTransportation")
public class PTController {
    @Autowired
   private PTServices ptServices;
     @PostMapping()
    public ResponseEntity<String> createRoute(@RequestBody FakeCity fakeCity){
    try {
        ptServices.createARoute(fakeCity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Route created successfully");
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred during route creation");
    }
    }

  @PostMapping("searchMunicipalTransport")
    public ResponseEntity<String>searchForTransportation(@RequestBody  String startPoint, String endPoint, LocalTime departureTime){


        try {
            ptServices.getRouteByTravelFromAndTravelTo(startPoint, endPoint, departureTime );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went wrong ");
        }

    }
    @GetMapping("allTransportation")
    public ResponseEntity<List<Map<String, Object>>> getAllPublicTransportation(){

        List<FakeCity> routes = ptServices.getAllRouts();
        if (routes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<Map<String, Object>> routeInfo= new ArrayList<>();
        for (FakeCity route:routes
             ) {
            Map<String, Object>info= new HashMap<>();
            info.put("Station Name", route.getStationName());
            info.put("Transport typ", route.getTransportTyp());
            info.put("Departure time", route.getDepartureTime());
            info.put("Arrival time", route.getArrivalTime());
            info.put("Changes", route.getChanges());
            info.put("Travel Duration", route.getTravelDuration());
            info.put("Issues or announced dilations Duration", route.getDelayingInfo());
            info.put("Estimated dilation's time", route.getDelayingTimeEstimation());
            routeInfo.add(info);
        }
        return ResponseEntity.ok(routeInfo);
    }
    @GetMapping("searchForTransportation")
    public ResponseEntity<Map<String,Object>>transportationSearch(@RequestParam String from, @RequestParam String to, @RequestParam String travelTime){
        try{
        FakeCity routes = ptServices.getRouteByTravelFromAndTravelTo(from, to, LocalTime.parse(travelTime));

        if (routes!=null){
            Map<String, Object> info = new HashMap<>();

            info.put("Station Name", routes.getStationName());
            info.put("Transport typ", routes.getTransportTyp());
            info.put("Departure time", routes.getDepartureTime());
            info.put("Arrival time", routes.getArrivalTime());
            info.put("Changes", routes.getChanges());
            info.put("Travel Duration", routes.getTravelDuration());
            info.put("Issues or announced dilations", routes.getDelayingInfo());
            info.put("Estimated dilation minuts", routes.getDelayingTimeEstimation());
            return ResponseEntity.ok(info);
        }else {
            return ResponseEntity.notFound().build();
        }
       }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("favoriteTransportation")
    public ResponseEntity<List<FakeCity>>getAllFavoritePublicTransportationRoute(){
        List<FakeCity> favoriteRoutes = ptServices.getFavoriteRouts();
        if (favoriteRoutes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(favoriteRoutes);
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> changeFavoriteStatus(@PathVariable Long id,@RequestBody boolean updateFavorite){

    try {
        ptServices.updateFavoriteStatus(id, updateFavorite);
        return ResponseEntity.ok("Favorite status updated ");
    }catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while updating status");
    }
    }

    @PostMapping("reportIssue/{id}")
    public ResponseEntity<String>reportIssueAndDilation(@PathVariable Long id, @RequestParam String issue, @RequestParam int estimatedDilation){
        try {
           // LocalTime estimatedDilationTime=LocalTime.parse(estimatedDilation);
            ptServices.reportAnIssue(id, issue, estimatedDilation);
            return ResponseEntity.ok("Issue has been reported successfully ");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while reporting an issue ");
        }
    }

    @GetMapping("dilationAndIssues")
    public ResponseEntity<List<FakeCity>>getDilationsAndIssues(){
        List<FakeCity> dilationsAndIssues=ptServices.getDelaysAndIssueInfo();
        return ResponseEntity.ok(dilationsAndIssues);
    }

}
