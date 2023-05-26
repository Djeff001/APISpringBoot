package springboot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "ID")
    private Long id;
    @Column (name= "NAME")
    @NonNull
    private String name;
    @Column (name= "BIRTHDATE")
    @NonNull
    private LocalDate birthdate;
    @Column (name= "COUNTRY")
    @NonNull
    private String country;

    // OPTIONAL ATTRIBUTES
    @Column (name= "PHONE")
    private String phone;
    @Column (name= "GENDER")
    private Gender gender;

    public User(String name, LocalDate birthdate, String country) {
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
    }
}

