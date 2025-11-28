package fr.insee.queen.batch.object;

import java.util.UUID;

import org.json.simple.JSONArray;

public class Personalization {

	private UUID id;
	
	private JSONArray value;
	
	private Interrogation surveyUnit;
	
	public Personalization() {
		
	}
	
	public Personalization(UUID id, JSONArray value, Interrogation surveyUnit) {
		super();
		this.id = id;
		this.value = value;
		this.surveyUnit = surveyUnit;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the value
	 */
	public JSONArray getValue() {
		return value;
	}

	/**
	 * @return the surveyUnit
	 */
	public Interrogation getInterrogation() {
		return surveyUnit;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(JSONArray value) {
		this.value = value;
	}

	/**
	 * @param surveyUnit the surveyUnit to set
	 */
	public void setInterrogation(Interrogation surveyUnit) {
		this.surveyUnit = surveyUnit;
	}
}
