package fr.insee.queen.batch.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.exception.DataBaseException;

/**
 * Service for database utils
 * @author samco
 *
 */
@Service
public class DatabaseService {
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	Environment env;
	
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	
	@Autowired
	String getKeyParadataIdSu;
	
	@Autowired
	String getKeyParadataEvents;
	
	List<String> missingTable = new ArrayList<>();
	List<String> lstTable = List.of("survey_unit", "comment", "data", "campaign", "questionnaire_model", 
			"nomenclature", "personalization", "state_data");
	
	/**
	 * Check the database tables/collections
	 * @throws DataBaseException
	 * @throws SQLException
	 */
	public void checkDatabaseAccess() throws DataBaseException, SQLException {
		checkDatabaseAccessJpa();
	}

	/**
	 * Check if the tables all exists
	 * @throws DataBaseException
	 * @throws SQLException
	 */
	public void checkDatabaseAccessJpa() throws DataBaseException, SQLException {
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			for (String tableName : lstTable) {
				rs = metaData.getTables(null, null, tableName, null);
				if (!rs.next())
					missingTable.add(tableName);
			}
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			throw new DataBaseException("Error during connection to database");
		} finally {
			if (connection != null)
				connection.close();
		}
		if (!missingTable.isEmpty()) {
			throw new DataBaseException(String.format("Missing tables in database : [%s]", String.join(",", missingTable)));
		}
	}
	
	/**
	 * This method return true if the persistence type is JPA
	 * @return
	 */
	public boolean isJpaDatabase() {
		return true;
	}
	
	/**
	 * Get the key idSu for paradata
	 * @return
	 */
	public String getKeyParadataIdSu() {
		return getKeyParadataIdSu;
	}
	
	/**
	 * Get the key events for paradata
	 * @return
	 */
	public String getKeyParadataEvents() {
		return getKeyParadataEvents;
	}
}
