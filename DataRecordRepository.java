package com.shank.springanalytics.repository;

import com.shank.springanalytics.model.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord, Long> {
    // Add custom methods if needed
}