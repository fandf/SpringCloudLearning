package com.fandf.mongo.core.geo;

import java.io.Serializable;

public class Polygon extends GeoJSON implements Serializable {

    private static final long serialVersionUID = 1L;

    private double[][][] coordinates;

    public Polygon() {
        type = "Polygon";
    }

    public double[][][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[][][] coordinates) {
        this.coordinates = coordinates;
    }

    public void setSingleRing(double[]... points) {
        int size = points.length;
        coordinates = new double[1][size][2];
        for (int i = 0; i < size; i++) {
            coordinates[0][i] = points[i];
        }
    }

    public void setSingleRing(Point... points) {
        int size = points.length;
        coordinates = new double[1][size][2];
        for (int i = 0; i < size; i++) {
            coordinates[0][i] = points[i].getCoordinates();
        }
    }

}
