/**
 * Compute the similarity between two items based on the Cosine between item ratings
 */

package alg.np.similarity.metric;

import java.util.Map;
import java.util.Map.Entry;

import profile.Profile;
import util.UserItemPair;
import util.reader.DatasetReader;

public class RatingMetric implements SimilarityMetric {
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * 
	 * @param reader
	 *            - dataset reader
	 */
	public RatingMetric(final DatasetReader reader) {
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
		// Profiles for item X and Y
		Profile profileX = reader.getItemProfiles().get(X);
		Profile profileY = reader.getItemProfiles().get(Y);

		// initialize the dotProduct sum to 0
		double dotProduct = 0.0;

		// itearate on the common ids for x and y using framework method getCommonIds
		for (Integer userId : profileX.getCommonIds(profileY)) {
			double ratingx = (profileX.getValue(userId)) != null ? profileX.getValue(userId) : 0;
			double ratingy = (profileY.getValue(userId)) != null ? profileY.getValue(userId) : 0;
			dotProduct += (ratingx * ratingy);
		}

		// calculated the cosine of angle between two items and computed their cosine
		double cosineofXnY = dotProduct / (profileX.getNorm() * profileY.getNorm());
		return cosineofXnY;

	}
}
