package fr.insee.queen.batch;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import fr.insee.queen.batch.config.ApplicationContext;
import fr.insee.queen.batch.enums.BatchErrorCode;
import fr.insee.queen.batch.enums.BatchOption;
import fr.insee.queen.batch.exception.ArgumentException;
import fr.insee.queen.batch.exception.FolderException;
import fr.insee.queen.batch.service.ExtractionService;
import fr.insee.queen.batch.utils.PathUtils;
import fr.insee.queen.batch.utils.XmlUtils;

/**
 * Launcher : Queen Batch main class. Used for extraction
 * 
 * @author Claudel Benjamin
 * 
 */
public abstract class Launcher {
	/**
	* The folder in use to insert datas 
	*/
	public static String FOLDER_IN;
	/**
	* The folder out use to store logs and file treated 
	*/
	public static String FOLDER_OUT;

	static org.springframework.context.ApplicationContext context;

	static ExtractionService extractionService;
	static XmlUtils xmlUtils;
	
	
	private static final Logger logger = LogManager.getLogger(Launcher.class);
	
	public static void main(String[] args) {
		context = new AnnotationConfigApplicationContext(ApplicationContext.class);
		extractionService = context.getBean(ExtractionService.class);
		xmlUtils = context.getBean(XmlUtils.class);
		FOLDER_IN = context.getBean("folderIn", String.class);
		FOLDER_OUT = context.getBean("folderOut", String.class);

		BatchErrorCode batchErrorCode = BatchErrorCode.OK;
		try{
			initBatch();
			checkFolderTree();
			batchErrorCode = runBatch(args);
			logger.log(Level.INFO, Constants.MSG_RETURN_CODE, batchErrorCode);
		}catch (ArgumentException|FolderException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
			batchErrorCode = BatchErrorCode.KO_TECHNICAL_ERROR;
			logger.log(Level.ERROR, Constants.MSG_RETURN_CODE, batchErrorCode);
		} finally {
			((AbstractApplicationContext) context).close();
			System.exit(batchErrorCode.getCode());
		}
	}

	protected static void initBatch() throws FolderException {
		if (StringUtils.isBlank(FOLDER_IN) || "${fr.insee.queen.folder.in}".equals(FOLDER_IN)) {
			throw new FolderException("property fr.insee.queen.batch.folder.in is not define in properties");
		}
		if (StringUtils.isBlank(FOLDER_OUT) || "${fr.insee.queen.folder.out}".equals(FOLDER_OUT)) {
			throw new FolderException("property fr.insee.queen.batch.folder.out is not define in properties");
		}
		logger.log(Level.INFO, "Folder properties are OK");
	}

	private static void checkFolderTree() throws FolderException {
		// Create the folder tree for "in"
		PathUtils.createFolderTreeIn(FOLDER_IN);
		logger.log(Level.INFO, "Folder tree '{}' is OK", FOLDER_IN);
		// Create the folder tree for "out"
		PathUtils.createFolderTreeOut(FOLDER_OUT);
		logger.log(Level.INFO, "Folder tree '{}' is OK", FOLDER_OUT);
	}

	public static BatchErrorCode runBatch(String[] options) throws ArgumentException {
		if (options.length == 0) {
			throw new ArgumentException("Batch type is empty, you must choose between [EXTRACTDATA] or [EXTRACTDATACOMPLETE]");
		}
		BatchOption batchOption;
		try {	
			batchOption = BatchOption.valueOf(options[0].trim());
		}catch(Exception e) {
			throw new ArgumentException("Wrong batch type, you must choose between [EXTRACTDATA] or [EXTRACTDATACOMPLETE]");
		}
		switch(batchOption) {
			case EXTRACTDATA :
			case EXTRACTDATACOMPLETE :
				return extractionService.extract(batchOption, FOLDER_OUT);
			default :
				throw new ArgumentException("Wrong batch type, you must choose between [EXTRACTDATA] or [EXTRACTDATACOMPLETE]");
		}
	}
}
