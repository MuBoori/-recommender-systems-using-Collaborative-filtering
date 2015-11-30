package similarity.metric;

import profile.Profile;

/**
 * Implementation of Manhattan Distance
 * */
public class ManhattanDistance implements SimilarityMetric{

	public double getSimilarity(Profile p1, Profile p2) {
		// TODO Auto-generated method stub
		int p1IdSize = p1.getIds().size();
		int p2IdSize = p2.getIds().size();
		
	    int intersectionSize =
	    		p1IdSize < p2IdSize ? p1.getCommonIds(p2).size() : p2.getCommonIds(p1).size();
		
	    		int distance = p1IdSize + p2IdSize - 2 * intersectionSize;
	    		return 1.0 / (1.0 + distance);
	}

}
