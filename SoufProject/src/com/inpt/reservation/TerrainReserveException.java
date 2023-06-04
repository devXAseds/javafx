package com.inpt.reservation;

public class TerrainReserveException extends Exception {
	public TerrainReserveException() {
		super("ce Terrain est deja reservee pour cette date!") ; 
	}
}
