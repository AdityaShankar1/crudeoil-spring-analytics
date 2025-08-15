package com.shank.springanalytics.service;

import com.shank.springanalytics.dto.SummaryDto;
import com.shank.springanalytics.model.DataRecord;
import com.shank.springanalytics.repository.DataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.Year;

@Service
public class DataRecordService {

    @Autowired
    private DataRecordRepository repository;
    @Autowired
    private Forecast forecast;
    
    public List<DataRecord> getAllRecords() {
        List<DataRecord> records;
        try {
            records = repository.findAll();
            if (records.isEmpty()) {
                records = loadFromCsv();
                System.out.println("Loaded data from CSV fallback");
            }
        } catch (Exception e) {
            System.out.println("Failed to load from DB, falling back to CSV");
            records = loadFromCsv();
        }
        return records;
    }

    private List<DataRecord> loadFromCsv() {
        String csvFile = "C:\\Users\\shank\\Desktop\\Crude Oil Production.csv";
        List<DataRecord> records = new ArrayList<>();
        
        System.out.println("Attempting to load data from: " + csvFile);

        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvFile))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) { // Skip header
                    System.out.println("Skipping header: " + line);
                    continue;
                }
                
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                
                try {
                    // First, handle the CSV line with a simpler approach
                    // Remove all quotes and split on commas
                    String[] fields = line.replaceAll("\"", "").split(",");
                    if (fields.length < 4) {
                        System.err.println("Warning: Line " + lineNumber + " has insufficient fields: " + line);
                        continue;
                    }

                    String month = fields[0].trim().replaceAll("^\"|\"$", ""); // Remove quotes if present
                    int yearValue = Integer.parseInt(fields[1].trim().replaceAll("^\"|\"$", ""));
                    Year year = Year.of(yearValue);
                    String oilCompany = fields[2].trim().replaceAll("^\"|\"$", "");
                    String quantityStr = fields[3].trim().replaceAll("^\"|\"$", "");
                    
                    if (quantityStr.isEmpty()) {
                        System.err.println("Warning: Empty quantity in line " + lineNumber);
                        continue;
                    }
                    
                    BigDecimal quantity = new BigDecimal(quantityStr);
                    DataRecord record = new DataRecord(month, year, oilCompany, quantity);
                    records.add(record);
                    
                } catch (Exception e) {
                    System.err.println("Error processing line " + lineNumber + ": " + line);
                    System.err.println("Error details: " + e.getMessage());
                }
            }
            
            System.out.println("Successfully loaded " + records.size() + " records from CSV");
            
        } catch (Exception e) {
            System.err.println("FATAL ERROR reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return records;
    }
    public void printBasicStats(List<DataRecord> records) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CRUDE OIL PRODUCTION STATISTICS");
        System.out.println("=".repeat(80));
        
        if (records.isEmpty()) {
            System.out.println("No records found to analyze.");
            return;
        }
        
        // Basic statistics
        BigDecimal min = records.stream()
                .map(DataRecord::getQmt)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = records.stream()
                .map(DataRecord::getQmt)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        double avg = records.stream()
                .mapToDouble(r -> r.getQmt().doubleValue())
                .average()
                .orElse(0.0);

        double variance = records.stream()
                .mapToDouble(r -> Math.pow(r.getQmt().doubleValue() - avg, 2))
                .average()
                .orElse(0.0);

        double stdDev = Math.sqrt(variance);
        
        // Additional statistics
        long totalRecords = records.size();
        
        // Find the record with maximum production
        DataRecord maxRecord = records.stream()
            .max((r1, r2) -> r1.getQmt().compareTo(r2.getQmt()))
            .orElse(null);
            
        // Find the record with minimum production
        DataRecord minRecord = records.stream()
            .min((r1, r2) -> r1.getQmt().compareTo(r2.getQmt()))
            .orElse(null);
        
        // Calculate total production
        BigDecimal totalProduction = records.stream()
            .map(DataRecord::getQmt)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Print the statistics
        System.out.println("\n" + "-".repeat(80));
        System.out.println("SUMMARY STATISTICS");
        System.out.println("-".repeat(80));
        System.out.printf("Total Records Analyzed: %,d%n", totalRecords);
        System.out.printf("Total Production (000 metric tonnes): %,.2f%n", totalProduction);
        
        System.out.println("\n" + "-".repeat(80));
        System.out.println("PRODUCTION STATISTICS (in 000 metric tonnes)");
        System.out.println("-".repeat(80));
        System.out.printf("Minimum Production: %,.2f%n", min);
        System.out.printf("Maximum Production: %,.2f%n", max);
        System.out.printf("Average Production: %,.2f%n", avg);
        System.out.printf("Standard Deviation: %,.2f%n", stdDev);
        
        if (maxRecord != null) {
            System.out.println("\n" + "-".repeat(80));
            System.out.println("HIGHEST PRODUCTION RECORD");
            System.out.println("-".repeat(80));
            System.out.printf("Company: %s%n", maxRecord.getOilCompany());
            System.out.printf("Period: %s %d%n", maxRecord.getMonth(), maxRecord.getYear().getValue());
            System.out.printf("Production: %,.2f (000 metric tonnes)%n", maxRecord.getQmt());
        }
        
        if (minRecord != null) {
            System.out.println("\n" + "-".repeat(80));
            System.out.println("LOWEST PRODUCTION RECORD");
            System.out.println("-".repeat(80));
            System.out.printf("Company: %s%n", minRecord.getOilCompany());
            System.out.printf("Period: %s %d%n", minRecord.getMonth(), minRecord.getYear().getValue());
            System.out.printf("Production: %,.2f (000 metric tonnes)%n", minRecord.getQmt());
        }
        
        System.out.println("\n" + "=".repeat(80) + "\n");
    }

    public SummaryDto computeSummary() {
        List<DataRecord> records = getAllRecords(); // Fetch from DB or CSV fallback
        SummaryDto dto = new SummaryDto();

        if (records.isEmpty()) {
            return dto; // Return empty DTO if no records
        }

        // Basic statistics
        BigDecimal min = records.stream()
                .map(DataRecord::getQmt)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = records.stream()
                .map(DataRecord::getQmt)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        double avg = records.stream()
                .mapToDouble(r -> r.getQmt().doubleValue())
                .average()
                .orElse(0.0);

        double variance = records.stream()
                .mapToDouble(r -> Math.pow(r.getQmt().doubleValue() - avg, 2))
                .average()
                .orElse(0.0);

        double stdDev = Math.sqrt(variance);

        long totalRecords = records.size();
        BigDecimal totalProduction = records.stream()
                .map(DataRecord::getQmt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Find records with max and min production
        DataRecord maxRecord = records.stream()
                .max((r1, r2) -> r1.getQmt().compareTo(r2.getQmt()))
                .orElse(null);

        DataRecord minRecord = records.stream()
                .min((r1, r2) -> r1.getQmt().compareTo(r2.getQmt()))
                .orElse(null);

        // Wrap double values with BigDecimal.valueOf() for the DTO
        dto.setTotalRecords(totalRecords);
        dto.setTotalProduction(totalProduction); // already BigDecimal
        dto.setMinProduction(min);
        dto.setMaxProduction(max);
        dto.setAvgProduction(avg);
        dto.setStdDevProduction(stdDev);

        if (maxRecord != null) {
            dto.setHighestCompany(maxRecord.getOilCompany());
            dto.setHighestPeriod(maxRecord.getMonth() + " " + maxRecord.getYear().getValue());
            dto.setHighestValue(maxRecord.getQmt()); // already BigDecimal
        }

        if (minRecord != null) {
            dto.setLowestCompany(minRecord.getOilCompany());
            dto.setLowestPeriod(minRecord.getMonth() + " " + minRecord.getYear().getValue());
            dto.setLowestValue(minRecord.getQmt()); // already BigDecimal
        }

        return dto;
    }}
