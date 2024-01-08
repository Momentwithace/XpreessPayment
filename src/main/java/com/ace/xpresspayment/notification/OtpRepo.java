package com.ace.xpresspayment.notification;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OtpRepo extends CrudRepository<OTP, String> {


    Optional<OTP> findByCodeAndEmail(String code, String email);
}
