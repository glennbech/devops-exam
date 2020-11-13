package devops.org.eksamen;

import devops.org.eksamen.cards.CardsService;
import devops.org.eksamen.users.User;
import devops.org.eksamen.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import devops.org.eksamen.dto.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import static java.nio.charset.StandardCharsets.UTF_8;


@RequestMapping(path = "/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private UserService userService;
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public RestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<Boolean> createUser(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.status(201).body(userService.registerUser(userDto.getUserName(), userDto.getEmail(), userDto.getName(), userDto.getLastName()));
    }

    @GetMapping(path = "/user/{userName}")
    public ResponseEntity<User> getUserInfo(
            @PathVariable String userName
    ){
        return ResponseEntity.status(200).body(userService.getUser(userName));
    }

    //Get random card for given user
    @PostMapping("/user/card")
    public ResponseEntity<Boolean> getRandomCard(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.status(201).body(userService.addCardToUser(userDto.getUserName(),CardsService.getRandomCard()));
    }

    @GetMapping(
            value = "imgs/{imgId}",
            produces = {MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<String> getImage(
            @PathVariable String imgId
    ){
        String imageName;
        try {
            imageName = CardsService.getCards().get(Integer.parseInt(imgId));
        }catch (Exception e){
            return ResponseEntity.status(404).body("Not found");
        }

        Resource svg = resourceLoader.getResource("classpath:1236106-monsters/svg/" + imageName);
        String imageAsString;
        try (Reader reader = new InputStreamReader(svg.getInputStream(), UTF_8)) {
            imageAsString = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return ResponseEntity.status(200).body(imageAsString);
    }

}
