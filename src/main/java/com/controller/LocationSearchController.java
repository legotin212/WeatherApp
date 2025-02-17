package com.controller;

import com.dto.response.LocationResponseDto;
import com.entity.User;
import com.service.LocationService;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LocationSearchController {
    private final LocationService locationService;
    private final UserService userService;


    @GetMapping("/locationSearch")
    public String search(@RequestParam(name = "locationName" ) String locationName, Model model,
                         @CookieValue("SESSIONID") String sessionId) {
        User user = userService.getUser(UUID.fromString(sessionId));

        List<LocationResponseDto> locations = locationService.getAvailableLocationsByName(locationName, user);
        model.addAttribute("locations", locations);
        return "search-results";
    }

    @PostMapping("/addLocation")
    public String addLocation(@ModelAttribute(name = "locationDto" ) LocationResponseDto locationDto,
                              @CookieValue("SESSIONID") String sessionId) {
        User user = userService.getUser(UUID.fromString(sessionId));
        locationService.saveLocation(user, locationDto);
        return "redirect:/home";
    }
}
