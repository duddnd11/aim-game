package com.aim.application.game.dto;

import java.util.UUID;

import com.aim.domain.game.enums.GameMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CircleDto {
	private String circleKey;
	private double x;
	private double z;
	private int radius;
	private int bounce;
	private boolean hit;
	private int clickCount;
	private long createdTime;
	private boolean active;
	
	private double speed;
	private float edgeX;
	private float edgeZ;
	private double vectorX;
	private double vectorZ;
	private double directionX;
	private double directionZ;
	private double length;
	
	private boolean resizeCheck;
	
	private float scale;
	private float maxScale;
	
	private int targetLife;
	
	public CircleDto(GameDto gameDto) {
		this.radius = gameDto.getMinTargetSize();
		this.circleKey = UUID.randomUUID().toString();
		this.x=(Math.random() * 1000 - 500);
		this.z=(Math.random() * 600 - 300);
		this.active = true;
		this.resizeCheck = true;
		this.createdTime = System.currentTimeMillis();
		
		if(gameDto.getGameMode().equals(GameMode.MOVING)) {
			this.setSpeed(gameDto.getMoveSpeed());
			this.setMove();
		}
		
		if(gameDto.getMinTargetSize() != gameDto.getMaxTargetSize()) {
			this.scale = (float)1.0;
			this.maxScale = (float)gameDto.getMaxTargetSize() / this.radius;
		}
		
		this.targetLife = gameDto.getTargetLife();
	}
	
	public void setSpeed(double speed) {
		if(speed > 0){
			this.speed = speed / 2 / 100;
		}else {
			this.speed = (int)(Math.random() * 3);
		}
	}
	
	public void setMove() {
		this.edgeX = 1065 / 2 - this.radius;
		this.edgeZ = 655 / 2 - this.radius;
		this.vectorX = ((Math.random() - 0.5) * 2);
		this.vectorZ = ((Math.random() - 0.5) * 2);
		
		this.length = Math.sqrt(vectorX * vectorX + vectorZ * vectorZ);
		this.directionX = this.vectorX / this.length; // 방향 벡터의 x값을 정규화
		this.directionZ = this.vectorZ / this.length; // 방향 벡터의 z값을 정규화
	}
	
	public void move() {
		this.x += this.directionX * this.speed;
        this.z += this.directionZ * this.speed;
		
		// 화면 경계에 닿을때 방향 반전
		if(this.x >= this.edgeX || this.x <= -this.edgeX){
			this.directionX = -this.directionX;
			this.bounce++;
		}
		
		if(this.z >= this.edgeZ || this.z <= -this.edgeZ){
			this.directionZ = -this.directionZ;
			this.bounce++;
		}
		
		// 최대 튕김 횟수 이상일 경우 삭제
		if(this.bounce >= 5){
			this.active = false;
			this.bounce = -1;
		}
	}
	
	public void resize() {
		if(resizeCheck){
			this.scale+=0.01;
		}else{
			this.scale-=0.01;
		}
		
		if(this.scale >= this.maxScale){
			this.resizeCheck=false;
		}else if(this.scale <= 0){
			this.resizeCheck = true;
			this.active = false;
		}
	}
	
	public void lifeOut() {
		if(System.currentTimeMillis() - this.createdTime >= this.targetLife * 1000) {
			this.active = false;
		}
	}
	
	public void click() {
		this.hit = true;
		this.clickCount++;
	}
	
}
