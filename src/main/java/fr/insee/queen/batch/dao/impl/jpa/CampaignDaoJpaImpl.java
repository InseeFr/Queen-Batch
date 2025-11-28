package fr.insee.queen.batch.dao.impl.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import fr.insee.queen.batch.dao.CampaignDao;
import fr.insee.queen.batch.object.Campaign;

/**
 * Service for the Campaign entity that implements the interface associated
 * @author scorcaud
 *
 */
@Service
public class CampaignDaoJpaImpl implements CampaignDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;

    /**
     * Get campaigns by ids
     */
	@Override
	public List<Campaign> findByCampaignIds(List<String> campaignIds) {
		if (campaignIds == null || campaignIds.isEmpty()) {
			return List.of();
		}

		String placeholders = String.join(",", campaignIds.stream().map(id -> "?").toArray(String[]::new));
		String sql = "SELECT id, label FROM campaign WHERE id IN (" + placeholders + ")";

		return jdbcTemplate.query(
				sql,
				campaignIds.toArray(),
				(rs, rowNum) -> new Campaign(
						rs.getString("id"),
						rs.getString("label")
				)
		);
	}
}
