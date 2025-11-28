package fr.insee.queen.batch.dao;

import fr.insee.queen.batch.object.Interrogation;

import java.util.List;

/**
 * Interface for the Interrogation entity
 * @author scorcaud
 *
 */
public interface InterrogationDao {

	/**
	 * Get all SU for a campaign
	 * @param campaignId
	 * @return
	 */
	List<Interrogation> findInterrogations(String campaignId, List<String> states);
}
