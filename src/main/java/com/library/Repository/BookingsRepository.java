package com.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.Entities.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {

}
