package swt6.soccer.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Team entity
 */
@Entity
@Data
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Team(String name){
        this.name = name;
    }

    public Team() {
    }
}
