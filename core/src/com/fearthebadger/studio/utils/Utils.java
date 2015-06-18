package com.fearthebadger.studio.utils;

import com.badlogic.gdx.Gdx;

public class Utils {
	// private static final String TAG = "Rotater UsefulFunctions";
	
	private static double deviceRadians;
	private static float deviceAngle;

	public static int randLocation (int min, int max) {
		double rand = Math.random();
		int temp = (int)((rand * (max-min)) + min); 
		
		if (((int)(rand*1000) % 2) == 0) { temp *= -1; }
			
		return temp;
	}
	
	public static float phoneAngle() {
		// Do the maths to find where the phone is...
		deviceRadians = Math.atan2(-Gdx.input.getRoll(), Gdx.input.getPitch());
		deviceAngle = Math.round(((deviceRadians*180)/Math.PI));
		
		// The math returns a number where dead center is 90 and gdx has that position at 0.
		deviceAngle -= 90;
		
		return deviceAngle;
	}
}
