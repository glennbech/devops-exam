package devops.org.eksamen;

import devops.org.eksamen.cards.CardsService;
import devops.org.eksamen.dto.UserDto;
import devops.org.eksamen.users.User;
import devops.org.eksamen.users.UserService;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;


@RequestMapping(path = "/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private Environment env;

    private final MeterRegistry meterRegistry;
    private final UserService userService;
    private final ResourceLoader resourceLoader;
    private static final Logger LOG = LoggerFactory.getLogger(RestController.class.getName());


    @Autowired
    public RestController(UserService userService, ResourceLoader resourceLoader, MeterRegistry meterRegistry) {
        this.userService = userService;
        this.resourceLoader = resourceLoader;
        this.meterRegistry = meterRegistry;
    }

    //Register user
    @PostMapping(path = "/user")
    public ResponseEntity<Boolean> createUser(
            @RequestBody UserDto userDto
    ) {
        LOG.info("Creating new user");
        meterRegistry
                .counter("http_post_request", "api/user", userDto.getUserName())
                .increment();
        return ResponseEntity
                .status(201)
                .body(userService.registerUser(userDto.getUserName(), userDto.getEmail(), userDto.getName(), userDto.getLastName()));
    }

    @GetMapping(path = "/user/{userName}")
    public ResponseEntity<User> getUserInfo(
            @PathVariable String userName
    ) {
        LOG.info("Getting user info");
        meterRegistry
                .counter("http_get_request", "user_info_fetch", userName)
                .increment();
        User user = userService.getUser(userName);
        DistributionSummary
                .builder("response_size")
                .tag("user", "info")
                .register(meterRegistry)
                .record(userService.getUserSize(user));

        return ResponseEntity
                .status(200)
                .body(user);
    }

    //Get random card for given user
    @PostMapping("/user/card")
    public ResponseEntity<Boolean> getRandomCard(
            @RequestBody UserDto userDto
    ) {
        LOG.info("Getting random card for user!");
        meterRegistry
                .counter("http_post_request", "local", "eu", "number_of_random_cards_pulled", userDto.getUserName())
                .increment();

        boolean hasGotCard = userService.addCardToUser(userDto.getUserName(), CardsService.getRandomCard());
        if (hasGotCard) {
            User user = userService.getUser(userDto.getUserName());
            meterRegistry.gauge("user_owned_cards", user.getCardsOwned(), List::size);
        }
        return ResponseEntity
                .status(201)
                .body(hasGotCard);
    }

    @GetMapping(
            value = "imgs/{imgId}",
            produces = {MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<String> getImage(
            @PathVariable String imgId
    ) {
        LOG.info("Fetching image data and sending to user. ");
        long startTime = System.currentTimeMillis();

        String imageName;
        try {
            LOG.warn("This call could fail!");
            imageName = CardsService
                    .getCards()
                    .get(Integer.parseInt(imgId));
            meterRegistry
                    .counter("http_get_failed_request", "imgs/{imgId}/", imgId)
                    .increment();
        } catch (Exception e) {
            LOG.error("Well, sry, but it did fail, there is no image with index: " + imgId + " on server ");
            return ResponseEntity
                    .status(404)
                    .body("not found");
        }

        Resource svg = resourceLoader.getResource("classpath:1236106-monsters/svg/" + imageName);
        String imageAsString;
        try (Reader reader = new InputStreamReader(svg.getInputStream(), UTF_8)) {
            LOG.warn("Trying to get image, this call could fail!");
            imageAsString = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            meterRegistry
                    .counter("http_get_failed_request", "image_fetched_failed", imgId)
                    .increment();
            LOG.error("Well, sry, but it did fail. Failed to fetch image with index: " + imgId + "!");
            throw new UncheckedIOException(e);
        }
        meterRegistry
                .counter("http_get_request", "image_fetched", imgId)
                .increment();
        meterRegistry
                .timer("response_builder_timer", "local", "eu")
                .record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);

        return ResponseEntity
                .status(200)
                .body(imageAsString);
    }

    //This is just method for testing
    //Made for long task timer, since I do not have such high consuming process
    //I decided to make "dummy" one
    @GetMapping(path = "/reflect/{msg}")
    public ResponseEntity<String> reflectMessage(
            @PathVariable String msg
    ) {
        LOG.warn("This is going to take long time!");
        LongTaskTimer longTaskTimer = LongTaskTimer
                .builder("reflect_long_response")
                .description("Simulating server heavy operation")
                .tag("localhost", "eu")
                .register(meterRegistry);

        longTaskTimer.record(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);//Simulating heavy operations on server side
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity
                .status(201)
                .body(msg);
    }


}
