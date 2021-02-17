package com.zhuk.domain.room;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    private Long id;

    @NotNull
    private Integer capacity;
    @NotNull
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Status status = Status.FREE;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomtype;

    @NotNull
    private String imgName;

    @OneToMany(mappedBy = "room")
    private Set<Booking> bookings;

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public RoomType getRoomtype() {
        return roomtype;
    }


    public void setRoomtype(RoomType roomtype) {
        this.roomtype = roomtype;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Long getTotalPrice(LocalDate checkin, LocalDate checkout) {
        return price * DAYS.between(checkin, checkout);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", price=" + price +
                ", status=" + status +
                ", roomType=" + roomtype +
                ", imgPath='" + imgName + '\'' +
                '}';
    }
}
