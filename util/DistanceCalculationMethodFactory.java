package com.mlaskows.tsplib.util;

import com.mlaskows.tsplib.datamodel.Node;
import com.mlaskows.tsplib.exception.TspLibException;
import com.mlaskows.tsplib.datamodel.types.EdgeWeightType;

import java.util.function.BiFunction;

/**
 * Created by mlaskows on 22/04/2017.
 */
public class DistanceCalculationMethodFactory {

    private static final double EARTH_RADIUS = 6378.388;

    public static BiFunction<Node, Node, Integer>
    getDistanceCalculationMethod(EdgeWeightType edgeWeightType) {
        switch (edgeWeightType) {
            case EUC_2D:
                return getEuc2dFunction();
            case GEO:
                return getGeoFunction();
            case MAN_2D:
                return getMan2dFunction();
            case MAX_2D:
                return getMax2dFunction();
            case CEIL_2D:
                return getCeil2dFunction();
            case ATT:
                return getAttFunction();
            default:
                throw new TspLibException(edgeWeightType + " not implemented yet");
        }
    }

    private static BiFunction<Node, Node, Integer> getEuc2dFunction() {
        return (i, j) -> {
            final double xd = i.getX() - j.getX();
            final double yd = i.getY() - j.getY();
            return (int) (Math.sqrt(xd * xd + yd * yd) + 0.5);
        };
    }

    private static BiFunction<Node, Node, Integer> getGeoFunction() {
        return (i, j) -> {
            final double latitudeI = convertToRadians(i.getX());
            final double longitudeI = convertToRadians(i.getY());
            final double latitudeJ = convertToRadians(j.getX());
            final double longitudeJ = convertToRadians(j.getY());
            final double q1 = Math.cos(longitudeI - longitudeJ);
            final double q2 = Math.cos(latitudeI - latitudeJ);
            final double q3 = Math.cos(latitudeI + latitudeJ);
            return (int) (EARTH_RADIUS *
                    Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
        };
    }

    private static double convertToRadians(Double v) {
        int deg = v.intValue();
        final double min = v - deg;
        return Math.PI * (deg + 0.5 * min / 3.0) / 180;
    }

    private static BiFunction<Node, Node, Integer> getMan2dFunction() {
        return (i, j) -> {
            final double xd = Math.abs(i.getX() - j.getX());
            final double yd = Math.abs(i.getY() - j.getY());
            return (int) (xd + yd);
        };
    }

    public static BiFunction<Node, Node, Integer> getMax2dFunction() {
        return (i, j) -> {
            final double xd = Math.abs(i.getX() - j.getX());
            final double yd = Math.abs(i.getY() - j.getY());
            return (int) Math.max(xd, yd);
        };
    }

    private static BiFunction<Node, Node, Integer> getCeil2dFunction() {
        return (i, j) -> (int) Math.ceil(getEuc2dFunction().apply(i, j));
    }

    private static BiFunction<Node, Node, Integer> getAttFunction() {
        return (i, j) -> {
            final double xd = i.getX() - j.getX();
            final double yd = i.getY() - j.getY();
            final double rij = Math.sqrt((xd * xd + yd * yd) / 10.0);
            final double tij = cutDecimal(rij);
            final int dij;

            if (tij < rij) {
                dij = (int) tij + 1;
            } else {
                dij = (int) tij;
            }

            return dij;
        };
    }

    private static double cutDecimal(double x) {
        final int k = (int) x;
        return (double) k;
    }

}
