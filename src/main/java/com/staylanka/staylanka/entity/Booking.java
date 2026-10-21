package com.staylanka.staylanka.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;


    @ManyToOne
    @JoinColumn(name = "customerId",nullable = false)
    private CustomerUser customerUser;

   /* @ManyToOne
    @JoinColumn(name = "roomId",nullable = false)
    private R*/
}
