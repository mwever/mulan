/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    GeometricMeanAverageInterpolatedPrecision.java
 *    Copyright (C) 2009-2012 Aristotle University of Thessaloniki, Greece
 */
package mulan.evaluation.measure;

/**
 * Implementation of GMAiP (Geometric Mean Average Interpolated Precision)
 *
 * @author Fragkiskos Chatziasimidis
 * @author John Panagos
 * @version 2012.05.24
 */public class GeometricMeanAverageInterpolatedPrecision extends MeanAverageInterpolatedPrecision {

    public GeometricMeanAverageInterpolatedPrecision(int numOfLabels, int recallLevels) {
        super(numOfLabels, recallLevels);
    }

    @Override
    public String getName() {
        return "Geometric Mean Average Interpolated Precision";
    }

    @Override
    public double getValue() {
        calculateAverageInterpolatedPrecisions();
        double productAvgPre = 1;
        for (int labelIndex = 0; labelIndex < numOfLabels; labelIndex++) {
            productAvgPre = productAvgPre * averageInterpolatedPrecision[labelIndex];
        }
        return Math.pow(productAvgPre, 1.0 / numOfLabels);
    }
}