package fr.insee.queen.batch.dao;

import fr.insee.queen.batch.object.SurveyUnit;

import java.util.List;

/**
 * Interface for the SurveyUnit entity
 * @author scorcaud
 *
 */
public interface SurveyUnitDao {

	/**
	 * Get all SU for a campaign
	 * @param campaignId
	 * @return
	 */
	List<SurveyUnit> findSurveyUnits(String campaignId, List<String> states);

	/**
	 * Get Survey unit by his id
	 * @param id
	 * @return
	 */
	String findQuestionnaireIdBySurveyUnitId(String id);
}
