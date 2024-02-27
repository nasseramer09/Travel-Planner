package com.example.publictransportation.Repository;

import com.example.publictransportation.Model.FakeCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListResourceBundle;

@Repository
public interface FakeCityRepository extends JpaRepository<FakeCity, Long> {

   List<FakeCity> findByTransportTyp(String typ);
    FakeCity findAllById(Long id);

    FakeCity findByStationNameIgnoreCase(String name);

    List<FakeCity>findByChanges(int changes);

    List<FakeCity>findByDelayingInfoNotNull();
    List<FakeCity>findByFavorite(boolean favorite);





}
