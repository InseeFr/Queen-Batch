package fr.insee.queen.batch.dao;

import java.sql.SQLException;
import java.util.List;

import fr.insee.queen.batch.object.QuestionnaireModel;

/**
 * Interface for the QuestionnaireModel entity
 * @author scorcaud
 *
 */
public interface QuestionnaireModelDao {
	
	/**
	 * Create an QuestionnaireModel in database
	 * @param questionnaireModel
	 * @throws SQLException
	 */
	void create(QuestionnaireModel questionnaireModel, String campaignId) throws SQLException;

    /**
     * Check if a QuestionnaireModel already exist in database
     * @param id
     * @return boolean
     * @throws Exception
     */
	boolean exist(String id);

	/**
	 * Find a Questionnaire by a campaign id
	 * @param campaignId
	 * @return
	 */
	List<QuestionnaireModel> findByCampaignId(String campaignId);
}
