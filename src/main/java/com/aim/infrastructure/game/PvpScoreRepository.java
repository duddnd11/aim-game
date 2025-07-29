package com.aim.infrastructure.game;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.game.entity.PvpScore;

public interface PvpScoreRepository extends JpaRepository<PvpScore,Long>{

}
