package springboot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Column (name= "NAME", unique = true)
    private String name;
    @Column (name= "BIRTHDATE")
    private LocalDate birthdate;
    @Column (name= "COUNTRY")
    private String country;

    // OPTIONAL ATTRIBUTES
    @Column (name= "PHONE")
    private String phone;
    @Column (name= "GENDER")
    private Gender gender;
}

