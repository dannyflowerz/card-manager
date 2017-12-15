package io.github.dannyflowerz.cardmanager.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    private String pan;
    private String expirationDate; // YYMM

}
