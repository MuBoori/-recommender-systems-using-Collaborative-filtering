/**
 * An interface to compute the target user's predicted rating for the target item (user-based CF)
 *
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ib.predictor;

import java.util.Map;


import alg.ib.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;

public interface Predictor 
{
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numeric ID of the target item
	 * @param userProfileMap - a map containing user profiles
	 * @param itemProfileMap - a map containing item profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a map containing user-user similarities
	 */
	public Double getPrediction(final Integer itemId, final Integer userId, final Map<Integer,Profile> itemProfileMap, final Map<Integer,Profile> userProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap);

}
