package com.utkarsh.leanassignment.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String photoUrl;
    private boolean enabled;
    private String roles;

    public User(String userName,String email,String password,boolean enabled){
        this.username=userName;
        this.email=email;
        this.password=password;
        this.enabled=enabled;
        this.photoUrl="";
        this.roles="ROLE_USER";
    }
}