package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.DashboardResponse;
import com.example.footballmanagement.service.DashboardService;
import com.example.footballmanagement.service.HomeService;
import com.example.footballmanagement.service.NewsService;
import com.example.footballmanagement.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final DashboardService dashboardService;
    private final HomeService homeService;
    private final NewsService newsService;
    private final GalleryService galleryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredPlayers", homeService.getFeaturedPlayers(4));
        model.addAttribute("latestMatches", homeService.getLatestMatches(5));
        model.addAttribute("topTeams", homeService.getTopTeams(4));
        model.addAttribute("statistics", homeService.getStatistics());
        model.addAttribute("latestNews", newsService.getLatestNews(3));
        model.addAttribute("gallery", galleryService.getLatestGalleries(6));
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login"; // Return login.html template
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register"; // Return register.html template
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardResponse dashboardData = dashboardService.getDashboardData();
        model.addAttribute("dashboardData", dashboardData);
        return "dashboard"; // Return dashboard.html template
    }
}
