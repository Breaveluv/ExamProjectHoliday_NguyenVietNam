package com.example.footballmanagement.service;

import com.example.footballmanagement.entity.News;
import com.example.footballmanagement.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> getLatestNews(int count) {
        Page<News> page = newsRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "publishedDate")));
        return page.getContent();
    }
}
