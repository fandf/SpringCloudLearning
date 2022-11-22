package com.fandf.mongo.core.geo;

import java.io.Serializable;

public class MultiPolygon extends GeoJSON implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private double[][][][] coordinates;
    
    public MultiPolygon(){
        type = "MultiPolygon";
    }

    public double[][][][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[][][][] coordinates) {
        this.coordinates = coordinates;
    }
    
    public void setPolygons(Polygon... polygons){
        int size = polygons.length;
        coordinates = new double[size][][][];
        for(int i=0; i<size; i++){
            coordinates[i] = polygons[i].getCoordinates();
        }
    }
    
}
