package springboot.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.api.utils.Constantes;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotNull(message = Constantes.NOT_NULL)
    private String name;
    @NotNull(message = Constantes.NOT_NULL)
    @JsonFormat(pattern="d/MM/yyyy")
    private String birthdate;
    @NotNull(message = Constantes.NOT_NULL)
    private String country;
    @Pattern(regexp = "(\\+33|0033|0)[0-9]{9}", message = Constantes.NOT_VALID)
    private String phone;
    private String gender;

    public UserDto( String name, String birthdate, String country) {
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
    }
}
