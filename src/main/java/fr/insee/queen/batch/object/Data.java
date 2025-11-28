package fr.insee.queen.batch.object;

import java.util.UUID;

import org.json.simple.JSONObject;

public class Data {
	/**
	 * Id of the data
	 */
	private UUID id;
	
	/**
	 * Value of the data
	 */
	private JSONObject value;
	
	/**
	 * Interrogation related to the data
	 */
	private Interrogation interrogation;
	
	public Data(){
		
	}

	public Data(UUID id, JSONObject value, Interrogation interrogation) {
		super();
		this.id = id;
		this.value = value;
		this.interrogation = interrogation;
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
	public JSONObject getValue() {
		return value;
	}

	/**
	 * @return the surveyUnit
	 */
	public Interrogation getInterrogation() {
		return interrogation;
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
	public void setValue(JSONObject value) {
		this.value = value;
	}

	/**
	 * @param interrogation the surveyUnit to set
	 */
	public void setInterrogation(Interrogation interrogation) {
		this.interrogation = interrogation;
	}
}
