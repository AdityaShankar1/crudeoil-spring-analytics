package com.shank.springanalytics.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year year) {
        return (year != null) ? year.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? Year.of(dbData) : null;
    }
}