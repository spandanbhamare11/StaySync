package com.spandan.staysync.reservation;
import com.spandan.staysync.room.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
@Entity
@Table(name="reservations")
public class Reservation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String guestName;
    @Email @NotBlank private String guestEmail;
    @ManyToOne(optional=false) @JoinColumn(name="room_id") private Room room;
    @NotNull @FutureOrPresent private LocalDate checkIn;
    @NotNull private LocalDate checkOut;
    public Reservation() {}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getGuestName(){return guestName;} public void setGuestName(String v){guestName=v;}
    public String getGuestEmail(){return guestEmail;} public void setGuestEmail(String v){guestEmail=v;}
    public Room getRoom(){return room;} public void setRoom(Room v){room=v;}
    public LocalDate getCheckIn(){return checkIn;} public void setCheckIn(LocalDate v){checkIn=v;}
    public LocalDate getCheckOut(){return checkOut;} public void setCheckOut(LocalDate v){checkOut=v;}
}
