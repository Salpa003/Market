package org.salpa.market.service;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.news.NewsReadDto;
import org.salpa.market.dto.news.NewsUpdateDto;
import org.salpa.market.dto.user.UserUpdateDto;
import org.salpa.market.entity.news.News;
import org.salpa.market.entity.user.User;
import org.salpa.market.mapper.news.NewsReadDtoMapper;
import org.salpa.market.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsReadDtoMapper newsReadDtoMapper;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<NewsReadDto> getAllNews() {
        List<News> news = newsRepository.findAll();
        return news.stream().map(newsReadDtoMapper::map).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public byte[] getNewsImage(Integer id) {
        Optional<News> maybeNews = newsRepository.findById(id);
        if (maybeNews.isEmpty())
            return null;
        News news = maybeNews.get();
        Optional<byte[]> bytes = imageService.getNews(news.getImage());
        if (bytes.isEmpty())
            return null;
        return bytes.get();
    }

    @Transactional
    public void createNews() {
        newsRepository.save(News.builder().build());
    }

    @Transactional
    public void updateNews(Integer id, NewsUpdateDto updateDto) {
        Optional<News> maybeNews = newsRepository.findById(id);
        if (maybeNews.isEmpty())
            return;
      News news = maybeNews.get();
        news.setHeader(updateDto.getHeader());
        news.setDescription(updateDto.getDescription());
        if (!updateDto.getImage().isEmpty()) {
            String fileName = id + imageService.getImageFormat(updateDto.getImage());
            try {
                imageService.updateNews(news.getImage(), fileName, updateDto.getImage().getInputStream());
                news.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        newsRepository.save(news);
    }

    @Transactional
    public void deleteNews(Integer id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            imageService.deleteNews(news.get().getImage());
            newsRepository.deleteById(id);
        }

    }
}
