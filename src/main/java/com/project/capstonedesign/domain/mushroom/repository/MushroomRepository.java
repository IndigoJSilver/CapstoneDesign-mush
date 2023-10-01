package com.project.capstonedesign.domain.mushroom.repository;

import com.project.capstonedesign.domain.mushroom.Mushroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MushroomRepository extends JpaRepository<Mushroom, Long> {
}
