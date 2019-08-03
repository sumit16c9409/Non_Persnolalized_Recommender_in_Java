/**
 * Compute the similarity between two items based on the Cosine between item genome scores
 */

package alg.np.similarity.metric;

import java.util.Iterator;
import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class GenomeMetric implements SimilarityMetric {
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new GenomeMetric object
	 * 
	 * @param reader
	 *            - dataset reader
	 */
	public GenomeMetric(final DatasetReader reader) {
		this.reader = reader;
	}

	/**
	 * computes the similarity between items
	 * 
	 * @param X
	 *            - the id of the first item
	 * @param Y
	 *            - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y) {
		// calculate similarity using Cosine

		// Genome Scores for items x and y respectively
		Profile profileX = reader.getItemGenomeScores().get(X);
		Profile profileY = reader.getItemGenomeScores().get(Y);

		// initialize the dotProduct sum to 0
		double dotProduct = 0.0;

		// itearate on the common ids for x and y using framework method getCommonIds
		for (Integer tagid : profileX.getCommonIds(profileY)) {
			dotProduct += (profileX.getValue(tagid) * profileY.getValue(tagid));
		}
		// calculated the cosine of angle between two items and computed their cosine
		double cosineofXnY = dotProduct / (profileX.getNorm() * profileY.getNorm());
		return cosineofXnY;
	}
}
