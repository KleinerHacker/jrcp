package org.pcsoft.framework.jrcp.support.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pcsoft.framework.jrcp.core.JRCPClient;
import org.pcsoft.framework.jrcp.support.spring.api.DemoApi;
import org.pcsoft.framework.jrcp.support.spring.api.DemoApiImpl;
import org.pcsoft.framework.jrcp.support.spring.api.types.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DemoApiImpl.class)
@TestPropertySource(properties = "server.port=9999")
public class SpringAnnotationProviderTest {

    @Test
    public void test() {
        final var client = JRCPClient.createBuilder("http://localhost:999/rest")
                .withAnnotationProvider(SpringAnnotationProvider.INSTANCE)
                .withApiInterface(DemoApi.class)
                .withStandardContentProviders()
                .build();

        final var proxy = client.getProxy(DemoApi.class);
        Assert.assertNotNull(proxy.getUsers());
        Assert.assertEquals(0, proxy.getUsers().length);

        proxy.addUser(new User("Chris", "pc"));
        proxy.addUser(new User("Maria", "mar"));
        proxy.addUser(new User("Chris", "chr"));

        Assert.assertNotNull(proxy.getUsers());
        Assert.assertEquals(3, proxy.getUsers().length);
        Assert.assertEquals(2, proxy.getUsers("Chris").length);
        Assert.assertEquals(0, proxy.getUsers("123").length);
        Assert.assertNotNull(proxy.getUser("pc"));
        Assert.assertNull(proxy.getUser("123"));

        proxy.deleteUser("pc");

        Assert.assertNotNull(proxy.getUsers());
        Assert.assertEquals(2, proxy.getUsers().length);
        Assert.assertEquals(1, proxy.getUsers("Chris").length);
        Assert.assertEquals(0, proxy.getUsers("123").length);
        Assert.assertNull(proxy.getUser("pc"));
        Assert.assertNotNull(proxy.getUser("chr"));
        Assert.assertNull(proxy.getUser("123"));
    }

}