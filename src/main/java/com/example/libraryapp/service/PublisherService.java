package com.example.libraryapp.service;

import com.example.libraryapp.entity.Publisher;
import com.example.libraryapp.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher getOrCreatePublisher(String name) {
        return publisherRepository.findByPublisherName(name)
                .orElseGet(() -> {
                    Publisher publisher = new Publisher();
                    publisher.setPublisherName(name);
                    return publisherRepository.save(publisher);
                });
    }

    public List<Publisher> getTop2Publishers() {
        return publisherRepository.findAll().stream()
                .limit(2)
                .toList();
    }
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
}
