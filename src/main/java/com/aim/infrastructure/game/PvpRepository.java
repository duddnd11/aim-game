package com.aim.infrastructure.game;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.game.entity.Pvp;

public interface PvpRepository extends JpaRepository<Pvp,Long>{

}
