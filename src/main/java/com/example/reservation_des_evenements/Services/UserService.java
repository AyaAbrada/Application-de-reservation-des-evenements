package com.example.reservation_des_evenements.Services;
import com.example.reservation_des_evenements.Repositories.UserRepositorie;
import com.example.reservation_des_evenements.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepositorie userRepositorie;
    public UserService(UserRepositorie userRepositorie) {
        this.userRepositorie = userRepositorie;
    }

    public int allUser() {
        return userRepositorie.getALLUser();
    }

}
