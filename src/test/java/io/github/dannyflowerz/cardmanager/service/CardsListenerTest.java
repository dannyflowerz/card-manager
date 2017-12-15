package io.github.dannyflowerz.cardmanager.service;

import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.dannyflowerz.cardmanager.model.Card;
import io.github.dannyflowerz.cardmanager.service.CardsListener;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(workOffline = true, ids = "io.github.dannyflowerz:list-cards:0.0.1-SNAPSHOT:stubs")
public class CardsListenerTest {

    @Autowired private CardsListener cardsListener;
    @Autowired private Sink sink;
    @Autowired private StubTrigger stubTrigger;

    @Test
    public void test_should_consume_card_message() throws Exception {
        Message<Card> message = MessageBuilder.withPayload(Card.builder().pan("0123456789012345").expirationDate("2208").build()).build();
        SubscribableChannel channel = sink.input();
        channel.send(message);

        BDDAssertions.then(cardsListener.getMessageCounter()).isGreaterThan(0);
    }

    @Test
    public void test_should_consume_card_message_integration() throws Exception {
        stubTrigger.trigger("triggerCard");

        BDDAssertions.then(cardsListener.getMessageCounter()).isGreaterThan(0);
    }

}