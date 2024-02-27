package com.example.publictransportation.Controller;

import com.example.publictransportation.Model.FakeCity;
import com.example.publictransportation.Services.PTServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

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
            ptServices.getRouteByTravelFromAndTravelTo(startPoint, endPoint );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some thing went wrong ");
        }

    }
    @GetMapping("allTransportation")
    public ResponseEntity<List<FakeCity>> getAllPublicTransportation(){
        List<FakeCity> routes = ptServices.getAllRouts();
        if (routes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(routes);
    }
    @GetMapping("searchForTransportation")
    public ResponseEntity<FakeCity>getAllPublicTransportationBasedOnFromAndTo(@RequestParam String from, @RequestParam String to){
        FakeCity routes = ptServices.getRouteByTravelFromAndTravelTo(from, to);
        return ResponseEntity.ok(routes);
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
    public ResponseEntity<String>reportIssueAndDilation(@PathVariable Long id, @RequestParam String issue, @RequestParam String estimatedDilation){
        try {
            LocalTime estimatedDilationTime=LocalTime.parse(estimatedDilation);
            ptServices.reportAnIssue(id, issue, estimatedDilationTime);
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
