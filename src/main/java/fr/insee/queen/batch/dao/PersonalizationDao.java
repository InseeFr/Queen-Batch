package fr.insee.queen.batch.dao;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import fr.insee.queen.batch.object.Personalization;

public interface PersonalizationDao {
	
	/**
	 * Retrieves all the personalization for a Survey Unit
	 * @param surveyUnitId
	 * @return
	 */
	List<Personalization> findByInterrogationId(String surveyUnitId);
	
	/**
	 * Retrieve the value by the id passed in parameter
	 * @param id
	 * @return JSONObject object
	 * @throws ParseException 
	 */
	JSONArray getValueById(UUID id) throws ParseException;
}
