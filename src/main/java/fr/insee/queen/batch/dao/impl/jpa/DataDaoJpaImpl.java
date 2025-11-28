package fr.insee.queen.batch.dao.impl.jpa;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.dao.DataDao;

/**
 * Service for the Data entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
public class DataDaoJpaImpl implements DataDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	/**
	 * Get all data for a SurveyUnit
	 */
	@Override
	public JSONObject getDataBySurveyUnitId(String suId) throws ParseException {
		StringBuilder qString= new StringBuilder("SELECT value FROM data WHERE interrogation_id=?");
		PGobject data =  jdbcTemplate.queryForObject(qString.toString(), new Object[]{suId}, PGobject.class);
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(data.getValue());
		
	}
}
