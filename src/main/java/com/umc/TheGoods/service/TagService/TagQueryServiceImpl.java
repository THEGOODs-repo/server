package com.umc.TheGoods.service.TagService;

import com.umc.TheGoods.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagQueryServiceImpl implements TagQueryService{

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public boolean existTagById(Long id) {
        return tagRepository.existsById(id);
    }
}
