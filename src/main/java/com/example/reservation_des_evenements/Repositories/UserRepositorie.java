package com.example.reservation_des_evenements.Repositories;
import com.example.reservation_des_evenements.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorie extends JpaRepository<User , Long> {
    User findByUsername(String username);

    @Query(value = "select count(*) from User where role = 'user'", nativeQuery = true)
    int getALLUser();
}
