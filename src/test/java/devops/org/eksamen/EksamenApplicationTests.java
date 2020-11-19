package devops.org.eksamen;

import devops.org.eksamen.dto.Converter;
import devops.org.eksamen.dto.UserDto;
import devops.org.eksamen.users.User;
import devops.org.eksamen.users.UserRepository;
import devops.org.eksamen.users.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EksamenApplicationTests {

    @LocalServerPort
    protected int port = 0;

    @Autowired
    protected UserService userService;


    @Autowired
    protected UserRepository userRepository;

    @PostConstruct
    void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    private void initTest() {
        //Each test should be independent, destroy all
        userRepository.deleteAll();
    }

    @Test
    void testCreateAndGetUser() {
        String userName = "foo";

        userService.registerUser(userName, userName + "@email.com", userName + "_name", userName + "_last_name");
        RestAssured.port = port;

        given()
                .get("http://localhost/api/user/" + userName)
                .then()
                .statusCode(200);
    }

    @Test
    void testCreateUserAndGetCard() {
        String userName = "foo";

        userService.registerUser(userName, userName + "@email.com", userName + "_name", userName + "_last_name");
        RestAssured.port = port;

        given()
                .get("/user/" + userName)
                .then()
                .statusCode(200);
        UserDto userDto = Converter.convertUserToUserDto(userService.getUser(userName));
        given()
                .contentType(ContentType.JSON)
                .body(userDto)
                .post("/user/card")
                .then()
                .statusCode(201);

        //Check if user has gotten card
        User user = userService.getUser(userName);
        assertEquals(1, user.getCardsOwned().size());
    }

    @Test
    void testFetchSvg() {
        given()
                .get("imgs/1")
                .then()
                .statusCode(200);
    }

}
