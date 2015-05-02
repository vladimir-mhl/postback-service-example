package com.callfire.postbackservice;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import static com.callfire.postbackservice.type.NotificationFormatType.JSON;
import static com.callfire.postbackservice.type.TriggerEventType.OUTBOUND_TEXT_FINISHED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Postback controller for processing CallFire api notifications
 * callback method mapped to /postback URL
 */
@RestController
public class PostbackController {

    public static final String POSTBACK_URL = "/postback";

    private static final Logger LOGGER = LoggerFactory.getLogger(PostbackController.class);

    @Value("${app.login}")
    private String login;
    @Value("${app.password}")
    private String password;
    @Value("${app.endpoint}")
    private String endpoint;
    @Value("${callfire.api.subscription}")
    private String api;

    @PostConstruct
    public void createEndpointSubscription() {
        LOGGER.debug("register endpoint {} for user {}", endpoint, login);

        try {
            HttpResponse<JsonNode> subscriptionResponse = Unirest.post(api)
                .header("Accept", APPLICATION_JSON_VALUE)
                .basicAuth(login, password)
                .field("Endpoint", endpoint)
                .field("NotificationFormat", JSON)
                .field("TriggerEvent", OUTBOUND_TEXT_FINISHED)
                .asJson();
            LOGGER.debug("Created subscription {}", subscriptionResponse.getBody().toString());
        } catch (UnirestException e) {
            LOGGER.error("Can't create callback subscription", e);
        }
    }

    @RequestMapping(value = POSTBACK_URL, method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postbackProcess(@RequestBody String payload) {

        // your callback code goes here
        LOGGER.debug("received message from callfire: {}", payload);

        return new ResponseEntity(NO_CONTENT);
    }
}
