/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.onechance.models;

/**
 *
 * @author ANNA
 */
public class Stats {
	private String name;
	private int lives;

	public Stats(String name, int lives) {
		this.name = name;
		this.lives = lives;
	}

	public int getLives() {
		return lives;
	}

	public String getName() {
		return name;
	}
}
