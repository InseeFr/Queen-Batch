package fr.insee.queen.batch.enums;

/**
 * Enum wich defines the differents error messages when a problem occurs
 * @author scorcaud
 *
 */
public enum BatchErrorCode {
	OK(0, "Exécution correcte(sans avertissement)"),
	OK_TECHNICAL_WARNING(201, "Exécution correcte avec des avertissements techniques"),
	KO_TECHNICAL_ERROR(202, "Echec de l'exécution avec des avertissements techniques");
	/**
     * return code
     */
    private int code;
	
	/**
     * label
     */
    private String label;
	
    /**
     * Defaut constructor for a BatchErrorCode
     * @param code
     * @param label
     */
	BatchErrorCode(int code, String label) {
		this.code=code;
		this.label=label;
	}
	
	/**
	 * Get the code for a BatchErrocode
	 * @return code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Get the label for a BatchErrocode
	 * @return label
	 */
	public String getLabel() {
		return label;
	}
}
