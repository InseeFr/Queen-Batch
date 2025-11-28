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

import fr.insee.queen.batch.dao.InterrogationDao;
import fr.insee.queen.batch.object.Interrogation;

/**
 * Service for the Interrogation entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
public class InterrogationDaoJpaImpl implements InterrogationDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	/**
	 * Get all SU with state not null for a campaign
	 * @param campaignId
	 * @return
	 */
	@Override
	public List<Interrogation> findInterrogations(String campaignId, List<String> states) {
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

		return jdbcTemplate.query(sql, params, new InterrogationMapper());
	}

	/**
	 * Implements the mapping between the result of the query and the QuestionnaireModel entity
	 * @return QuestionnaireModelMapper
	 */
	private static final class InterrogationMapper implements RowMapper<Interrogation> {
		public Interrogation mapRow(ResultSet rs, int rowNum) throws SQLException         {
			Interrogation interro = new Interrogation();
			interro.setId(rs.getString("id"));
			QuestionnaireModel model = new QuestionnaireModel();
			model.setId(rs.getString("questionnaire_model_id"));
			interro.setQuestionnaireModel(model);
			interro.setSurveyUnitId(rs.getString("survey_unit_id"));
			return interro;
		}
	}
}

