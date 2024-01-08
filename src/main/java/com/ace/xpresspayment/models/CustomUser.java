package com.ace.xpresspayment.models;

import com.ace.xpresspayment.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
public class CustomUser implements UserDetails, Serializable {
    private long id;
    private String email;
    private String phoneNo;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    public CustomUser(long id, String email, String password, Role role, boolean isEnabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled =true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }


    public static long getId(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return customUser.getId();
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
