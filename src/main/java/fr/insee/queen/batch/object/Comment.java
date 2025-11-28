package fr.insee.queen.batch.object;

import java.util.UUID;

import org.json.simple.JSONObject;

public class Comment {
	
	/**
	 * The id of the comment
	 */
	private UUID id;
	
	/**
	 * Value of the comment
	 */
	private JSONObject value;
	
	/**
	 * Interrogation associated to the comment
	 */
	private Interrogation interrogation;
	
	/**
	 * All args constructor
	 * @param id
	 * @param value
	 * @param interrogation
	 */
	public Comment(UUID id, JSONObject value, Interrogation interrogation) {
		super();
		this.id = id;
		this.value = value;
		this.interrogation = interrogation;
	}
	
	/**
	 * COnstructor with id only
	 * @param randomUUID
	 */
	public Comment(UUID randomUUID) {
		// TODO Auto-generated constructor stub
		this.id = randomUUID;
	}

	/**
	 * Default constructor
	 */
	public Comment() {
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
	 * @param interrogation the INTERRO to set
	 */
	public void setInterrogation(Interrogation interrogation) {
		this.interrogation = interrogation;
	}
}
