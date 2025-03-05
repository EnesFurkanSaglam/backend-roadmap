package com.efs.efs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){

        User user = userService.getUserById(id);

        if (user!= null){
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }


    @GetMapping("/find-user-with-orders/{id}")
    public ResponseEntity<User> findUserWithOrders(@PathVariable("id") long id){
        return ResponseEntity.ok(userService.findUserWithOrders(id));
    }
}
