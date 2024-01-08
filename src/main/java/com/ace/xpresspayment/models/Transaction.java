package com.ace.xpresspayment.models;

import com.ace.xpresspayment.enums.Network;
import com.ace.xpresspayment.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@Entity(name = "transactions")
@Table(indexes = @Index(columnList = "userId"))
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private long userId;

    @Enumerated(EnumType.STRING)
    private Network network;

    @Column(unique = true)
    private String processorReference;

    @Column(updatable = false, unique = true, nullable = false)
    private String transactionReference;


    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
}
