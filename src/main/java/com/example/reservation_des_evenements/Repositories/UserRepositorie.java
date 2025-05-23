package com.example.reservation_des_evenements.Repositories;
import com.example.reservation_des_evenements.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorie extends JpaRepository<User , Long> {
    User findByUsername(String username);
}
