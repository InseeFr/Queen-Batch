package fr.insee.queen.batch.object;

/**
 * Object XmlInterrogation : represent the survey unit in XML file
 * 
 * @author Claudel Benjamin
 * 
 */
public class Interrogation {

	/**
	 * The id of survey unit
	 */
	private String id;

	private String surveyUnitId;
	
	/**
	 * Campaign associated to the survey-unit
	 */
	private Campaign campaign;
	
	/**
	 * Questionnaire associated to the survey-unit
	 */
	private QuestionnaireModel questionnaireModel;
	
	/**
	 * The JSON comment of survey unit
	 */
	private Comment comment;
	
	/**
	 * The JSON data of survey unit
	 */
	private Data data;
	
	/**
	 * The stateData of the survey-unit
	 */
	private StateData stateData;
	/**
	 * The JSON personalization of survey unit
	 */
	private Personalization personalization;

	/**
	 * Default constructor
	 */
	public Interrogation() {
	}

	/**
	 * @return id of survey unit
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return JSON data of survey unit
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * @return the stateData
	 */
	public StateData getStateData() {
		return stateData;
	}

	/**
	 * @param stateData the stateData to set
	 */
	public void setStateData(StateData stateData) {
		this.stateData = stateData;
	}

	public String getSurveyUnitId() {
		return surveyUnitId;
	}

	public void setSurveyUnitId(String surveyUnitId) {
		this.surveyUnitId = surveyUnitId;
	}

	/**
	 * @return JSON comment of survey unit
	 */
	public Personalization getPersonalization() {
		return personalization;
	}

	/**
	 * @param personalization to set
	 */
	public void setPersonalization(Personalization personalization) {
		this.personalization = personalization;
	}

	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 * @return the questionnaireModel
	 */
	public QuestionnaireModel getQuestionnaireModel() {
		return questionnaireModel;
	}

	/**
	 * @param questionnaireModel the questionnaireModel to set
	 */
	public void setQuestionnaireModel(QuestionnaireModel questionnaireModel) {
		this.questionnaireModel = questionnaireModel;
	}

	/**
	 * @return the campaign
	 */
	public Campaign getCampaign() {
		return campaign;
	}

	/**
	 * @param campaign the campaign to set
	 */
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
}
