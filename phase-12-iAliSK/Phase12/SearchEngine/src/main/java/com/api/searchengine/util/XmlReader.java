package com.api.searchengine.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class XmlReader {
    private static final XmlMapper xmlMapper;

    static {
        xmlMapper = new XmlMapper();
//        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        xmlMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);

        xmlMapper.findAndRegisterModules();
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }


    public static <T> List<T> read(String fileName, Class<T> toValueType) {
        try {
            return xmlMapper.readValue(new File(fileName),
                    xmlMapper.getTypeFactory().constructCollectionType(List.class, toValueType)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
