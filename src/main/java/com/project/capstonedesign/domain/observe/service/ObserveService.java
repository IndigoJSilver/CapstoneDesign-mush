package com.project.capstonedesign.domain.observe.service;

import com.project.capstonedesign.common.service.S3Uploader;
import com.project.capstonedesign.domain.observe.Observe;
import com.project.capstonedesign.domain.observe.dto.ObserveResponse;
import com.project.capstonedesign.domain.observe.repository.ObserveRepository;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ObserveService {

    private final ObserveRepository observeRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long saveMushroom(Long userId, ObserveResponse observeResponse, MultipartFile image) {
        User user = userService.findById(userId);
        String imagePath = null;
        if(!image.isEmpty()) {
            try {
                imagePath = s3Uploader.upload(image, "observe");
            } catch (IOException e) {
                throw new IllegalArgumentException("이미지 업로드 실패");
            }
        }

        Observe observe = Observe.builder()
                .lng(observeResponse.getLng())
                .lat(observeResponse.getLat())
                .image(imagePath)
                .build();

        Observe saveObserve = observeRepository.save(observe);
        return saveObserve.getObserveId();
    }
}
