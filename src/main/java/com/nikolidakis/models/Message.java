package com.nikolidakis.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "message_time")
    @NotNull
    private String messageTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId", referencedColumnName = "user_id")
    private User sender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reciver_id", referencedColumnName = "user_id")
    private User receiver;

    @Column(name = "subject")
    @NotNull
    private String subject; //The subject of the message is the auction id

    @Column(name = "message")
    @NotNull
    private String message;

    @Column(name = "is_read")
    private boolean isRead;


}
