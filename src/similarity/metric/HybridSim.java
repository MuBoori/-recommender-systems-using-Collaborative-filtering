package similarity.metric;

import profile.Profile;

/*
 * Use multiple similarity metric and take the average
 * */
public class HybridSim implements SimilarityMetric{

	SimilarityMetric cosine;
	SimilarityMetric pearson; 
	SimilarityMetric euclidean; 
	
	double cosineW;
	double pearsonW;
	double euclideanW;
	/**
	 * constructor - creates a new CosineMetric object
	 */

	
	public HybridSim(double cosineW, double pearsonW,double euclideanW)
	{
		cosine = new Cosine();
		pearson = new Pearson();
		euclidean = new EuclideanDistance();
		this.cosineW = cosineW;
		this.pearsonW = pearsonW;
		this.euclideanW = euclideanW;
	}
	
	public double getSimilarity(Profile p1, Profile p2) {
		
		double cosineSim = cosine.getSimilarity(p1, p2);
		double pearsonSim = pearson.getSimilarity(p1, p2);
		double euclideanSim = euclidean.getSimilarity(p1, p2);
		
		return ((cosineSim*cosineW)+(pearsonSim*pearsonW)+(euclideanSim*euclideanW))/(3);
	}

}
