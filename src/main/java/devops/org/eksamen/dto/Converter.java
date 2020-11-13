package devops.org.eksamen.dto;

import devops.org.eksamen.users.User;

public class Converter {
    public static UserDto convertUserToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}
