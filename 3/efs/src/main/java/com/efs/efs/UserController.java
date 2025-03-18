package com.efs.efs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private AtomicLong counter = new AtomicLong(1);

    @Autowired
    private ShardRoutingService shardRoutingService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        if (user.getId() == null) {
            user.setId(counter.getAndIncrement());
        }

        shardRoutingService.setShard(user.getId());
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
