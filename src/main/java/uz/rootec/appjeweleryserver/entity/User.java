package uz.rootec.appjeweleryserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.rootec.appjeweleryserver.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sherlock on 01.06.2021.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbstractEntity implements UserDetails {
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    private UUID director;

    public User(String phoneNumber, String password, String lastName, String firstName, List<Role> roles, String email) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.roles = roles;
        this.email = email;
    }

    public User(String phoneNumber, String password, String lastName, String firstName, List<Role> roles, String email, UUID director) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.roles = roles;
        this.email = email;
        this.director = director;
    }

    public User(String lastName, String firstName, List<Role> roles, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.roles = roles;
        this.email = email;
    }

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
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

    @PreUpdate
    public void update() {
        setUpdatedBy(null);
    }
}
