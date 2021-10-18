package ru.damintsew.webserver.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.damintsew.webserver.dto.UploadIndexRequest;
import ru.damintsew.webserver.service.FileService;

@RestController
@RequestMapping("/manage")
public class SharedIndexController {

    private final FileService fileService;

    public SharedIndexController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void getFile(@RequestParam String commitId,
                        @RequestParam String projectId,
                        @RequestParam("file") MultipartFile file) {

        fileService.uploadNewIndex(new UploadIndexRequest(commitId, projectId), file);
    }
}
