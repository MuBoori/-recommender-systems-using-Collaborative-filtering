package alg.ub.neighbourhood;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import profile.Profile;
import similarity.SimilarityMap;
import util.ScoredThingDsc;

public class ThresholdNeighbourhood implements Neighbourhood {
	
	private final int k; // the number of neighbours in the neighbourhood
	private final double thresold;
	
	/**
	 * constructor - creates a new NearestNeighbourhood object
	 * @param k - the number of neighbours in the neighbourhood
	 * @param threshold - set threshold
	 */
	public ThresholdNeighbourhood(final int k, double threshold)
	{
		this.k = k;
		this.thresold = threshold;
	}
	

	/**
	 * @returns the neighbour IDs
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param itemProfileMap - a map containing item profiles
	 * @param simMap - a map containing user-user similarities
	 */
	public ArrayList<Integer> getNeighbours(final Integer userId, final Integer itemId, final Map<Integer,Profile> itemProfileMap, final SimilarityMap simMap)
	{
		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); // store all user IDs in order of descending similarity in a sorted set

		if(itemProfileMap.containsKey(itemId))
		{
			for(Iterator<Integer> it = itemProfileMap.get(itemId).getIds().iterator(); it.hasNext(); ) // iterate over each user in the item profile
			{
				Integer id = it.next();
				double sim = simMap.getSimilarity(userId, id);
				
				
				if(sim > thresold){ // threshold
					
                    ss.add(new ScoredThingDsc(sim, id));	
				}
			}
		}
		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(); // get the most similar neighbours
		
		int kCounter = 0;
		for(Iterator<ScoredThingDsc> it = ss.iterator(); it.hasNext(); )
		{
			kCounter++;
		
			ScoredThingDsc st = it.next();
			neighbours.add((Integer)st.thing);
			
			if (kCounter > k)
				break;
			
		}
		
		return neighbours;
	}

}
