package com.shank.springanalytics.service;
import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;
import com.shank.springanalytics.model.DataRecord;
@Service 
public class Forecast {
	public List<DataRecord> forecastNextMonths(int n, List<DataRecord> historicalRecords) {
	    // Map of (year, month number) to total quantity across all companies
	    // Using TreeMap to keep sorted order
	    TreeMap<Integer, BigDecimal> monthIndexToQuantity = new TreeMap<>();

	    // Helper to convert month string to month number (Jan=1,...Dec=12)
	    Map<String, Integer> monthMap = Map.ofEntries(
	        Map.entry("Jan", 1), Map.entry("Feb", 2), Map.entry("Mar", 3), Map.entry("Apr", 4),
	        Map.entry("May", 5), Map.entry("Jun", 6), Map.entry("Jul", 7), Map.entry("Aug", 8),
	        Map.entry("Sep", 9), Map.entry("Oct", 10), Map.entry("Nov", 11), Map.entry("Dec", 12)
	    );

	    // Convert year+month into a linear month index (e.g. Jan 2020 = 0, Feb 2020 =1, ...):
	    int baseYear = historicalRecords.stream().mapToInt(r -> r.getYear().getValue()).min().orElse(2020);

	    for (DataRecord r : historicalRecords) {
	        Integer mNum = monthMap.getOrDefault(r.getMonth(), 0);
	        if (mNum == 0) continue; // skip unknown month strings

	        int monthIndex = (r.getYear().getValue() - baseYear) * 12 + (mNum - 1);
	        monthIndexToQuantity.put(monthIndex,
	            monthIndexToQuantity.getOrDefault(monthIndex, BigDecimal.ZERO).add(r.getQmt()));
	    }

	    // If less than 2 points, can’t do trend forecasting, return empty
	    if (monthIndexToQuantity.size() < 2) {
	        System.out.println("Not enough data to forecast.");
	        return List.of();
	    }

	    // Calculate simple linear regression slope and intercept (y = a + b*x)
	    int nPoints = monthIndexToQuantity.size();
	    double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
	    @SuppressWarnings("unused")
		int i = 0;
	    for (Map.Entry<Integer, BigDecimal> entry : monthIndexToQuantity.entrySet()) {
	        double x = entry.getKey();
	        double y = entry.getValue().doubleValue();
	        sumX += x;
	        sumY += y;
	        sumXY += x * y;
	        sumX2 += x * x;
	        i++;
	    }

	    double slope = (nPoints * sumXY - sumX * sumY) / (nPoints * sumX2 - sumX * sumX);
	    double intercept = (sumY - slope * sumX) / nPoints;

	    // Forecast next n months
	    List<DataRecord> forecast = new ArrayList<>();
	    int lastMonthIndex = monthIndexToQuantity.lastKey();

	    // Reverse monthMap for month number to name
	    String[] monthNames = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

	    for (int k = 1; k <= n; k++) {
	        int forecastMonthIndex = lastMonthIndex + k;
	        double predictedValue = intercept + slope * forecastMonthIndex;

	        int yearVal = baseYear + (forecastMonthIndex / 12);
	        int monthVal = (forecastMonthIndex % 12);

	        String forecastMonth = monthNames[monthVal];

	        // Create a DataRecord for forecast - oilCompany can be "Forecast" or empty
	        DataRecord forecastRecord = new DataRecord(forecastMonth, Year.of(yearVal), "Forecast", BigDecimal.valueOf(predictedValue));
	        forecast.add(forecastRecord);
	    }

	    return forecast;
	}}