/**
 * Compute the Hybrid similarity between profiles
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package similarity.metric;

import java.util.Map;
import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

/*
 * This class use the the inverse frequency cosine  
 * **/
public class CosineInverseFreq implements SimilarityMetric
{
	private Map<Integer, Profile> itemProfile;
	private Map<Integer, Profile> userProfile;

	/**
	 * constructor - creates a new CosineMetric object
	 */
	public CosineInverseFreq(DatasetReader reader)
	{
		itemProfile = reader.getItemProfiles();
		userProfile = reader.getUserProfiles();
	}
	
	
	
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getSimilarity(final Profile p1, final Profile p2)
	{
        double dotProduct = 0;
        double bottom = 0;
        int m = userProfile.size(); // number of user
        // Top , sum of the i ra,jfj ri,jfj
        Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
		
			double logF = Math.log(m/itemProfile.get(id).getSize());
			double r1 = p1.getValue(id).doubleValue() * logF; 
			double r2 = p2.getValue(id).doubleValue()* logF;
			dotProduct += r1 * r2;
		}

		// Bottom
		
		double leftBottom = 0;
		double rightBottom = 0;
		//p1
		Set<Integer> p1Items = p1.getIds();
		for(Integer id: p1Items)
		{
			double logK = Math.log(m/itemProfile.get(id).getSize());
			leftBottom += p1.getValue(id).doubleValue() * logK;
			 
		}
		
		//p2
		Set<Integer> p2Items = p2.getIds();
		for(Integer id: p2Items)
		{
			double logK = Math.log(m/itemProfile.get(id).getSize());
			
			rightBottom += p2.getValue(id).doubleValue() * logK;
		}
		
		bottom = Math.sqrt(Math.pow(leftBottom, 2)) * Math.sqrt(Math.pow(rightBottom, 2));

		

		// Significance Weighting
		Double sig = (Math.min(Math.abs((double)common.size()), 50)/50);
		
		return (bottom > 0) ? (dotProduct / bottom)*sig : 0;
	}
}
