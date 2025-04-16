package com.arutyun.quiz_server.user.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    @Setter
    private String name;

    @Column(name = "birth_date")
    @Setter
    private LocalDate birthDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<RoleEntity> roles = new HashSet<>();

    public UserEntity(
            String email,
            String password,
            Set<RoleEntity> roles
    ) {
        this.password = password;
        this.email = email;
        this.roles.addAll(roles);
    }

    public UserEntity(
            String email,
            String password,
            String name,
            LocalDate birthDate,
            Set<RoleEntity> roles
    ) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.roles.addAll(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }


    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
