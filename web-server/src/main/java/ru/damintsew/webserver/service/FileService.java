package ru.damintsew.webserver.service;

import java.io.File;
import java.io.OutputStream;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import ru.damintsew.webserver.dto.UploadIndexRequest;

public interface FileService {

    void uploadNewIndex(@RequestBody UploadIndexRequest indexRequest,
                        MultipartFile archiverFiles);

    File getFileByPath(String path);
}
