package similarity.metric;

import java.util.Set;

import profile.Profile;

/**
 * Pearson correlation
 * */
public class Pearson implements SimilarityMetric{

	public double getSimilarity(Profile p1, Profile p2) {


	        Set<Integer> common = p1.getCommonIds(p2);
			
			 double above = 0.0, bottom1 = 0.0, bottom2 = 0.0;
				for(Integer id: common)
				{
					above += ((p1.getValue(id).doubleValue() -  p1.getMeanValue()) * (p2.getValue(id).doubleValue() -  p2.getMeanValue()));
					bottom1 += Math.pow(p1.getValue(id).doubleValue() - p1.getMeanValue(), 2.0);
					bottom2 += Math.pow(p2.getValue(id).doubleValue() - p2.getMeanValue(), 2.0);
				}
				
		
				

				// Significance Weighting
				Double sig = (Math.min(Math.abs((double)common.size()), 50)/50);
		
		return (bottom1 > 0 && bottom2 > 0) ? (above / (Math.sqrt(bottom1) * Math.sqrt(bottom2)) * sig ) : 0;
	}

}
