package fr.insee.queen.batch.dao;

import java.util.List;

import fr.insee.queen.batch.object.Campaign;

/**
 * Interface for the Campaign entity
 * @author scorcaud
 *
 */
public interface CampaignDao {
	/**
     * Get all Campaign id in database
	 * @return {@link List} of {@link String}
	 */
	List<Campaign> findByCampaignIds(List<String> campaignIds);
}
