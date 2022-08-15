package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.resource;



import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.config.KafkaConfiguration;
import com.todebpatikajavaspringbootcampcreditscoreapplicationproject.model.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class CreditResource {

    @Autowired
    @Qualifier("kafkaSimpleMessageTemplate")
    private KafkaTemplate<String, String> kafkaSimpleMessageTemplate;

    @Autowired
    @Qualifier("kafkaObjectTemplate")
    private KafkaTemplate<String, Credit> kafkaObjectTemplate;

    @GetMapping("/publish/{message}")
    public String publishMessage(@PathVariable final String message) {
        kafkaSimpleMessageTemplate.send(KafkaConfiguration.KAFKA_EXAMPLE, message);
        return "Message : " + message + " published successfully";
    }

    @PostMapping("/publish")
    public String publishUser(@RequestBody final Credit credit) {
        kafkaObjectTemplate.send(KafkaConfiguration.KAFKA_EXAMPLE_JSON, credit);
        return "Credit : " + credit + "published successfully";
    }

    public String publishCredit(final Credit credit) {
        kafkaObjectTemplate.send(KafkaConfiguration.KAFKA_EXAMPLE_JSON, credit);
        return "Credit : " + credit + "published successfully";
    }

}
