package com.app.test.util;

import org.springframework.core.env.Environment;

public class LocationUtility {
    public static boolean distanceTo(double latitude1, double longitude1, double latitude2, double longitude2, int radius) {
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(latitude1);
        double lon1 = Math.toRadians(longitude1);
        double lat2 = Math.toRadians(latitude2);
        double lon2 = Math.toRadians(longitude2);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        if (statuteMiles <= radius)
            return true;
        return false;
    }

    public static double[] getCityCoordinates(Environment env, String city){
        double latitude = Double.parseDouble(env.getProperty(city.toLowerCase()+".lat"));
        double longitude = Double.parseDouble(env.getProperty(city.toLowerCase()+".lon"));
        return new double[]{latitude, longitude};
    }
}
