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
 *    RankingLoss.java
 *    Copyright (C) 2009 Aristotle University of Thessaloniki, Thessaloniki, Greece
 *
 */
package mulan.evaluation.measure;

import java.util.ArrayList;
import mulan.classifier.MultiLabelOutput;

/**
 * Implementation of the ranking loss measure.
 * 
 * @author Grigorios Tsoumakas
 */
public class RankingLoss extends ExampleBasedMeasure {

    public String getName() {
        return "Ranking Loss";
    }

    public double updateInternal(MultiLabelOutput output, boolean[] trueLabels) {

        // gather indexes of true and false labels
        // indexes of true and false labels
        int numLabels = trueLabels.length;
        ArrayList<Integer> trueIndexes = new ArrayList<Integer>();
        ArrayList<Integer> falseIndexes = new ArrayList<Integer>();
        for (int labelIndex = 0; labelIndex < numLabels; labelIndex++) {
            if (trueLabels[labelIndex]) {
                trueIndexes.add(labelIndex);
            } else {
                falseIndexes.add(labelIndex);
            }
        }

        //======ranking loss related=============
        if (trueIndexes.size() != 0 && falseIndexes.size() != 0) {
            int[] ranks = output.getRanking();
            int rolp = 0; // reversed ordered label pairs
            for (int k : trueIndexes) {
                for (int l : falseIndexes) {
                    //	if (output[instanceIndex].getConfidences()[trueIndexes.get(k)] <= output[instanceIndex].getConfidences()[falseIndexes.get(l)])
                    if (ranks[k] > ranks[l])
                    {
                        rolp++;
                    }
                }
            }
            double rloss = (double) rolp / (trueIndexes.size() * falseIndexes.size());
            
            sum += rloss;
            count++;

            return rloss;
        } else
            return Double.NaN;
    }

    @Override
    public double getIdealValue() {
        return 1;
    }
}