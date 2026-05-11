package com.bookcase.bookcase.dashboard.controller;

import com.bookcase.bookcase.dashboard.DTO.DashboardResponseDto;
import com.bookcase.bookcase.dashboard.sevice.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponseDto getDashBoard() {
        return dashboardService.getDashboard();
    }

}
