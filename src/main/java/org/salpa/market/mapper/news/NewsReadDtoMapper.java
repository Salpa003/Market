package org.salpa.market.mapper.news;

import org.salpa.market.dto.news.NewsReadDto;
import org.salpa.market.entity.news.News;
import org.salpa.market.mapper.Mapper;
import org.springframework.stereotype.Component;


@Component
public class NewsReadDtoMapper implements Mapper<News, NewsReadDto> {
    @Override
    public NewsReadDto map(News news) {
        return new NewsReadDto(news.getId(), news.getHeader(), news.getDescription());
    }
}
