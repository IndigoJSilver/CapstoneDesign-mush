package com.project.capstonedesign.domain.mushroom.service;

import com.project.capstonedesign.domain.mushroom.Mushroom;
import com.project.capstonedesign.domain.mushroom.dto.MushroomResponse;
import com.project.capstonedesign.domain.mushroom.exception.NotFoundMushroomException;
import com.project.capstonedesign.domain.mushroom.repository.MushroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MushroomService {

    private final MushroomRepository mushroomRepository;

    @Transactional(readOnly = true)
    public Mushroom findById(Long mushId, MushroomResponse mushroomResponse) {
        return mushroomRepository.findById(mushId)
                .orElseThrow(() -> new NotFoundMushroomException(String.format("Mushroom is not found.")));
    }

    @Transactional(readOnly = true)
    public List<Mushroom> findAll() {
        return mushroomRepository.findAll();
    }
}
