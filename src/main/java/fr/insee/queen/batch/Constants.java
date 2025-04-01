package fr.insee.queen.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

	private static final Logger logger = LoggerFactory.getLogger(Constants.class);

	public static final String MSG_RETURN_CODE = "CODE RETOUR BATCH : {}";
	public static final String MSG_FAILED_MOVE_FILE = "Failed to move the file {}";
	public static final String MSG_FILE_MOVE_SUCCESS = "File {} renamed and moved successfully";

	public static final String TEMP_FOLDER_PATH = System.getProperty("java.io.tmpdir") + File.separator + "queen-batch";
	public static final Path TEMP_FOLDER= getTempDir(TEMP_FOLDER_PATH);

	private Constants() {

	}

	public static Path getTempDir(String pathFolder) {
		logger.info("temp directory is: "+ pathFolder);
		Path tempDirPath = null;
		File dir = new File(pathFolder);

		if(dir.exists()) {
			tempDirPath = Paths.get(pathFolder);
		}
		else {
			try {
				tempDirPath = Files.createDirectory(Paths.get(pathFolder));
			} catch(IOException e) {
				logger.debug("Temp directory fail to initialize");
				e.printStackTrace();
			}
		}
		return tempDirPath;
	}
	
}
