package com.efs.efs;

import org.springframework.stereotype.Service;

@Service
public class ShardRoutingService {

    public void setShard(Long userId){
        if (userId % 2 == 0){
            ContextHolder.setShard("shard1");
        }else{
            ContextHolder.setShard("shard2");
        }
    }
}
