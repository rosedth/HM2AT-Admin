package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static void copyFileNIO(String from, String to) throws IOException {

        Path fromFile = Paths.get(from);
        Path toFile = Paths.get(to);

        // if fromFile doesn't exist, Files.copy throws NoSuchFileException
        if (Files.notExists(fromFile)) {
            System.out.println("File doesn't exist? " + fromFile);
            return;
        }

        // if toFile folder doesn't exist, Files.copy throws NoSuchFileException
        // if toFile parent folder doesn't exist, create it.
        Path parent = toFile.getParent();
        if(parent!=null){
            if(Files.notExists(parent)){
                Files.createDirectories(parent);
            }
        }

        // default - if toFile exist, throws FileAlreadyExistsException
        //Files.copy(fromFile, toFile);

        // if toFile exist, replace it.
        Files.copy(fromFile, toFile, StandardCopyOption.REPLACE_EXISTING);

        // multiple StandardCopyOption
        /*CopyOption[] options = { StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES,
                LinkOption.NOFOLLOW_LINKS };

        Files.copy(fromFile, toFile, options);*/

    }
}
