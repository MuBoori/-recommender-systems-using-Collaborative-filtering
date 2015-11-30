package alg.hybird;

import similarity.SimilarityMap;
import similarity.metric.SimilarityMetric;
import util.reader.DatasetReader;
import alg.CFAlgorithm;
import alg.ib.predictor.Predictor;



public class HybirdBasedCF implements CFAlgorithm{
	
	private alg.ib.predictor.Predictor iBpredictor; // the predictor technique  
	private alg.ib.neighbourhood.Neighbourhood iBneighbourhood; // the neighbourhood technique
	private alg.ub.predictor.Predictor uBpredictor; // the predictor technique  
	private alg.ub.neighbourhood.Neighbourhood uBneighbourhood; // the neighbourhood technique
	private DatasetReader reader; // dataset reader
	private SimilarityMap uBsimMap; // similarity map - stores all user-user similarities
	private SimilarityMap iBsimMap; // similarity map - stores all item-item similarities
	
	
	/**
	 * @param itemBasePredictor Item-Based predictor
	 * @param userBasedPredictor User-Based Prdictor
	 * @param itemBasedNeighbourhood - Item-Based neighbourhood
	 * @param userBasedNeighbourhood - User - Based Neighbourhood
	 * @param iBmetric Similarilty Matric
	 * @param uBmetric
	 * @param reader
	 */
	public HybirdBasedCF(alg.ib.predictor.Predictor itemBasePredictor, alg.ub.predictor.Predictor userBasedPredictor,
			alg.ib.neighbourhood.Neighbourhood itemBasedNeighbourhood, alg.ub.neighbourhood.Neighbourhood userBasedNeighbourhood,
			SimilarityMetric iBmetric, SimilarityMetric uBmetric, final DatasetReader reader)
	{
		this.reader = reader;
		this.iBpredictor = itemBasePredictor;
		this.uBpredictor = userBasedPredictor;
		this.iBneighbourhood = itemBasedNeighbourhood;
		this.uBneighbourhood = userBasedNeighbourhood;
		this.iBsimMap = new SimilarityMap(reader.getItemProfiles(), iBmetric); // compute all user-user similarities
		this.uBsimMap = new SimilarityMap(reader.getUserProfiles(), uBmetric); // compute all user-user similarities
		
	}
	
	public Double getPrediction(Integer userId, Integer itemId) {

		
		Double ubPrediction = uBpredictor.getPrediction(userId, itemId, reader.getUserProfiles(), reader.getItemProfiles(), uBneighbourhood, uBsimMap);
		Double ibPrediction = iBpredictor.getPrediction(itemId, userId, reader.getItemProfiles(), reader.getUserProfiles(), iBneighbourhood, iBsimMap);
		
		if(ubPrediction != null && ibPrediction !=null)
		{
			return (ubPrediction + ibPrediction)/2;
			
		}else if(ubPrediction != null)
		{
			return ubPrediction;
			
		}else if(ibPrediction != null)
		{
			return ibPrediction;
		}else
		{
			return null;
		}
		
	}

}
