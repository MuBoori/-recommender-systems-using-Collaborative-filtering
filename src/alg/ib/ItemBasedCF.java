package alg.ib;

import similarity.SimilarityMap;
import similarity.metric.SimilarityMetric;
import util.reader.DatasetReader;
import alg.CFAlgorithm;
import alg.ib.predictor.Predictor;

public class ItemBasedCF implements CFAlgorithm {
	
	private Predictor predictor; // the predictor technique  
	private alg.ib.neighbourhood.Neighbourhood neighbourhood; // the neighbourhood technique
	private DatasetReader reader; // dataset reader
	private SimilarityMap simMap; // similarity map - stores all user-user similarities
	
	/**
	 * constructor - creates a new UserBasedCF object
	 * @param predictor - the predictor technique
	 * @param neighbourhood - the neighbourhood technique
	 * @param metric - the user-user similarity metric
	 * @param reader - dataset reader
	 */
	public ItemBasedCF(final Predictor predictor, final alg.ib.neighbourhood.Neighbourhood neighbourhood, final SimilarityMetric metric, final DatasetReader reader)
	{
		this.predictor = predictor;
		this.neighbourhood = neighbourhood;
		this.reader = reader;
		this.simMap = new SimilarityMap(reader.getItemProfiles(), metric); // compute all item-based similarities
	}

	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the target user ID
	 * @param itemId - the target item ID
	 */
	public Double getPrediction(final Integer userId, final Integer itemId)
	{	
		return predictor.getPrediction(itemId, userId, reader.getItemProfiles(), reader.getUserProfiles(), neighbourhood, simMap);
	}
	
	

}
