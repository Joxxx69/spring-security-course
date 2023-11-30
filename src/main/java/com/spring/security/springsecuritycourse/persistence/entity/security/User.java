package com.spring.security.springsecuritycourse.persistence.entity.security;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.security.springsecuritycourse.persistence.util.RoleEnumm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_user")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;

    //@Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(
        name = "role_id",
        referencedColumnName = "roleId"
    )
    private Role role;
    //private RoleEnum roleEnum;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {return null;}
        if (role.getPermissions() == null) {return null;}
        //return roleEnum.getPermissions().stream().map(each->{
        //    String name = each.name();
        //    return new SimpleGrantedAuthority(name);
        //}).collect(Collectors.toList());

        List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                .map(each -> each.getOperation().getName())
                .map(name -> new SimpleGrantedAuthority(name))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.role.getName()));
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }    
    

    @Override
    public boolean isAccountNonExpired() {
         return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
