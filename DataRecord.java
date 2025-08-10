package com.shank.springanalytics.model;

import jakarta.persistence.*;
import java.time.Year;
import java.math.BigDecimal;

@Entity
@Table(name = "oil_production")
public class DataRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`month`")
    private String month;

    @Column(name = "year")
    private Year year;

    @Column(name = "oil_company", nullable = false, length = 255)
    private String oilCompany;

    @Column(name = "quantity_000_metric_tonnes", nullable = false, precision = 10, scale = 2)
    private BigDecimal qmt;

    public DataRecord() {}

    public DataRecord(String month, Year year, String oilCompany, BigDecimal qmt) {
        this.month = month;
        this.year = year;
        this.oilCompany = oilCompany;
        this.qmt = qmt;
    }

    // Getters and setters omitted for brevity (include as needed)

    // ... your getters and setters here ...

    @Override
    public String toString() {
        return "DataRecord{" +
                "id=" + id +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", oilCompany='" + oilCompany + '\'' +
                ", quantity000MetricTonnes=" + qmt +
                '}';
    }

	public BigDecimal getQmt() {
		// TODO Auto-generated method stub
		return qmt;
	}

	public String getMonth() {
		// TODO Auto-generated method stub
		return month;
	}

	public Year getYear() {
		return year;
	}
	
	public String getOilCompany() {
		return oilCompany;
	}
}