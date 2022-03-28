package org.pcsoft.framework.jrcp.support.spring.api;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.framework.jrcp.support.spring.api.types.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemoApiImpl implements DemoApi{
    private final List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        if (users.contains(user))
            return;

        users.add(user);
    }

    @Override
    public User getUser(String nick) {
        return users.stream()
                .filter(x -> StringUtils.equals(x.getNick(), nick))
                .findFirst().orElse(null);
    }

    @Override
    public User[] getUsers(String name) {
        return users.stream()
                .filter(x -> StringUtils.equals(x.getName(), name))
                .toArray(User[]::new);
    }

    @Override
    public void deleteUser(String nick) {
        users.removeIf(x -> StringUtils.equals(x.getNick(), nick));
    }
}
