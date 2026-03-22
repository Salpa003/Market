package org.salpa.market.dto.news;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class NewsUpdateDto {
    String header;
    String description;
    MultipartFile image;
}
