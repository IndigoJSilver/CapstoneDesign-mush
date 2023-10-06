package com.project.capstonedesign.common.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.capstonedesign.domain.mushroom.Mushroom;
import com.project.capstonedesign.domain.mushroom.WhichMush;
import com.project.capstonedesign.domain.mushroom.repository.MushroomRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
//@Service
public class MushroomInitService {

    private final MushroomRepository mushroomRepository;

    @PostConstruct
    public void initialize() {
        try {
            String jsonFilePath = "mushrooms.json"; // JSON 파일 경로
            importMushroomsFromJson(jsonFilePath);
        } catch (IOException e) {
            log.error("MushroomInitService.initialize() : IOException",e);
        }
    }

    public void importMushroomsFromJson(String jsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MushroomDto> mushroomDtoList = objectMapper.readValue(new ClassPathResource(jsonFilePath).getFile(), new TypeReference<>() {
        });

        for (MushroomDto mushroomDto : mushroomDtoList) {
            Mushroom.MushroomBuilder builder = Mushroom.builder()
                    .mushId(mushroomDto.getKey())
                    .name(mushroomDto.getName())
                    .feature(mushroomDto.getFeature())
                    .rarity(mushroomDto.getRarity())
                    .image(mushroomDto.getUrl())
                    .isCatched("false");// 기본값 설정
            if ("식용버섯".equals(mushroomDto.getType())) {
                builder.whichMush(WhichMush.EAT);
            } else if ("독버섯".equals(mushroomDto.getType())) {
                builder.whichMush(WhichMush.POISON);
            } else {
                builder.whichMush(WhichMush.NOTFOUND);
            }
            mushroomRepository.save(builder.build());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    static class MushroomDto {
        private Long key;
        private String name;
        private String feature;
        private Long rarity;
        private String url;
        private String type;
    }
}