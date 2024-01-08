package com.ace.xpresspayment.security;

import com.ace.xpresspayment.models.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.function.Function;

public interface TokenProvider {
    String getUsernameFromJWTToken(String token);

    Date getExpirationDateFromJWTToken(String token);

    <T> T getClaimFromJWTToken(String token, Function<Claims, T> claimsResolver);

    Header<?> getHeaderFromJWTToken(String token);

    Claims getAllClaimsFromJWTToken(String token);

    Boolean isJWTTokenExpired(String token);

    String generateJWTToken(Authentication authentication);

    Boolean validateJWTToken(String token, CustomUser userDetails);

    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final CustomUser userDetails);
}