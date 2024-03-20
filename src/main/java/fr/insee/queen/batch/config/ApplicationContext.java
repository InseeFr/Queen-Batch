package fr.insee.queen.batch.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
@ComponentScan("fr.insee.queen.batch.*")
@PropertySource(value = {"classpath:/queen-bo.properties", "file:${properties.path}/queen-bo.properties"}, ignoreResourceNotFound = true)
public class ApplicationContext {
		
	/**
	 * These values are read in the property file
	 */
	
	@Value("${fr.insee.queen.persistence.database.host}")
	String dbHost;

	@Value("${fr.insee.queen.persistence.database.port}")
	String dbPort;

	@Value("${fr.insee.queen.persistence.database.schema}")
	String dbSchema;

	@Value("${fr.insee.queen.persistence.database.user}")
	private String dbUser;

	@Value("${fr.insee.queen.persistence.database.password}")
	private String dbPassword;

	@Value("${fr.insee.queen.persistence.database.driver}")
	private String dbDriver;
	
	@Value("${fr.insee.queen.folder.in}")
	private String FOLDER_IN;
	
	@Value("${fr.insee.queen.folder.out}")
	private String FOLDER_OUT;
	
	@Value("${fr.insee.queen.key.paradata.id}")
	private String keyParadataIdSu;
	
	@Value("${fr.insee.queen.key.paradata.events}")
	private String keyParadataEvents;

	private String campaignName = "";
	
	private String filename = "";
	
	/***
	 * This method create a new Datasource object
	 * @return new Datasource
	 */
	@Bean("dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbDriver);
		dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbSchema));
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}
	
	/***
	 * This method return datasource connection
	 * @param dataSource
	 * @return Connection
	 * @throws SQLException 
	 */
	@Bean("connection")
	public Connection connection(@Autowired @Qualifier("dataSource") DataSource dataSource) throws SQLException {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	/***
	 * Create a new JdbcTemplate with a datasource passed in parameter
	 * @param dataSource
	 * @return JdbcTemplate
	 */
	@Bean("jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Autowired @Qualifier("dataSource") DataSource dataSource) {
		JdbcTemplate jdbcTemplate = null;
		try {
			jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(connection(dataSource), false));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}

	/**
	 * Bean to get the Folder_in value
	 * @return
	 */
	@Bean
	public String getFolderIn() {
		return FOLDER_IN;
	}
	
	/**
	 * Bean to get the Folder_out value
	 * @return
	 */
	@Bean
	public String getFolderOut() {
		return FOLDER_OUT;
	}
	
	/**
	 * Bean to get the idSu key for paradata
	 * @return
	 */
	@Bean
	public String getKeyParadataIdSu() {
		return keyParadataIdSu;
	}

	/**
	 * Bean to get the events key for paradata
	 * @return
	 */
	@Bean
	public String getKeyParadataEvents() {
		return keyParadataEvents;
	}
	
	/**
	 * Bean to get the campaign name
	 * @return
	 */
	@Bean
	public String getCampaignName() {
		return campaignName;
	}
	
	/**
	 * Bean to get the filename
	 * @return
	 */
	@Bean
	public String getFilename() {
		return filename;
	}
	
}
