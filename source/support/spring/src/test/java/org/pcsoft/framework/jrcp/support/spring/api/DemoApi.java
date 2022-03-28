package org.pcsoft.framework.jrcp.support.spring.api;

import org.pcsoft.framework.jrcp.support.spring.api.types.User;
import org.springframework.web.bind.annotation.*;

@RestController("user")
public interface DemoApi {
    @PutMapping(produces = "application/json")
    void addUser(@RequestBody User user);

    @GetMapping(path = "{nick}", consumes = "application/json")
    @ResponseBody
    User getUser(@PathVariable("nick") String nick);

    @GetMapping(consumes = "application/json")
    @ResponseBody
    User[] getUsers(@RequestParam("name") String name);

    default User[] getUsers() {
        return getUsers(null);
    }

    @DeleteMapping
    void deleteUser(@RequestParam("nick") String nick);
}
