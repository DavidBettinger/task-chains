package bettinger.david.taskchains.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity {

    private String name;

    private String firstName;

    private String userName; //Email

    private String password;

    private String role;
}
