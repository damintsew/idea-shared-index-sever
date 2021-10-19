package ru.damintsew.webserver.service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

import com.intellij.indexing.shared.cdn.CdnSnapshotLocal;
import com.intellij.indexing.shared.cdn.CdnUpload;
import com.intellij.indexing.shared.cdn.CdnUploadKt;
import kotlin.collections.CollectionsKt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IndexerService {

    private final static Logger log = LoggerFactory.getLogger(IndexerService.class);

    @Value("${base-url}")
    private String baseUrl;

    @Value("${base-path}")
    private String basePath;

    @PostConstruct
    public void init() {
        reindexLocalFiles();
    }

    public void reindexLocalFiles() {
        log.info("Start processing indexing base-dir={} base-url={}", basePath, baseUrl);

        LocalCdnUploadContext cdnUploadContext = new LocalCdnUploadContext(baseUrl);

        CdnUpload masterPlan = CdnUploadKt.rebuildCdnLayout(cdnUploadContext, CollectionsKt.emptyList(),
                CdnSnapshotLocal.INSTANCE.localCdnLocalEntries(Path.of(basePath)));
        CdnSnapshotLocal.INSTANCE.wipeFiles(Path.of(basePath), masterPlan.getRemoveEntries());
        CdnSnapshotLocal.INSTANCE.addFiles(Path.of(basePath), masterPlan.getNewEntries());
    }
}
