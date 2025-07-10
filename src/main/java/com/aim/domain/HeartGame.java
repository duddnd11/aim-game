package com.aim.domain;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;

@Entity
@Getter
@DynamicInsert
public class HeartGame {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long heartGameId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Enumerated(EnumType.STRING)
	private YnType useYn;
	
	public HeartGame() {}
	
	public HeartGame(Game game, Member member) {
		this.game=game;
		this.member=member;
		this.useYn=YnType.Y;
	}
	
	public void changeUse() {
		this.useYn = this.useYn.equals(YnType.Y) ? YnType.N : YnType.Y;
	}
	
	@PrePersist
    public void prePersist() {
        if (this.useYn == null) {
            this.useYn = YnType.Y;
        }
    }
}
