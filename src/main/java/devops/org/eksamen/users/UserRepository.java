package devops.org.eksamen.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface UserRepository extends CrudRepository<User, String> {

}
