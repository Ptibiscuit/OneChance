/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.onechance.data;

import com.ptibiscuit.onechance.models.Stats;

/**
 *
 * @author ANNA
 */
public interface DataHandler {
	public Stats createStats(String name, int lives);
	public void deleteStats(String name);
	public void updateStats(String name, int lives);
	public Stats getStats(String name);
	public boolean checkActivity();
}
