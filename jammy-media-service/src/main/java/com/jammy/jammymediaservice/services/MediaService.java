package com.jammy.jammymediaservice.services;

import com.jammy.jammymediaservice.repositories.MediaDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MediaService {
    private final MediaDataRepository mediaDataRepository;
}
