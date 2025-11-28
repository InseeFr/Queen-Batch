package fr.insee.queen.batch.dao.impl.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.insee.queen.batch.object.QuestionnaireModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.dao.SurveyUnitDao;
import fr.insee.queen.batch.object.SurveyUnit;

/**
 * Service for the SurveyUnit entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
public class SurveyUnitDaoJpaImpl implements SurveyUnitDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * Get all SU with state not null for a campaign
	 * @param campaignId
	 * @return
	 */
	@Override
	public List<SurveyUnit> findSurveyUnits(String campaignId, List<String> states) {
		if (states == null || states.isEmpty()) {
			return List.of();
		}

		String placeholders = String.join(",", states.stream().map(s -> "?").toArray(String[]::new));

		String sql = "SELECT su.id, su.questionnaire_model_id, su.survey_unit_id " +
				"FROM interrogation AS su " +
				"INNER JOIN state_data AS stateData ON stateData.interrogation_id = su.id " +
				"WHERE stateData.state IN (" + placeholders + ") " +
				"AND su.campaign_id = ?";

		Object[] params = new Object[states.size() + 1];
		int i = 0;
		for (String state : states) {
			params[i++] = state;
		}
		params[i] = campaignId;

		return jdbcTemplate.query(sql, params, new SurveyUnitMapper());
	}



	/**
	 * Get Survey unit by his id
	 * @param id
	 * @return
	 */
	@Override
	public String findQuestionnaireIdBySurveyUnitId(String id) {
		StringBuilder qString = new StringBuilder("SELECT questionnaire_model_id FROM interrogation WHERE id= ?");
		return jdbcTemplate.queryForObject(qString.toString(), new Object[]{id}, String.class);
	}

	/**
	 * Implements the mapping between the result of the query and the QuestionnaireModel entity
	 * @return QuestionnaireModelMapper
	 */
	private static final class SurveyUnitMapper implements RowMapper<SurveyUnit> {
		public SurveyUnit mapRow(ResultSet rs, int rowNum) throws SQLException         {
			SurveyUnit su = new SurveyUnit();
			su.setId(rs.getString("id"));
			QuestionnaireModel model = new QuestionnaireModel();
			model.setId(rs.getString("questionnaire_model_id"));
			su.setQuestionnaireModel(model);
			su.setSurveyUnitId(rs.getString("survey_unit_id"));
			return su;
		}
	}
}

