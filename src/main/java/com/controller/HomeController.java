package com.controller;

import com.dto.LocationDeleteRequest;
import com.dto.WeatherDto;
import com.entity.User;
import com.service.LocationService;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final LocationService locationService;
    @GetMapping("/home")
    public String home(@CookieValue("SESSIONID") String sessionId, Model model) {
        User user = userService.getUser(UUID.fromString(sessionId));
        List<WeatherDto> weatherList = locationService.getWeatherList(user);
        model.addAttribute("username", user.getLogin());
        model.addAttribute("weatherList",weatherList);
        model.addAttribute("locationDeleteRequest", new LocationDeleteRequest());
        return "index";
    }

    @PostMapping("/deleteLocation")
    public String deleteLocation(@CookieValue("SESSIONID") String sessionId,
                                 @ModelAttribute("locationDeleteRequest") LocationDeleteRequest locationDeleteRequest,
                                 Model model) {
        User user = userService.getUser(UUID.fromString(sessionId));
        locationService.removeLocation(user, locationDeleteRequest);
        return "redirect:/home";

    }
}
