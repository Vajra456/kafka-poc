package poc.uidai.com.kafkapoc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;


import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import poc.uidai.com.kafkapoc.data.entity.Greeting;
import poc.uidai.com.kafkapoc.service.KafkaPoCService;

@RestController
@RequestMapping("api/kafka")
public class KafkaPocController {

    private static final Logger LOGGER = Logger.getLogger(KafkaPocController.class.getName());

    @Autowired
    private KafkaPoCService kafkaPocService;

    @PostMapping(path = "/send", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public String sendMessage(@RequestBody Greeting greeting) throws Exception {
        LOGGER.info("Message received: " + greeting.getMessage());

        kafkaPocService.sendMessage(greeting);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return "Message received: " + greeting.getMessage();
    }

}


