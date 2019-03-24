package org.nju.mycourses.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @ClassName Log
 * @PackageName org.nju.mycourses.data.entity
 * @Author sheen
 * @Date 2019/3/25
 * @Version 1.0
 * @Description //TODO
 *
 **/
@Entity
@Data
@Table(name = "log")
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lid")
    private Integer id;
    private String type;
    private LocalDateTime time;
    private String massage;
    private Role role;
    private String username;
    public Log(String type, String massage, User user) {
        this.type = type;
        this.massage = massage;
        this.role = user.getRole();
        this.username=user.getUsername();
        this.time=LocalDateTime.now();
    }

    public Log(String type, String massage, String username, Role role) {
        this.type = type;
        this.massage = massage;
        this.role = role;
        this.username = username;
        this.time=LocalDateTime.now();
    }
}
