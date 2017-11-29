package com.example.websocketdemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.chatbot.objectmapper.ObjectMapperSingleton;


public class SlackMessagingService {

    public void postMessageToSlack(String userName) throws UnsupportedOperationException, IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://valor-hack-group.slack.com/api/chat.postMessage");

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("channel", "@" + userName));
        params.add(new BasicNameValuePair("text", "Hello valor buoy this side!"));
        params.add(new BasicNameValuePair("username", "ValorBOT"));
        params.add(new BasicNameValuePair("token",
                "your token"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            java.util.Map<String, Object> jsonMap = ObjectMapperSingleton.getInstance().readValue(instream,
                    java.util.Map.class);
            try {
            } finally {
                instream.close();
            }
        }
    }

}
