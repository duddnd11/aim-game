package com.aim.infrastructure.game;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.repository.PvpRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PvpRepositoryImpl implements PvpRepository{
	private final PvpJpaRepository pvpJpaRepository;

	@Override
	public Optional<Pvp> findById(Long pvpId) {
		return pvpJpaRepository.findById(pvpId);
	}

	@Override
	public Pvp save(Pvp pvp) {
		return pvpJpaRepository.save(pvp);
	}
}
