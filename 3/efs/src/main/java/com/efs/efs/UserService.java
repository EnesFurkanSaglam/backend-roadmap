package com.efs.efs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private static final AtomicLong counter = new AtomicLong(1);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShardRoutingService shardRoutingService;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUser(Long userId){
        shardRoutingService.setShard(userId);
        return userRepository.findById(userId);
    }

}
