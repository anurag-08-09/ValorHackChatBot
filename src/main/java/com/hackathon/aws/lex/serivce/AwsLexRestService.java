package com.hackathon.aws.lex.serivce;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Hex;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatbot.objectmapper.ObjectMapperSingleton;
import com.example.websocketdemo.model.AwsLexServiceResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AwsLexRestService {

    private static final Logger logger = LoggerFactory.getLogger(AwsLexRestService.class);

    public AwsLexServiceResponse getAwsLexBotResponse(String inputText)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
        ObjectMapper mapper = ObjectMapperSingleton.getInstance();
        AwsLexServiceResponse serviceResponse = null;
        final String method = "POST";
        final String service = "lex";
        final String host = "runtime.lex.us-east-1.amazonaws.com";
        final String region = "us-east-1";
        final String endpoint = "https://runtime.lex.us-east-1.amazonaws.com/";
        final String accessKey = "access key";
        final String secretKey = "secret key";
        final String botName = "identifyDomainBot";
        final String botAlias = "valor_chatbot";
        final String userId = "user id";
        final String postAction = "text";
        final String contentType = "application/json";
        final String canonicalUri = String.format("/bot/%s/alias/%s/user/%s/%s/", botName, botAlias, userId,
                postAction);
        final String canonicalQueryString = "";
        final String signedHeaders = "content-type;host;x-amz-date";
        final String algorithm = "AWS4-HMAC-SHA256";
        final List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        final WebClient client = WebClient.create(endpoint, providers);
        client.accept(contentType).type(contentType).path(canonicalUri);
        final String requestParameters = String
                .format("{\"inputText\": \"%s\", \"sessionAttributes\": {\"attr_name\" : \"value\"}}", inputText);
        final String payloadHash = hexEncode(sha256Hash(requestParameters));
        final ZonedDateTime utcNow = Instant.now().atZone(ZoneOffset.UTC);
        final String amzDate = utcNow.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        final String dateStamp = utcNow.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        final String canonicalHeaders = String.format("content-type:%s\nhost:%s\nx-amz-date:%s\n", contentType, host,
                amzDate);
        final String canonicalRequest = String.format("%s\n%s\n%s\n%s\n%s\n%s", method, canonicalUri,
                canonicalQueryString, canonicalHeaders, signedHeaders, payloadHash);
        final String credentialScope = String.format("%s/%s/%s/aws4_request", dateStamp, region, service);
        final String canonicalRequestHash = hexEncode(sha256Hash(canonicalRequest));
        final String stringToSign = String.format("%s\n%s\n%s\n%s", algorithm, amzDate, credentialScope,
                canonicalRequestHash);
        final byte[] signatureKey = getSignatureKey(secretKey, dateStamp, region, service);
        final String signature = hexEncode(hmacSHA256(stringToSign, signatureKey));
        final String authorizationHeader = String.format("%s Credential=%s/%s, SignedHeaders=%s, Signature=%s",
                algorithm, accessKey, credentialScope, signedHeaders, signature);
        final MetadataMap<String, String> headersMap = new MetadataMap<>();
        headersMap.add("Content-Type", contentType);
        headersMap.add("X-Amz-Date", amzDate);
        headersMap.add("Authorization", authorizationHeader);
        client.headers(headersMap);
        Response response = client.post(requestParameters);
        InputStream responseStream = (InputStream) response.getEntity();
        if (response.getStatus() == 200) {
            JsonNode jsonNode = new ObjectMapper().readTree(responseStream);
            serviceResponse = mapper.treeToValue(jsonNode, AwsLexServiceResponse.class);
            System.out.println(jsonNode);
        } else {
            logger.error("Some error ocurred while processing response from aws lex runtime");
        }
        return serviceResponse;
    }

    private static byte[] sha256Hash(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MessageDigest.getInstance("SHA-256").digest(value.getBytes("UTF8"));
    }

    private static String hexEncode(byte[] data) throws Exception {
        Hex hex = new Hex();
        byte[] arr = hex.encode(data);
        return new String(arr);
    }

    static byte[] hmacSHA256(String data, byte[] key) throws Exception {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName)
            throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
        byte[] kDate = hmacSHA256(dateStamp, kSecret);
        byte[] kRegion = hmacSHA256(regionName, kDate);
        byte[] kService = hmacSHA256(serviceName, kRegion);
        byte[] kSigning = hmacSHA256("aws4_request", kService);
        return kSigning;
    }

}
