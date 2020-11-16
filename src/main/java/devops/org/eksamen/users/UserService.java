package devops.org.eksamen.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(String userName, String email, String name, String lastName){
        if(userRepository.existsById(userName)){
            return false;
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);

        userRepository.save(user);

        return true;
    }

    public User getUser(String userName){
        if(userRepository.findById(userName).isPresent()){
            return userRepository.findById(userName).get();
        }else{
            return null;
        }
    }

    public boolean addCardToUser(String userName,String cardId){
        User user = getUser(userName);
        if(user==null){
            return false;
        }

        user.getCardsOwned().add(cardId);
        return true;
    }

    /**
     * Calculate size of user
     * All strings --> UTF8 size * number of chars
     * @param user user that we are calculating soze of
     * @return size of user in bytes
     */
    public double getUserSize(User user){
        double sum = 0;
        sum += user.getEmail().length();
        sum += user.getUserName().length();
        sum += user.getName().length();
        sum += user.getLastName().length();
        sum += user.getCardsOwned().stream().mapToInt(String::length).sum();
        return sum;
    }
}
