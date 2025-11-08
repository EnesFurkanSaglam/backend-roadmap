package com.enesfurkan.notification.client;


import com.enesfurkan.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationClient implements CommandLineRunner {

    @GrpcClient("userService")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public void run(String... args) throws Exception {

        CreateUserRequest request = CreateUserRequest.newBuilder()
                .setName("test")
                .setEmail("test@gmail.com")
                .build();

        CreateUserResponse response = userServiceBlockingStub.createUser(request);

        System.out.println("Yanıt : " + response.getMessage());

    }

}
