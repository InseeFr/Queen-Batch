package fr.insee.queen.batch;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.insee.queen.batch.config.ApplicationContext;
import fr.insee.queen.batch.enums.BatchErrorCode;
import fr.insee.queen.batch.enums.BatchOption;
import fr.insee.queen.batch.service.ExtractionService;
import fr.insee.queen.batch.utils.PathUtils;
@TestMethodOrder(OrderAnnotation.class)
public abstract class TestEndToEnd {
	
	org.springframework.context.ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);
	ExtractionService extractionService = context.getBean(ExtractionService.class);

	@BeforeAll
	public static void copyFiles() {
		File outDir = new File("src/test/resources/out");
		if (!outDir.exists()) {
			outDir.mkdir();
		}
		File sampleOutDir = new File("src/test/resources/out/sample");
		if (!sampleOutDir.exists()) {
			sampleOutDir.mkdir();
		}
		File extractdataOutDir = new File("src/test/resources/out/extractdata");
		if (!extractdataOutDir.exists()) {
			extractdataOutDir.mkdir();
		}
	}
	
	void purgeDirectory(File dir) {
		if(dir.exists()) {
			for (File file: dir.listFiles()) {
		        if (file.exists() && file.isFile()) {
		        	file.delete();
		        }
		    }
		}
	}
	
	@AfterEach
	public void cleanOutFolder() {
		purgeDirectory(new File("src/test/resources/out/sample"));
		purgeDirectory(new File("src/test/resources/out/extractdata"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2020X00/complete/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2020X00/complete/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2022X00/complete/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2022X00/complete/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2023X00/complete/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2023X00/complete/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2024X00/complete/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2024X00/complete/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2020X00/differential/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2020X00/differential/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2022X00/differential/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2022X00/differential/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2023X00/differential/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2023X00/differential/paradata"));
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2024X00/differential/data"));		
		purgeDirectory(new File("src/test/resources/out/extractdata/SIMPSONS2024X00/differential/paradata"));
	}

	/**
	 * Scenario 11 : Extract data 
	 */
	@Test
	@Order(1)
	public void testScenario1() {
		String out = "src/test/resources/out";
		assertEquals(BatchErrorCode.OK, extractionService.extract(BatchOption.EXTRACTDATA, out));
		assertEquals(true, PathUtils.isDirContainsErrorFile(Path.of("src/test/resources/out/extractdata/SIMPSONS2020X00/differential/data"),
				"data", "xml"));
		assertEquals(true, PathUtils.isDirContainsErrorFile(Path.of("src/test/resources/out/extractdata/SIMPSONS2020X00/differential/paradata"),
				"paradata", "json"));
	}
	
	/**
	 * Scenario 2 : Extract data failed
	 */
	@Test
	@Order(2)
	public void testScenario2() {
		String out = "src/test/resources/out";
		assertEquals(BatchErrorCode.KO_TECHNICAL_ERROR, extractionService.extract(BatchOption.EXTRACTDATACOMPLETE, out+"test"));  
	}
	
	/**
	 * Scenario 3 : extract data differential
	 */
	@Test
	@Order(3)
	public void testScenario3() {
		String out = "src/test/resources/out";
		assertEquals(BatchErrorCode.OK, extractionService.extract(BatchOption.EXTRACTDATACOMPLETE, out));
		assertEquals(true, PathUtils.isDirContainsErrorFile(Path.of("src/test/resources/out/extractdata/SIMPSONS2020X00/complete/data"),
				"data", "xml"));
		assertEquals(true, PathUtils.isDirContainsErrorFile(Path.of("src/test/resources/out/extractdata/SIMPSONS2020X00/complete/paradata"),
				"paradata", "json"));
	}
}
