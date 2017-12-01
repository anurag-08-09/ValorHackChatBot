package com.chat.websocketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import com.chat.objectmapper.ObjectMapperSingleton;
import com.chat.websocketdemo.model.OnCallResponse;
import com.fasterxml.jackson.core.type.TypeReference;

public class OnCallService {

    public String getOnCallByTeamName(String teamName) throws IOException {
        InputStream is = null;
        try {
            is = new URL("http://arconcall.ia55.net/export/?format=json").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            TypeReference<List<OnCallResponse>> mapType = new TypeReference<List<OnCallResponse>>() {
            };
            List<OnCallResponse> jsonToPersonList = ObjectMapperSingleton.getInstance().readValue(jsonText, mapType);
            for (OnCallResponse onCallResponse : jsonToPersonList) {
                if (teamName.equalsIgnoreCase(onCallResponse.getGroup())) {
                    return onCallResponse.getUser();
                }
            }
        } catch (Exception e) {

        } finally {
            is.close();
        }

        return "techops@arcesium.com";
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
