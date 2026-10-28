package com.spandan.staysync.reservation;
import com.spandan.staysync.room.Room;
import com.spandan.staysync.room.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
@RestController @RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationRepository reservations; private final RoomRepository rooms;
    public ReservationController(ReservationRepository reservations,RoomRepository rooms){this.reservations=reservations;this.rooms=rooms;}
    @GetMapping public List<Reservation> all(){return reservations.findAll();}
    @GetMapping("/{id}") public Reservation one(@PathVariable Long id){return reservations.findById(id).orElseThrow(()->new NoSuchElementException("Reservation not found: "+id));}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Reservation create(@Valid @RequestBody Reservation input){return saveValidated(input,null);}
    @PutMapping("/{id}") public Reservation update(@PathVariable Long id,@Valid @RequestBody Reservation input){Reservation existing=one(id); input.setId(existing.getId()); return saveValidated(input,id);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void cancel(@PathVariable Long id){reservations.delete(one(id));}
    private Reservation saveValidated(Reservation input,Long currentId){
        if(input.getCheckIn()==null||input.getCheckOut()==null||!input.getCheckOut().isAfter(input.getCheckIn())) throw new IllegalArgumentException("Check-out must be after check-in");
        if(input.getRoom()==null||input.getRoom().getId()==null) throw new IllegalArgumentException("room.id is required");
        Room room=rooms.findById(input.getRoom().getId()).orElseThrow(()->new NoSuchElementException("Room not found: "+input.getRoom().getId()));
        boolean conflict = currentId == null
            ? reservations.existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThan(room.getId(), input.getCheckOut(), input.getCheckIn())
            : reservations.existsByRoom_IdAndCheckInLessThanAndCheckOutGreaterThanAndIdNot(room.getId(), input.getCheckOut(), input.getCheckIn(), currentId);
        if (conflict) throw new IllegalArgumentException("Room is already booked for the selected dates");
        input.setRoom(room); return reservations.save(input);
    }
}
