package com.shank.springanalytics.dto;

import java.math.BigDecimal;

public class SummaryDto {
    private long totalRecords;
    private BigDecimal totalProduction;
    private BigDecimal minProduction;
    private BigDecimal maxProduction;
    private double avgProduction;
    private double stdDevProduction;

    private String highestCompany;
    private String highestPeriod;
    private BigDecimal highestValue;

    private String lowestCompany;
    private String lowestPeriod;
    private BigDecimal lowestValue;

    // Getters and setters
    public long getTotalRecords() { return totalRecords; }
    public void setTotalRecords(long totalRecords) { this.totalRecords = totalRecords; }

    public BigDecimal getTotalProduction() { return totalProduction; }
    public void setTotalProduction(BigDecimal totalProduction) { this.totalProduction = totalProduction; }

    public BigDecimal getMinProduction() { return minProduction; }
    public void setMinProduction(BigDecimal minProduction) { this.minProduction = minProduction; }

    public BigDecimal getMaxProduction() { return maxProduction; }
    public void setMaxProduction(BigDecimal maxProduction) { this.maxProduction = maxProduction; }

    public double getAvgProduction() { return avgProduction; }
    public void setAvgProduction(double avgProduction) { this.avgProduction = avgProduction; }

    public double getStdDevProduction() { return stdDevProduction; }
    public void setStdDevProduction(double stdDevProduction) { this.stdDevProduction = stdDevProduction; }

    public String getHighestCompany() { return highestCompany; }
    public void setHighestCompany(String highestCompany) { this.highestCompany = highestCompany; }

    public String getHighestPeriod() { return highestPeriod; }
    public void setHighestPeriod(String highestPeriod) { this.highestPeriod = highestPeriod; }

    public BigDecimal getHighestValue() { return highestValue; }
    public void setHighestValue(BigDecimal highestValue) { this.highestValue = highestValue; }

    public String getLowestCompany() { return lowestCompany; }
    public void setLowestCompany(String lowestCompany) { this.lowestCompany = lowestCompany; }

    public String getLowestPeriod() { return lowestPeriod; }
    public void setLowestPeriod(String lowestPeriod) { this.lowestPeriod = lowestPeriod; }

    public BigDecimal getLowestValue() { return lowestValue; }
    public void setLowestValue(BigDecimal lowestValue) { this.lowestValue = lowestValue; }
}