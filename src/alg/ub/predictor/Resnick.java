package alg.ub.predictor;

import java.util.ArrayList;
import java.util.Map;

import profile.Profile;
import similarity.SimilarityMap;
import alg.ub.neighbourhood.Neighbourhood;

/**
 * Implementation of Resnick formula 
 * */

public class Resnick implements Predictor{

	public Double getPrediction(Integer userId, Integer itemId,
			Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood,
			SimilarityMap simMap) 
	{
		double above = 0;
		double bottom = 0;
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(userId, itemId, itemProfileMap, simMap); // get the neighbours

		double meanProfile = userProfileMap.get(userId).getMeanValue(); // get the mean of the user profile
	
		for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
		{

			Double sim = simMap.getSimilarity(userId, neighbours.get(i));
			Double mean = userProfileMap.get(neighbours.get(i)).getMeanValue();
			Double rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); // get the neighbour's rating for the target item
			
			if(rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			
			above += (rating - mean) * sim ;
			bottom += Math.abs(sim);
		}
				
	
		if(neighbours.size() > 0)
			return  new Double(meanProfile + (above / bottom)) ;
		else
			return null;
	}
	

}
