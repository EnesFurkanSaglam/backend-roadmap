package com.efs.efs;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user){
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id){
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Version
    @GetMapping("/version")
    public ResponseEntity<String> getUsers(@RequestHeader("API-VERSION") String version){
        if ("1".equals(version)){
            return ResponseEntity.ok("Version 1 - Basic User List");
        } else if ("2".equals(version)) {
            return ResponseEntity.ok("Version 2 - User List with extra details");
        } else {
           return ResponseEntity.badRequest().body("Invalid API Version");
        }
    }

    //Paginate
    @GetMapping("/paginated")
    public Page<UserEntity> getAllUsersWithPagination(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    //Sorting
    @GetMapping("/sorted")
    public ResponseEntity<List<UserEntity>> findByNameContaining(@RequestParam String name){
        return ResponseEntity.ok(userRepository.findByNameContaining(name));
    }

}
