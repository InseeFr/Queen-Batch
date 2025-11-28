package fr.insee.queen.batch.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.insee.queen.batch.object.Interrogation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.insee.lunatic.conversion.data.JSONLunaticDataToXML;
import fr.insee.queen.batch.dao.CampaignDao;
import fr.insee.queen.batch.dao.DataDao;
import fr.insee.queen.batch.dao.PersonalizationDao;
import fr.insee.queen.batch.dao.InterrogationDao;
import fr.insee.queen.batch.enums.BatchErrorCode;
import fr.insee.queen.batch.enums.BatchOption;
import fr.insee.queen.batch.exception.BatchException;
import fr.insee.queen.batch.object.Campaign;
import fr.insee.queen.batch.object.Personalization;
import fr.insee.queen.batch.utils.PathUtils;

/**
 * Extraction Service : this service contains all functions used to extract datas
 * 
 * @author Claudel Benjamin
 * 
 */
@Service
public class ExtractionService {

	@Autowired
	CampaignDao campaignDao;
	@Autowired
	InterrogationDao interrogationDao;
	@Autowired
	DataDao dataDao;
	@Autowired
	PersonalizationDao personalizationDao;
	@Autowired(required=false)
	@Qualifier("connection")
	Connection connection;
	
	BatchOption batchOption;

	private static final Logger logger = LogManager.getLogger(ExtractionService.class);

	/**
	 * Main method for extraction
	 * @param batchOption
	 * @param out
	 * @return
	 */
	public BatchErrorCode extract(BatchOption batchOption, List<String> campaignIds, String out) {
		BatchErrorCode batchErrorCode = BatchErrorCode.OK;
		this.batchOption = batchOption;
		List<Campaign> lstCampaign;
		lstCampaign = campaignDao.findByCampaignIds(campaignIds);
		List<String> lstCampaignError = new ArrayList<>();
		List<String> lstCampaignSuccess = new ArrayList<>();
		for (Campaign c : lstCampaign) {
			try {
				extractCampaign(batchOption, c, out);
				lstCampaignSuccess.add(c.getId());
			}catch(Exception e) {
				logger.log(Level.WARN, "Error occured during extraction of campaign {}", c.getId());
				logger.log(Level.WARN, "Error message : {}", e.getMessage());
				lstCampaignError.add(c.getId());
			}
		}
		logger.log(Level.INFO, "Number of campaigns treated : {}", lstCampaign.size());
		logger.log(Level.INFO, "{} campaigns extracted with success : [{}]", lstCampaignSuccess.size(), StringUtils.join(lstCampaignSuccess, ", "));
		logger.log(Level.INFO, "{} campaigns not extracted : [{}]", lstCampaignError.size(), StringUtils.join(lstCampaignError, ", "));
		if(!lstCampaignError.isEmpty()) {
			if(lstCampaignError.size()==lstCampaign.size()) {
				batchErrorCode=BatchErrorCode.KO_TECHNICAL_ERROR;
			} else {
				batchErrorCode=BatchErrorCode.OK_TECHNICAL_WARNING;
			}
		}
		return batchErrorCode;
	}

	/**
	 * Method use to extract a campaign
	 * @param c
	 * @param out
	 * @throws BatchException
	 */
	public void extractCampaign(BatchOption batchOption, Campaign c, String out) throws BatchException  {
		if(this.batchOption == null)
			this.batchOption = batchOption;
		PathUtils.createFolderTreeExtract(this.batchOption, out, c.getId());
		StringBuilder fileName = new StringBuilder(out)
		.append("/extractdata/")
		.append(c.getId());

		List<Interrogation> interrogations = interrogationDao.findInterrogations(c.getId(), this.batchOption.getStates());

		if(this.batchOption.equals(BatchOption.EXTRACTDATAINIT)) {
			fileName.append("/differential/data/data.init.");
		}
		if(this.batchOption.equals(BatchOption.EXTRACTDATACOMPLETED)) {
			fileName.append("/complete/data/data.complete.");
		}
		fileName.append(c.getId())
		.append(".")
		.append(PathUtils.getTimestampForPath())
		.append(".xml");
		Document doc = new Document();
		File file = new File(fileName.toString());
		Element campaign = new Element("Campaign");
		try(FileWriter fileWriter = new FileWriter(file);) {
			campaign
					.addContent(new Element("Id").addContent(c.getId()))
					.addContent(new Element("Label").addContent(c.getLabel()))
					.addContent(getInterrogationsElement(interrogations));
			doc.setRootElement(campaign);
			// Create the XML
			XMLOutputter outter = new XMLOutputter();
			outter.setFormat(Format.getPrettyFormat());
			outter.output(doc, fileWriter);
			logger.log(Level.INFO, "campaign {} extracted succefully in file {}", c.getId(), fileName);
		} catch (Exception e) {
			logger.log(Level.WARN, "Error message : {}", e.getMessage());
			throw new BatchException(String.format("Error during extract campaign %s", c.getId()));
		}
	}

	/**
	 * Construct the <Interrogations> tag
	 * @param lstSu
	 * @return
	 * @throws Exception
	 */
	private Element getInterrogationsElement(List<Interrogation> lstSu) throws Exception {
		Element surveyUnits = new Element("Interrogations");
		for (Interrogation su : lstSu) {
			surveyUnits.addContent(getInterrogationContent(su));
		}
		return surveyUnits;

	}

	/**
	 * Construct the <Interrogation> tag
	 * @return
	 * @throws Exception
	 */
	private Element getInterrogationContent(Interrogation interro) throws Exception {
		return new Element("Interrogation")
					.addContent(new Element("Id").addContent(interro.getId()))
					.addContent(new Element("SurveyUnitId").addContent(interro.getSurveyUnitId()))
					.addContent(new Element("QuestionnaireModelId").addContent(interro.getQuestionnaireModel().getId()))
					.addContent(getDataContent(interro.getId()))
					.addContent(getPersonalizationContent(interro.getId()));
	}

	/**
	 * Construct the <Data> tag
	 * @param interroId
	 * @return
	 * @throws Exception
	 */
	private Element getDataContent(String interroId) throws Exception {
		JSONLunaticDataToXML jsonLunaticDataToXML = new JSONLunaticDataToXML();
		JSONObject dataJson = dataDao.getDataByInterrogationId(interroId);
		// create a temporary file
		Path tempFilePath = Files.createTempFile(null, null);
		File tempFile = tempFilePath.toFile();
		try (FileWriter file = new FileWriter(tempFile)) {
			file.write(dataJson.toJSONString());
		} catch (IOException e) {
			logger.log(Level.ERROR, "Error during extract Data content for Survey-Unit {}", interroId);
		}
		File xmlData = jsonLunaticDataToXML.transform(tempFile);
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(xmlData);

		try {
			Files.delete(tempFilePath);
			Files.delete(xmlData.toPath());
		} catch (IOException e) {
			logger.log(Level.WARN, "temp file has not be deleted: {}", e.getMessage());
		}

		return document.getRootElement().detach();
	}
	
	/**
	 * Construct the <Personalization> tag
	 * @param interroId
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	private List<Element> getPersonalizationContent(String interroId) throws ParseException{
		List<Element> personalizationsElements = new ArrayList<>();
		List<Personalization> personalizations = personalizationDao.findByInterrogationId(interroId);
		for (Personalization personalization : personalizations) {
			JSONArray jsonArray = personalizationDao.getValueById(personalization.getId());
			if(!jsonArray.isEmpty()) {
				Element personalizationElement = new Element("Personalization");
				for(int i= 0; i<jsonArray.size(); i++) {
					Element variableElement = new Element("Variable");
					JSONObject json = new JSONObject((Map) jsonArray.get(i));
					variableElement.addContent(new Element("Name").addContent(json.get("name").toString()));
					variableElement.addContent(new Element("Value").addContent(json.get("value").toString()));
					personalizationElement.addContent(variableElement);
				}
				personalizationsElements.add(personalizationElement);
			}
		}
		return personalizationsElements;
	}
}
