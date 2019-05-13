package com.guiji.botsentence.service.impl;

import com.guiji.botsentence.service.SpeechFileGenerateService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Service
public class SpeechFileGenerateServiceImpl implements SpeechFileGenerateService {

    private static final String SPEECH_FILES_ROOT_DIRECTORY = "/tmp/botsentence";

    private static final String dict_content = ",\n?\n、\n。\n“\n”\n《\n》\n！\n，\n：\n；\n？\n的\n吗\n吧\n呀\n了\n嘛";

    @Override
    public void generateSpeechFile(String processId) {

    }


    public static void main(String[] args) {
        Path rootPath = Paths.get(SPEECH_FILES_ROOT_DIRECTORY);

        Path staticJsonPath = rootPath.resolve("static.json");

        try {
            if(Files.exists(rootPath)){
                FileUtils.deleteDirectory(new File(rootPath.toUri()));
            }
            Files.createDirectory(rootPath);

            Path jiebaDictPath = rootPath.resolve("jieba_dict");
            Files.createDirectory(jiebaDictPath);

            Path dictFile = jiebaDictPath.resolve("dict.txt");
            Files.createFile(dictFile);
            Files.write(dictFile, dict_content.getBytes());

            String world = "hello world";
            Files.write(staticJsonPath, world.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DeleteDirectoryVisitor extends SimpleFileVisitor<Path>{

        @Override
        public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
            Files.deleteIfExists(filePath);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dirPath, IOException exc) throws IOException {
            Files.deleteIfExists(dirPath);
            return FileVisitResult.CONTINUE;
        }
    }

}
