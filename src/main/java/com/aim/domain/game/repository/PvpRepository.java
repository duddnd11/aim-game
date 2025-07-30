package com.aim.domain.game.repository;

import java.util.Optional;

import com.aim.domain.game.entity.Pvp;

public interface PvpRepository {
	Optional<Pvp> findById(Long pvpId);
	
	Pvp save(Pvp pvp);
}
