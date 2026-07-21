package com.example.footballmanagement.service;

import com.example.footballmanagement.entity.Gallery;
import com.example.footballmanagement.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public List<Gallery> getLatestGalleries(int count) {
        Page<Gallery> page = galleryRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "uploadedDate")));
        return page.getContent();
    }
}
