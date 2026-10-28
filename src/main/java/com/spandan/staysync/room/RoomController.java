package com.spandan.staysync.room;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
@RestController @RequestMapping("/api/rooms")
public class RoomController {
    private final RoomRepository rooms;
    public RoomController(RoomRepository rooms){this.rooms=rooms;}
    @GetMapping public List<Room> all(){return rooms.findAll();}
    @GetMapping("/{id}") public Room one(@PathVariable Long id){return rooms.findById(id).orElseThrow(()->new NoSuchElementException("Room not found: "+id));}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Room create(@Valid @RequestBody Room room){if(rooms.existsByRoomNumber(room.getRoomNumber())) throw new IllegalArgumentException("Room number already exists"); room.setId(null); return rooms.save(room);}
    @PutMapping("/{id}") public Room update(@PathVariable Long id,@Valid @RequestBody Room input){Room room=one(id); room.setRoomNumber(input.getRoomNumber());room.setRoomType(input.getRoomType());room.setCapacity(input.getCapacity());room.setPricePerNight(input.getPricePerNight());room.setAvailable(input.isAvailable());return rooms.save(room);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){rooms.delete(one(id));}
}
