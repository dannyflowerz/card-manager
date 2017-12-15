package io.github.dannyflowerz.cardmanager.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import io.github.dannyflowerz.cardmanager.model.Card;

@Slf4j
@Component
public class CardsListener {

    @Getter private int messageCounter;

    @StreamListener(Sink.INPUT)
    public void onCardMessage(Card card) {
        messageCounter++;
        log.info("CARD #" + messageCounter + ": " + card);
    }

}
