package com.oasis.TaskManagementApplication.config.security;

import com.oasis.TaskManagementApplication.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Data
@EqualsAndHashCode
public class UserPrincipal implements UserDetails {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private String password;
    private final String email;
    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(
            long id,
            String firstName,
            String lastName,
            String username,
            String password,
            String email,
            Boolean accountNonExpired,
            Boolean accountNonLocked,
            Boolean credentialsNonExpired,
            Boolean enabled,
            Collection<? extends GrantedAuthority> authorities) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        if (authorities == null) {
            this.authorities = null;
        } else {
            this.authorities = new ArrayList<>(authorities);
        }
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities =
                user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired(),
                user.getEnabled(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


}
