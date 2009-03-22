package mulan.evaluation;

import java.text.DecimalFormat;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class PhiCoefficient {

	int numOfLabels;
	double[][] phi;

	public double[][] calculatePhi(Instances dataSet, int numOfLabels)
			throws Exception {
		this.numOfLabels = numOfLabels;
		int predictors = dataSet.numAttributes() - numOfLabels;
		phi = new double[numOfLabels][numOfLabels];
		// Indices of label attributes
		int indices[] = new int[numOfLabels];
		int k = 0;
		for (int j = 0; j < numOfLabels; j++) {
			indices[k] = predictors + j;
			k++;
		}

		Remove remove = new Remove();
		remove.setInvertSelection(true);
		remove.setAttributeIndicesArray(indices);
		remove.setInputFormat(dataSet);
		Instances result = Filter.useFilter(dataSet, remove);
		result.setClassIndex(result.numAttributes() - 1);

		for (int i = 0; i < numOfLabels; i++) {
			int a[] = new int[numOfLabels];
			int b[] = new int[numOfLabels];
			int c[] = new int[numOfLabels];
			int d[] = new int[numOfLabels];
			double e[] = new double[numOfLabels];
			double f[] = new double[numOfLabels];
			double g[] = new double[numOfLabels];
			double h[] = new double[numOfLabels];
			for (int j = 0; j < result.numInstances(); j++) {
				for (int l = 0; l < numOfLabels; l++) {
					// if (l == i) {
					// continue;
					// }
					if (result.instance(j).value(i) == 0.0) {
						if (result.instance(j).value(l) == 0.0) {
							a[l]++;
						} else {
							c[l]++;
						}
					} else {
						if (result.instance(j).value(l) == 0.0) {
							b[l]++;
						} else {
							d[l]++;
						}
					}
				}
			}
			for (int l = 0; l < numOfLabels; l++) {
				e[l] = a[l] + b[l];
				f[l] = c[l] + d[l];
				g[l] = a[l] + c[l];
				h[l] = b[l] + d[l];

				double mult = e[l] * f[l] * g[l] * h[l];
				double denominator = Math.sqrt(mult);
				double nominator = a[l] * d[l] - b[l] * c[l];
				phi[i][l] = nominator / denominator;

			}
		}
		return phi;
	}

	public void printCorrelations() {
		String pattern = "##.##";
		DecimalFormat myFormatter = new DecimalFormat(pattern);

		for (int i = 0; i < numOfLabels; i++) {
			for (int j = 0; j < numOfLabels; j++) {
				System.out.print(myFormatter.format(phi[i][j]) + " ");
			}
			System.out.println("");
		}
	}
}