package similarity.metric;

import java.util.Set;

import profile.Profile;

public class TanimotoCoefficient implements SimilarityMetric{

	/*
	 * Implementation of Tanimoto Coefficient
	 * */
	public double getSimilarity(Profile p1, Profile p2) {
		// TODO Auto-generated method stub
		int p1IdSize = p1.getIds().size();
		int p2IdSize = p2.getIds().size();
		
		Set<Integer> common = p1.getCommonIds(p2);
		   if (p1IdSize == 0 && p2IdSize == 0) {
			      return Double.NaN;
			    }
		   
		   if (p1IdSize == 0 || p2IdSize == 0) {
			      return 0;
			    }
		    int intersectionSize =
		    		p1IdSize < p2IdSize ? p1.getCommonIds(p2).size() : p2.getCommonIds(p1).size();
		
		    	    if (intersectionSize == 0) {
		    	        return Double.NaN;
		    	      }
		    	    
		    	 
		  int unionSize = p1IdSize + p2IdSize - intersectionSize;
		  

		  return (double) intersectionSize / (double) unionSize;
	}

}
