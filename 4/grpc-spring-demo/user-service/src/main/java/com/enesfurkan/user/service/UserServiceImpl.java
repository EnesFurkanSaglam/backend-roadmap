package com.enesfurkan.user.service;

import com.enesfurkan.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private static final Map<Long, User> userStore = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        long id = idCounter.getAndIncrement();
        User user = new User(id, request.getName(), request.getEmail());
        userStore.put(id, user);

        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setMessage("Kullanıcı oluşturuldu ID: " + id)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        User user = userStore.get(request.getId());
        if (user != null) {
            GetUserResponse response = GetUserResponse.newBuilder()
                    .setName(user.name)
                    .setEmail(user.email)
                    .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onError(new Exception("Kullanıcı bulunamadı."));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserRequest request, StreamObserver<UpdateUserResponse> responseObserver) {
        User user = userStore.get(request.getId());
        if (user != null) {
            user.name = request.getName();
            user.email = request.getEmail();
            UpdateUserResponse response = UpdateUserResponse.newBuilder()
                    .setMessage("Kullanıcı güncellendi.")
                    .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onError(new Exception("Kullanıcı bulunamadı."));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(DeleteUserRequest request, StreamObserver<DeleteUserResponse> responseObserver) {
        User removed = userStore.remove(request.getId());
        if (removed != null) {
            DeleteUserResponse response = DeleteUserResponse.newBuilder()
                    .setMessage("Kullanıcı silindi.")
                    .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onError(new Exception("Kullanıcı bulunamadı."));
        }
        responseObserver.onCompleted();
    }

    private static class User {
        long id;
        String name;
        String email;

        User(long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }


}
