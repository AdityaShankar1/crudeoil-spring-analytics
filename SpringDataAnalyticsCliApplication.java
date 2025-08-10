package com.shank.springanalytics;
import com.shank.springanalytics.model.DataRecord;
import com.shank.springanalytics.service.DataRecordService;
import com.shank.springanalytics.service.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringDataAnalyticsCliApplication implements CommandLineRunner {

    @Autowired
    private DataRecordService dataRecordService;

    @Autowired
    private Forecast forecast;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataAnalyticsCliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            // Load records
            List<DataRecord> records = dataRecordService.getAllRecords();

            // Always do basic stats
            dataRecordService.printBasicStats(records);

            // If forecast works, show it
            if (!records.isEmpty()) {
                List<DataRecord> forecasted = forecast.forecastNextMonths(3, records);
                System.out.println("\n=== Forecast for Next 3 Months ===");
                forecasted.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }
    }
}