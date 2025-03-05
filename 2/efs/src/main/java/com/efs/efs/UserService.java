package com.efs.efs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserService {

    private static final String USER_KEY = "USER:";

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id){

        String key = USER_KEY + id;

        User user = (User) redisTemplate.opsForValue().get(key);

        if (user == null){
            user = userRepository.findById(id).orElse(null);
            if (user!=null){
                redisTemplate.opsForValue().set(key,user, Duration.ofMinutes(10));
            }
        }
        return user;
    }

    public User createUser(User user) {
        if (user.getOrders() != null) {
            for (Order order : user.getOrders()) {
                order.setUser(user);
            }
        }
        return userRepository.save(user);
    }


    public User findUserWithOrders(Long id){
        return userRepository.findUserWithOrders(id);
    }
}
