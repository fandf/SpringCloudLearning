package com.fandf.mongo.core.geo;

import java.io.Serializable;

public class Point extends GeoJSON implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private double[] coordinates;  //element 0 is longtitude, element 1 is latitude
    
    public Point(){
        type = "Point";
    }
    
    public Point(double longtitude, double latitude){
        type = "Point";
        coordinates = new double[2];
        coordinates[0] = longtitude;
        coordinates[1] = latitude;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
    
    public double getLongitude(){
        return coordinates==null ? 0 : coordinates[0];
    }
    
    public void setLongitude(double longitude){
        if(coordinates == null){
            coordinates = new double[2];
        }
        coordinates[0] = longitude;
    }
    
    public double getLatitude(){
        return coordinates==null ? 0 : coordinates[1];
    }
    
    public void setLatitude(double latitude){
        if(coordinates == null){
            coordinates = new double[2];
        }
        coordinates[1] = latitude;
    }
    
}
