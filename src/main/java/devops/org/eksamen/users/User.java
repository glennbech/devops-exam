package devops.org.eksamen.users;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    private String userName;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> cardsOwned;
}
