package com.spandan.staysync.reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThan(Long roomId, LocalDate checkOut, LocalDate checkIn);
    boolean existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThanAndIdNot(Long roomId, LocalDate checkOut, LocalDate checkIn, Long id);
}
