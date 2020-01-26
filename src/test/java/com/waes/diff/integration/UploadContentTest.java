package com.waes.diff.integration;

import com.waes.diff.configuration.Main;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import java.io.IOException;
import java.util.Random;

public class UploadContentTest {

    private Random random;

    @BeforeClass
    public static void setup() {
        Main.main(null);
        Spark.awaitInitialization();
    }

    @Test
    public void testUploadContentLeftSide() throws IOException {
        random = new Random();

        //Get a new id
        int statusCode = 0;
        int id = 0;
        while (statusCode != HttpStatus.SC_NOT_FOUND) {
            id = random.nextInt();
            if (id > 0) {
                CloseableHttpResponse resp = findById(id);
                statusCode = resp.getStatusLine().getStatusCode();
            }
        }

        //Then use that id to upload some content
        String plain = "some content";
        String side = "left";
        CloseableHttpResponse resp = putContent(plain, side, id);
        Assert.assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());

        //Finally find it again and verify the response
        resp = findById(id);
        Assert.assertEquals(HttpStatus.SC_FORBIDDEN, resp.getStatusLine().getStatusCode());
    }

    @Test
    public void testUploadContentRightSide() throws IOException {
        random = new Random();

        //Get a new id
        int statusCode = 0;
        int id = 0;
        while (statusCode != HttpStatus.SC_NOT_FOUND) {
            id = random.nextInt();
            if (id > 0) {
                CloseableHttpResponse resp = findById(id);
                statusCode = resp.getStatusLine().getStatusCode();
            }
        }

        //Then use that id to upload some content
        String plain = "some content";
        String side = "right";
        CloseableHttpResponse resp = putContent(plain, side, id);
        Assert.assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());

        //Finally find it again and verify the response
        resp = findById(id);
        Assert.assertEquals(HttpStatus.SC_FORBIDDEN, resp.getStatusLine().getStatusCode());
    }

    @Test
    public void testUploadContent() throws IOException, InterruptedException {
        random = new Random();
        //Get a new id
        int statusCode = 0;
        int id = 0;
        while (statusCode != HttpStatus.SC_NOT_FOUND) {
            id = random.nextInt();
            if (id > 0) {
                CloseableHttpResponse resp = findById(id);
                statusCode = resp.getStatusLine().getStatusCode();
            }
        }

        //Then use that id to upload some content
        String plain = "Hello world!!!";
        String side = "left";
        CloseableHttpResponse resp = putContent(plain, side, id);
        Assert.assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());

        plain = "Wello horld!??";
        side = "right";
        resp = putContent(plain, side, id);
        Assert.assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());

        //Finally find it again and verify the response
        resp = findById(id);
        Assert.assertEquals(HttpStatus.SC_OK, resp.getStatusLine().getStatusCode());
        HttpEntity entity = resp.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        Assert.assertEquals("{\"result\":\"3 differences found: pos: 1 length: 1, pos: 7 length: 1, pos: 14 length: 2\"}", responseString);
    }

    private static CloseableHttpResponse putContent(String plain, String side, int id) throws IOException {
        HttpUriRequest req = RequestBuilder.create("PUT").setUri("http://localhost:8080/v1/diff/" + id + "/" + side)
                .setEntity(new StringEntity(buildPayload(encodeText(plain)), ContentType.APPLICATION_JSON)).build();
        return HttpClientBuilder.create().build().execute(req);
    }

    private static CloseableHttpResponse findById(int id) throws IOException {
        HttpUriRequest req = RequestBuilder.create("GET").setUri("http://localhost:8080/v1/diff/" + id).build();
        return HttpClientBuilder.create().build().execute(req);
    }

    private static String buildPayload(String data) {
        return "{\n" +
                "\t\"data\": \"" + data + "\"\n" +
                "}";
    }

    private static String encodeText(String decodedText) {
        return new String(Base64.encodeBase64(decodedText.getBytes()));
    }
}
