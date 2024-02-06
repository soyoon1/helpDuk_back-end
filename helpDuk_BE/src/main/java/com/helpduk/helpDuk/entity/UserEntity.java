package com.helpduk.helpDuk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, length=30)
    private String userEmail;

    @Column(nullable = false, length=50)
    private String password;


    @Column(nullable = false, length=10)
    private String nickName;


    @Column(length = 200)
    private String profileImage;


    @Column(nullable = false)
    private Float temperature;

    // security 에서 사용하는 회원 구분 id
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer getUserId() {
        return this.userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getProfileImage(){
        return profileImage;
    }
    public Float getTemperature(){
        return temperature;
    }
}
