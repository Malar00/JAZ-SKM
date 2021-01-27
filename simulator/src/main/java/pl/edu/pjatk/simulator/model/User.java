package pl.edu.pjatk.simulator.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails, DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String authority;

    public User() {}

    public User(String username, String password, String authorities) {
        this.username = username;
        this.password = password;
        this.authority = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(authority);
        return Arrays.stream(this.authority.split(",")).map(String::trim).filter(authority -> !authority.equals("")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public Long getId() {
        return id;
    }
}
