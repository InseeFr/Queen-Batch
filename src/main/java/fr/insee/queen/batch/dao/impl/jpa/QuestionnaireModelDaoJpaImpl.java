package fr.insee.queen.batch.dao.impl.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.dao.QuestionnaireModelDao;
import fr.insee.queen.batch.object.QuestionnaireModel;

/**
 * Service for the QuestionnaireModel entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
public class QuestionnaireModelDaoJpaImpl implements QuestionnaireModelDao{
	
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * Implements the creation of a QuestionnaireModel in database
	 * @param questionnaireModel
	 * @throws SQLException
	 */
	@Override
	public void create(QuestionnaireModel questionnaireModel, String campaignId) throws SQLException {
		StringBuilder qString = new StringBuilder("INSERT INTO questionnaire_model (id, label, value, campaign_id) VALUES (?, ?, ?, ?)");
		PGobject value = new PGobject();
		value.setType("json");
		value.setValue(new JSONObject().toJSONString());
	    jdbcTemplate.update(qString.toString(), questionnaireModel.getId(), questionnaireModel.getLabel(), value, campaignId);
	}
	
	/**
	 * Implementation to check if a QuestionnaireModel already exist in database 
	 * @param id
	 * @return boolean
	 * @throws SQLException
	 */
	@Override
	public boolean exist(String id) {
		StringBuilder qString = new StringBuilder("SELECT count(*) FROM questionnaire_model WHERE id=?");
		Long nbRes = jdbcTemplate.queryForObject(qString.toString(), new Object[]{id}, Long.class);
		return nbRes>0;	
	}
	
	/**
	 * Retrieve the QuestionnaireModel by the id passed in parameter
	 * @param campaignId
	 * @return QuestionnaireModel object
	 */
	@Override
	public List<QuestionnaireModel> findByCampaignId(String campaignId) {
		StringBuilder qString = new StringBuilder("SELECT * FROM questionnaire_model WHERE campaign_id=?");
		return jdbcTemplate.query(qString.toString(), new Object[]{campaignId}, new QuestionnaireModelMapper());
	}
	
	/**
	 * Implements the mapping between the result of the query and the QuestionnaireModel entity
	 * @return QuestionnaireModelMapper
	 */
	private static final class QuestionnaireModelMapper implements RowMapper<QuestionnaireModel> {
        public QuestionnaireModel mapRow(ResultSet rs, int rowNum) throws SQLException         {
        	QuestionnaireModel qm = new QuestionnaireModel();
        	qm.setId(rs.getString("id"));
        	qm.setLabel(rs.getString("label"));
        	qm.setCampaignId(rs.getString("campaign_id"));
            return qm;
        }
    }
}
