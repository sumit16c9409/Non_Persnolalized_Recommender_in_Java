/**
 * Compute the similarity between two items based on increase in confidence
 */

package alg.np.similarity.metric;

import profile.Profile;
import util.reader.DatasetReader;

public class IncConfidenceMetric implements SimilarityMetric {
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new IncConfidenceMetric object
	 * 
	 * @param reader
	 *            - dataset reader
	 */
	public IncConfidenceMetric(final DatasetReader reader) {
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
		// calculate similarity using conf(X => Y) / conf(!X => Y)

		// Profiles for item X and Y
		Profile profileX = reader.getItemProfiles().get(X);
		Profile profileY = reader.getItemProfiles().get(Y);

		// Get Total Users
		int totalUsers = reader.getUserProfiles().size();

		// Initialized sums for all supports to be calculated
		double supportCountX = 0.0, supportCountXNY = 0.0, supportCountNotX = 0.0, supportCountNotXNY = 0.0;
		double supportX = 0.0, supportXNY = 0.0, supportNotX = 0.0, supportNotXNY = 0.0;
		// Iterate to get the support counts
		for (Integer userId : profileX.getIds()) {

			// If value is less than threshold and zero no increment
			supportCountX += profileX.getValue(userId) != null && profileX.getValue(userId) >= RATING_THRESHOLD ? 1 : 0;

			// If value is less than threshold and zero no increment for both the profiles
			supportCountXNY += (profileX.getValue(userId) != null && profileX.getValue(userId) >= RATING_THRESHOLD)
					&& (profileY.getValue(userId) != null && profileY.getValue(userId) >= RATING_THRESHOLD) ? 1 : 0;

			// If value is less than threshold and not zero increment the support Counter
			// for suppNotX
			supportCountNotX += profileX.getValue(userId) != null && profileX.getValue(userId) < RATING_THRESHOLD ? 1
					: 0;

			// If value is less than threshold and not zero increment the support Counter
			// for suppNotX and supportNotY
			supportCountNotXNY += (profileX.getValue(userId) != null && profileX.getValue(userId) < RATING_THRESHOLD)
					&& (profileY.getValue(userId) != null && profileY.getValue(userId) >= RATING_THRESHOLD) ? 1 : 0;

		}
		// Calculate Supports
		supportX = supportCountX / totalUsers;
		supportNotX = supportCountNotX / totalUsers;
		supportXNY = supportCountXNY / totalUsers;
		supportNotXNY = supportCountNotXNY / totalUsers;
		// calculated confidence x and confidenceNotXNY with null checks
		double confidenceX = supportX != 0.0 ? (supportXNY / supportX) : 0.0;
		double confidenceNotXNY = supportNotX != 0.0 ? (supportNotXNY / supportNotX) : 0.0;
		// Calcuated similarity
		double simlarity = confidenceNotXNY != 0.0 ? confidenceX / confidenceNotXNY : 0.0;

		return simlarity;
	}
}
