package utils;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;


class FileManagerTest {

	@Test
	void copyFileTest()  throws IOException {
		 Path copied = Paths.get("C:\\Users\\rosed\\Downloads\\testeCopy\\[copied]config.json");
		    Path originalPath = Paths.get("C:\\Users\\rosed\\Downloads\\config.json");
		    FileManager.copyFileNIO(originalPath.toString(), copied.toString());

		     assertTrue(Files.exists(copied));
		    assertLinesMatch(Files.readAllLines(originalPath),(Files.readAllLines(copied)));
	}

}
