package similarity.metric;

import java.util.Set;

import profile.Profile;

/*
 * Implementation of Euclindean Distance similarity
 * */
public class EuclideanDistance implements SimilarityMetric {

	public double getSimilarity(Profile p1, Profile p2) {
		// TODO Auto-generated method stub
		
		double sumXYdiff2 = 0;
		  Set<Integer> common = p1.getCommonIds(p2);
			for(Integer id: common)
			{
				double r1 = p1.getValue(id).doubleValue();
				double r2 = p2.getValue(id).doubleValue();
				sumXYdiff2 += Math.pow(r1 - r2, 2);
			}
			
			
		return 1.0 / (1.0 + Math.sqrt(sumXYdiff2) / Math.sqrt(common.size()));
	}

}
