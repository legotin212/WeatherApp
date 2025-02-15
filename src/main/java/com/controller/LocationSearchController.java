package com.controller;

import com.dto.LocationDto;
import com.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LocationSearchController {
    private final LocationService locationService;


    @GetMapping("/locationSearch")
    public String search(@RequestParam(name = "locationName" ) String locationName, Model model ) {
        List<LocationDto> locations = locationService.getLocationsByName(locationName);
        model.addAttribute("locations", locations);
        return "search-results";
    }

    @PostMapping("/addLocation")
    public String addLocation(@ModelAttribute(name = "locationDto" ) LocationDto locationDto,
                              @CookieValue("SESSIONID") String sessionId,
                              Model model) {

        return "search-results";
    }
}
