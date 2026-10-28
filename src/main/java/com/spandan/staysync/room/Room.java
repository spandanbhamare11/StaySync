package com.spandan.staysync.room;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
@Entity
@Table(name="rooms")
public class Room {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String roomNumber;
    @NotBlank private String roomType;
    @Positive private int capacity;
    @DecimalMin(value="0.0", inclusive=false) private BigDecimal pricePerNight;
    private boolean available = true;
    public Room() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getRoomNumber(){return roomNumber;} public void setRoomNumber(String v){roomNumber=v;}
    public String getRoomType(){return roomType;} public void setRoomType(String v){roomType=v;}
    public int getCapacity(){return capacity;} public void setCapacity(int v){capacity=v;}
    public BigDecimal getPricePerNight(){return pricePerNight;} public void setPricePerNight(BigDecimal v){pricePerNight=v;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
}
