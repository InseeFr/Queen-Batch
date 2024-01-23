package fr.insee.queen.batch.dao.impl.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import fr.insee.queen.batch.dao.mongo.impl.ParadataEventDaoMongoImpl;
import fr.insee.queen.batch.object.ParadataEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.config.ConditonJpa;
import fr.insee.queen.batch.dao.ParadataEventDao;
import fr.insee.queen.batch.service.DatabaseService;

/**
 * Service for the Paradata-Event entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
@Conditional(value= ConditonJpa.class)
public class ParadataEventDaoJpaImpl implements ParadataEventDao{

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	DatabaseService databaseService;
	
	/**
	 * Method used to retreive all the paradata for a SurveyUnit
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject findBySurveyUnitId(String suId) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONArray array = new JSONArray();
		JSONArray arrayIds = new JSONArray();
		StringBuilder qString = new StringBuilder("SELECT * FROM paradata_event WHERE value->>'").append(
				this.databaseService.getKeyParadataIdSu()).append("' =  ? ");
		List<ParadataEvent> listParadatas =  jdbcTemplate.query(qString.toString(),new ParadataMapper(), new Object[]{suId});
		for(ParadataEvent paradata : listParadatas) {
			JSONObject jsonObjectTemp = (JSONObject) parser.parse(paradata.getValue());
			array.add(jsonObjectTemp.get(databaseService.getKeyParadataEvents()));
			JSONObject item = new JSONObject();
			item.put("id",paradata.getId());
			arrayIds.add(item);
		}
		JSONObject jsobObjectClean = new JSONObject();
		jsobObjectClean.put("idSu", suId);
		jsobObjectClean.put("events", array);
		jsobObjectClean.put("ids", arrayIds);
		return jsobObjectClean;
	}

	private static final class ParadataMapper implements RowMapper<ParadataEvent> {
		public ParadataEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
			ParadataEvent infos = new ParadataEvent();
			infos.setId(rs.getString("id"));
			infos.setValue(rs.getString("value"));
			return infos;
		}


	}

	@Override
	public void deleteParadataById(String id) throws SQLException {

		StringBuilder qString = new StringBuilder("DELETE FROM paradata_event WHERE id = ?::uuid");
		jdbcTemplate.update(qString.toString(),new Object[]{id});
	}


	/**
	 * This method create a paradata_event, only use for testing in the creation of the dataset
	 */
	public void createParadata(JSONObject paradata) throws SQLException {
		StringBuilder qString = new StringBuilder("INSERT INTO paradata_event (id, value) VALUES (?, ?)");
		PGobject value = new PGobject();
		UUID id = UUID.randomUUID();
		value.setType("json");
		value.setValue(paradata.toJSONString());
		jdbcTemplate.update(qString.toString(), id, value);
	}

}
