package alg.ub.predictor;

import java.util.ArrayList;
import java.util.Map;

import profile.Profile;
import similarity.SimilarityMap;
import alg.ub.neighbourhood.Neighbourhood;

public class WeightedAverage implements Predictor{

	public Double getPrediction(Integer userId, Integer itemId,
			Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood,
			SimilarityMap simMap) {
		
			double above = 0;
			double bottom = 0;
			
			ArrayList<Integer> neighbours = neighbourhood.getNeighbours(userId, itemId, itemProfileMap, simMap); // get the neighbours
			
			
			for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
			{
				double w = simMap.getSimilarity(userId, neighbours.get(i))/16;;
				Double rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); // get the neighbour's rating for the target item
				if(rating == null)
				{
					System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
					System.exit(1);
				}
				
				above += w * rating.doubleValue();
				bottom += w;
			}
					
			if(neighbours.size() > 0)
				return new Double(above / bottom);
			else
				return null;
		}
		

}
