package com.fandf.mongo.core.geo;

import java.io.Serializable;

public class LineString extends GeoJSON implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private double[][] coordinates;
    
    public LineString(){
        type = "LineString";
    }
    
    public LineString(double[] start, double[] end){
        type = "LineString";
        coordinates = new double[2][];
        coordinates[0] = start;
        coordinates[1] = end;
    }
    
    public LineString(Point start, Point end){
        type = "LineString";
        coordinates = new double[2][];
        coordinates[0] = start.getCoordinates();
        coordinates[1] = end.getCoordinates();
    }
    
    public double[][] getCoordinates() {
        return coordinates;
    }
    
    public void setCoordinates(Point start, Point end){
        coordinates = new double[2][];
        coordinates[0] = start.getCoordinates();
        coordinates[1] = end.getCoordinates();
    }
    
    public void setCoordinates(double[] start, double[] end){
        coordinates = new double[2][];
        coordinates[0] = start;
        coordinates[1] = end;
    }
    
}
