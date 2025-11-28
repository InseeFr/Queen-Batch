package fr.insee.queen.batch.dao.impl.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.dao.PersonalizationDao;
import fr.insee.queen.batch.object.Personalization;

/**
 * Service for the Personalization entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
public class PersonalizationDaoJpaImpl implements PersonalizationDao{

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * Get the personalization for a Interrogation id
	 */
	@Override
	public List<Personalization> findByInterrogationId(String surveyUnitId) {
		StringBuilder qString = new StringBuilder("SELECT * FROM personalization WHERE interrogation_id= ?");
		return jdbcTemplate.query(qString.toString(), new Object[]{surveyUnitId}, new PersonalizationModelMapper());
	}
	
	/**
	 * Retrieve the QuestionnaireModel by the id passed in parameter
	 * @param id
	 * @return QuestionnaireModel object
	 * @throws ParseException 
	 */
	@Override
	public JSONArray getValueById(UUID id) throws ParseException {
		StringBuilder qString = new StringBuilder("SELECT value FROM personalization WHERE id=?");
		PGobject value =  jdbcTemplate.queryForObject(qString.toString(), new Object[]{id}, PGobject.class);
		if(value != null && (value.getValue().contains("name") || value.getValue().contains("value"))) {
			JSONParser parser = new JSONParser();
			return (JSONArray) parser.parse(value.getValue());
		} else {
			return new JSONArray();
		}
		
	}
	
	/**
	 * Implements the mapping between the result of the query and the QuestionnaireModel entity
	 * @return QuestionnaireModelMapper
	 */
	private static final class PersonalizationModelMapper implements RowMapper<Personalization> {
        public Personalization mapRow(ResultSet rs, int rowNum) throws SQLException         {
        	Personalization personalization = new Personalization();
        	personalization.setId(rs.getObject("id", UUID.class));
            return personalization;
        }
    }
}
