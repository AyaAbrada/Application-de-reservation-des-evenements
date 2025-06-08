package com.example.reservation_des_evenements.Repositories;
import com.example.reservation_des_evenements.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
