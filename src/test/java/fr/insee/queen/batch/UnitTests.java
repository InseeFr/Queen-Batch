package fr.insee.queen.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import fr.insee.queen.batch.enums.BatchErrorCode;
import fr.insee.queen.batch.exception.ArgumentException;
import fr.insee.queen.batch.utils.PathUtils;

class UnitTests {

	public Lanceur launcher = new Lanceur();
	
	/* Run Batch */
	
	@SuppressWarnings("static-access")
	@Test
	void noOptionDefine() {
		String[] options= {};
		Exception exception = assertThrows(ArgumentException.class, () -> {
			BatchErrorCode returnCode = launcher.runBatch(options);
			assertEquals(BatchErrorCode.KO_TECHNICAL_ERROR, returnCode);
	    });
		String expectedMessage = "Batch type is empty, you must choose between [EXTRACTDATA] or [EXTRACTDATACOMPLETE]";
		assertEquals(expectedMessage, exception.getMessage());
	}
	
	@SuppressWarnings("static-access")
	@Test
	void wrongOptionDefine() {
		String[] options= {"LOAD"}; 
		Exception exception = assertThrows(ArgumentException.class, () -> {
			BatchErrorCode returnCode = launcher.runBatch(options);
			assertEquals(BatchErrorCode.KO_TECHNICAL_ERROR, returnCode);
	    });
		String expectedMessage = "Wrong batch type, you must choose between [EXTRACTDATA] or [EXTRACTDATACOMPLETE]";
		assertEquals(expectedMessage, exception.getMessage());
	}
	
	/* Tests for PathUtils.java */

	@Test
	void directoryShouldExist() {
		assertEquals(true, PathUtils.isDirectoryExist("src/test/resources/out"));
	}
	
	@Test
	void directoryShouldntExist() {
		assertEquals(false, PathUtils.isDirectoryExist("src/test/resources/test"));
	}
}
