package fr.insee.queen.batch.enums;

import java.util.List;

public enum BatchOption {
	EXTRACTDATAINIT(List.of("INIT")), EXTRACTDATACOMPLETED(List.of("VALIDATED", "EXTRACTED"));

	private List<String> states;

	/**
	 * Defaut constructor for BatchOption
	 * @param states
	 */
	BatchOption(List<String> states) {
		this.states = states;
	}

	public List<String> getStates() {
		return states;
	}
}
