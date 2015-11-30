/**
 * An class to compute the target items's predicted rating for the target user (item-based CF) using Resnick Algorightm.
 * 
 * Derek Organ
 * 08/02/2012
 */

package alg.ib.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;


public class Resnick implements Predictor {

	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	public Resnick()
	{
		
	}
	
	
	public Double getPrediction(final Integer itemId, final Integer userId, final Map<Integer,Profile> itemProfileMap, final Map<Integer,Profile> userProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap)
	{
		double above = 0;
		double bottom = 0;
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(itemId, userId, userProfileMap, simMap); // get the neighbours

		if(itemProfileMap.get(itemId) == null) return null; // if there is no item for the user profile
		double meanProfile = itemProfileMap.get(itemId).getMeanValue(); // get the mean of the item profile
	
		for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
		{

			Double sim = simMap.getSimilarity(itemId, neighbours.get(i));
			Double mean = itemProfileMap.get(neighbours.get(i)).getMeanValue();
			Double rating = itemProfileMap.get(neighbours.get(i)).getValue(userId); // get the neighbour's rating for the target user
			
			if(rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			
			above += (rating - mean) * sim ;
			bottom += Math.abs(sim);
		}
				
	
		if(neighbours.size() > 20)
			return  new Double(meanProfile + (above / bottom)) ;
		else
			return null;
	}
	
}
