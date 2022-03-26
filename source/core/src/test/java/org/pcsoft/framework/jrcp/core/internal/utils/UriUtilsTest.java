package org.pcsoft.framework.jrcp.core.internal.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class UriUtilsTest {

    @Test
    public void testEmpty() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        final HashMap<String, Object> queryParams = new HashMap<>();
        final var uri = UriUtils.createUri("http://localhost:8080/rest", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest", uri);
    }

    @Test
    public void testQuerySingle() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        final HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("v", "text");
        final var uri = UriUtils.createUri("http://localhost:8080/rest", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest?v=text", uri);
    }

    @Test
    public void testQueryMultiple() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        final HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("v1", "text");
        queryParams.put("v2", 10);
        final var uri = UriUtils.createUri("http://localhost:8080/rest", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest?v1=text&v2=10", uri);
    }

    @Test
    public void testPathSingle() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("v", "text");
        final HashMap<String, Object> queryParams = new HashMap<>();
        final var uri = UriUtils.createUri("http://localhost:8080/rest/{v}", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest/text", uri);
    }

    @Test
    public void testPathMultiple() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("v1", "text");
        pathParams.put("v2", 10);
        final HashMap<String, Object> queryParams = new HashMap<>();
        final var uri = UriUtils.createUri("http://localhost:8080/rest/{v1}/{v2}", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest/text/10", uri);
    }

    @Test
    public void testAllSingle() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("path", "text");
        final HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("query", 10);
        final var uri = UriUtils.createUri("http://localhost:8080/rest/{path}/any", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest/text/any?query=10", uri);
    }

    @Test
    public void testAllMultiple() {
        final HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("path1", "text");
        pathParams.put("path2", 99);
        final HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("query1", 10);
        queryParams.put("query2", "here");
        final var uri = UriUtils.createUri("http://localhost:8080/rest/{path1}/any/{path2}", pathParams, queryParams);

        Assert.assertNotNull(uri);
        Assert.assertEquals("http://localhost:8080/rest/text/any/99?query1=10&query2=here", uri);
    }
}