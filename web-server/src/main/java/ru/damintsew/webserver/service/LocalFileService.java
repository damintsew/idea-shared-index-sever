package ru.damintsew.webserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.damintsew.webserver.dto.UploadIndexRequest;

@Component
public class LocalFileService implements FileService {

    @Value("${base-path}")
    private String basePath;
    @Autowired
    private IndexerService indexerService;

    @Override
    public void uploadNewIndex(UploadIndexRequest indexRequest, MultipartFile archiverFiles) {
        Path basePathForFiles = Path.of(basePath, "project", indexRequest.getProjectId(),
                indexRequest.getCommitId());

        try (ZipInputStream zis = new ZipInputStream(archiverFiles.getInputStream())) {
            Files.createDirectories(basePathForFiles);

            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                System.out.println("zipEntry.name = " + zipEntry.getName());
                Path filePath = basePathForFiles.resolve(Path.of(zipEntry.getName()));
                System.out.println("filePath = " + filePath.toString());

                Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            indexerService.reindexLocalFiles(); //todo refactor this method
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFileByPath(String path) {
        try {
            //todo replace to Files.newInputStream()
            return Path.of(basePath, path).toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
