package fr.insee.queen.batch.dao;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Interface for the Data entity
 * @author scorcaud
 *
 */
public interface DataDao {
    /**
     * Get the data by a SurveyUnit id
     * @param suId
     * @return
     * @throws ParseException
     */
	JSONObject getDataBySurveyUnitId(String suId) throws ParseException;
}
