package com.api.searchengine.service;

import com.api.searchengine.model.Tag;
import com.api.searchengine.repository.elastic.ESTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
//    private final MSTagRepository msTagRepo;

    private final ESTagRepository esTagRepo;

    public List<Tag> getTags() {
//        return msTagRepo.findAll();
        return esTagRepo.findAll();
    }

}
