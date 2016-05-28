package swt6.soccer.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * User entity
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String name;

    public User(String name,String email){
        this.name=name;
        this.email=email;
    }

    public User() {
    }
}
