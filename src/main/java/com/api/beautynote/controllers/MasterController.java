package com.api.beautynote.controllers;

import com.api.beautynote.controllers.dto.responses.MasterArrayDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterController {

  @GetMapping("/masters")
  public MasterArrayDto getAllMasters(
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String region,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) List<Long> types,
      @RequestParam(required = false) List<Long> services
  ) {

    System.out.println("Country: " + country);
    System.out.println("Region: " + region);
    System.out.println("City: " + city);
    System.out.println("Types: " + types);
    System.out.println("Services: " + services);

    return new MasterArrayDto();
  }

  /**
   * 	/masters
   * 	/masters/services
   * 	/masters/{masterId}
   * 	/masters/{masterId}/services
   * 	/masters/{masterId}/services/{serviceId}
   * 	/masters/{masterId}/services/{serviceId}/reviews
   * 	/masters/slots
   * 	/masters/slots/{slotId}
   */
}
