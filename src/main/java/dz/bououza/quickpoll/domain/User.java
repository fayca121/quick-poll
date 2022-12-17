package dz.bououza.quickpoll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Type;

import java.util.Objects;

@Entity
@Table("USERS")
public class User {
    @Id
    @GeneratedValue
    @Column("USER_ID")
    private Long id;

    @Column(name="USERNAME")
    @NotEmpty
    private String username;

    @Column(name="PASSWORD")
    @NotEmpty
    @JsonIgnore
    private String password;

    @Column(name="FIRST_NAME")
    @NotEmpty
    private String firstName;

    @Column(name="LAST_NAME")
    @NotEmpty
    private String lastName;

    @Column(name="ADMIN", columnDefinition="char(3)")
    @Type(type="yes_no")
    @NotEmpty
    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return admin == user.admin && id.equals(user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, admin);
    }
}
