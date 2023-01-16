package com.yapp.ios2.fitfty.domain.user;

import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;

public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return Arrays.asList(string.split(SPLIT_CHAR));
    }
}