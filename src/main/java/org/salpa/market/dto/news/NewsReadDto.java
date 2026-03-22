package org.salpa.market.dto.news;

import lombok.Value;

@Value
public class NewsReadDto {
    Integer id;
    String header;
    String description;
}
