package org.example.secureqabackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.secureqabackend.dto.UserDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
public class User extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastLogin;

    private Integer reputation = 0;

    @Lob
    private String expertiseAreas;

    public UserDto userDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        return userDto;
    }
}
