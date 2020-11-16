package devops.org.eksamen.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String userName;

    private String email;

    private String name;

    private String lastName;

    private List<String> cardsOwned;

}
