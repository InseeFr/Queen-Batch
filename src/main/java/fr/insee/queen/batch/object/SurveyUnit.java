package fr.insee.queen.batch.object;

/**
 * Object XmlSurveyUnit : represent the survey unit in XML file
 * 
 * @author Claudel Benjamin
 * 
 */
public class SurveyUnit {

	/**
	 * The id of survey unit
	 */
	private String id;
	
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
	 * Constructor with all args
	 * @param id
	 * @param campaign
	 * @param questionnaireModel
	 * @param comment
	 * @param data
	 * @param stateData
	 * @param personalization
	 */
	public SurveyUnit(String id, Campaign campaign, QuestionnaireModel questionnaireModel, 
			Comment comment, Data data, StateData stateData, Personalization personalization) {
		this.id = id;
		this.campaign = campaign;
		this.questionnaireModel = questionnaireModel;
		this.data = data;
		this.stateData = stateData;
		this.personalization = personalization;
		this.comment = comment;
	}

	/**
	 * Default constructor
	 */
	public SurveyUnit() {
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

	/**
	 * @return JSON comment of survey unit
	 */
	public Personalization getPersonalization() {
		return personalization;
	}

	/**
	 * @param comment comment to set
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
