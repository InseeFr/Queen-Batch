package fr.insee.queen.batch.service;

import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.queen.batch.dao.CommentDao;
import fr.insee.queen.batch.dao.DataDao;
import fr.insee.queen.batch.dao.PersonalizationDao;
import fr.insee.queen.batch.dao.SurveyUnitDao;
import fr.insee.queen.batch.object.SurveyUnit;

/**
 * Load Service : this service contains all functions used to load datas
 * 
 * @author Claudel Benjamin
 * 
 */
@Service
public class LoadService {

	private static final Logger logger = LogManager.getLogger(LoadService.class);

	@Autowired
	SurveyUnitDao surveyUnitDao;

	@Autowired
	DataDao dataDao;

	@Autowired
	CommentDao commentDao;

	@Autowired
	PersonalizationDao personalizationDao;
	
	public void createOrUpdateSurveyUnit(SurveyUnit surveyUnit) throws SQLException {
		if (!surveyUnitDao.existSurveyUnit(surveyUnit.getId())) {
			logger.log(Level.WARN, "Create Survey Unit {}", surveyUnit.getId());
			// Create Survey Unit
			surveyUnitDao.createSurveyUnit(surveyUnit);
			// Create Data
			dataDao.createData(surveyUnit);
			if(surveyUnit.getPersonalization() != null) {
				// Create Personalization
				personalizationDao.createPersonalization(surveyUnit);
			}
			// Create Empty Comment
			commentDao.createComment(surveyUnit);
		} else {
			//if the SU already exist we update
			logger.log(Level.WARN, "Update Survey Unit {}", surveyUnit.getId());
			surveyUnitDao.updateSurveyUnit(surveyUnit);
			dataDao.updateData(surveyUnit);
			if(surveyUnit.getPersonalization() != null) {
				personalizationDao.updatePersonalization(surveyUnit);
			}
		}
	}
}
