
package com.spandan.staysync.reservation;

import com.spandan.staysync.room.Room;
import com.spandan.staysync.room.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationRepository reservations;

    @Mock
    private RoomRepository rooms;

    private ReservationController controller;
    private Room room;

    @BeforeEach
    void setUp() {
        controller = new ReservationController(reservations, rooms);

        room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        room.setRoomType("DELUXE");
        room.setCapacity(2);
    }

    private Reservation validReservation() {
        Reservation reservation = new Reservation();
        reservation.setGuestName("Test Guest");
        reservation.setGuestEmail("test@example.com");
        reservation.setRoom(room);
        reservation.setCheckIn(LocalDate.of(2026, 11, 10));
        reservation.setCheckOut(LocalDate.of(2026, 11, 12));
        return reservation;
    }

    @Test
    void createReservationSuccessfully() {
        Reservation input = validReservation();

        when(rooms.findById(1L)).thenReturn(Optional.of(room));
        when(reservations
                .existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThan(
                        eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);
        when(reservations.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = controller.create(input);

        assertEquals("Test Guest", result.getGuestName());
        assertEquals(1L, result.getRoom().getId());
        verify(reservations).save(input);
    }

    @Test
    void rejectInvalidDateRange() {
        Reservation input = validReservation();
        input.setCheckIn(LocalDate.of(2026, 11, 15));
        input.setCheckOut(LocalDate.of(2026, 11, 12));

        assertThrows(IllegalArgumentException.class,
                () -> controller.create(input));

        verifyNoInteractions(rooms, reservations);
    }

    @Test
    void rejectOverlappingReservation() {
        Reservation input = validReservation();

        when(rooms.findById(1L)).thenReturn(Optional.of(room));
        when(reservations
                .existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThan(
                        eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> controller.create(input));

        verify(reservations, never()).save(any(Reservation.class));
    }

    @Test
    void rejectMissingRoom() {
        Reservation input = validReservation();

        when(rooms.findById(1L)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class,
                () -> controller.create(input));

        verify(reservations, never()).save(any(Reservation.class));
    }

    @Test
    void updateExistingReservation() {
        Reservation existing = validReservation();
        existing.setId(5L);

        Reservation input = validReservation();
        input.setGuestName("Updated Guest");

        when(reservations.findById(5L)).thenReturn(Optional.of(existing));
        when(rooms.findById(1L)).thenReturn(Optional.of(room));
        when(reservations
                .existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThanAndIdNot(
                        eq(1L), any(LocalDate.class), any(LocalDate.class), eq(5L)))
                .thenReturn(false);
        when(reservations.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = controller.update(5L, input);

        assertEquals(5L, result.getId());
        assertEquals("Updated Guest", result.getGuestName());
        verify(reservations).save(input);
    }

    @Test
    void deleteExistingReservation() {
        Reservation existing = validReservation();
        existing.setId(5L);

        when(reservations.findById(5L)).thenReturn(Optional.of(existing));

        controller.cancel(5L);

        verify(reservations).delete(existing);
    }
}
