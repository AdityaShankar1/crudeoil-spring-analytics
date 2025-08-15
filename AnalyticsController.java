package com.shank.springanalytics.controller;

import com.shank.springanalytics.dto.SummaryDto;
import com.shank.springanalytics.service.DataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {

    @Autowired
    private DataRecordService service;

    @GetMapping("/api/analytics/summary")
    public SummaryDto getSummary() {
        return service.computeSummary();
    }
}