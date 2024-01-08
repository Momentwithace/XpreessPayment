package com.ace.xpresspayment.notification;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity(name = "otp")
@Table(indexes = @Index(columnList = "email"))
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    private String email;
    private String code;
}
