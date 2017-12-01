package com.chat.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {

    private static ObjectMapper mapper;
    private static Object mutex = new Object();

    private ObjectMapperSingleton() {
    }

    public static ObjectMapper getInstance() {

        if (mapper == null) {
            synchronized (mutex) {
                if (mapper == null) {
                    mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                }
            }
        }
        return mapper;
    }

}
