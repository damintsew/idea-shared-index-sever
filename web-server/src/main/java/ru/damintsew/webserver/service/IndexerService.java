package ru.damintsew.webserver.service;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

import com.intellij.indexing.shared.cdn.CdnSnapshotLocal;
import com.intellij.indexing.shared.cdn.CdnUpload;
import com.intellij.indexing.shared.cdn.CdnUploadKt;
import kotlin.collections.CollectionsKt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IndexerService {

    @Value("${base-url}")
    private String baseUrl;

    @Value("${base-path}")
    private String basePath;

    @PostConstruct
    public void init() {
        reindexLocalFiles();
    }

    public void reindexLocalFiles() {
        LocalCdnUploadContext cdnUploadContext = new LocalCdnUploadContext(baseUrl);

        CdnUpload masterPlan = CdnUploadKt.rebuildCdnLayout(cdnUploadContext, CollectionsKt.emptyList(),
                CdnSnapshotLocal.INSTANCE.localCdnLocalEntries(Path.of(basePath)));
        CdnSnapshotLocal.INSTANCE.wipeFiles(Path.of(basePath), masterPlan.getRemoveEntries());
        CdnSnapshotLocal.INSTANCE.addFiles(Path.of(basePath), masterPlan.getNewEntries());
    }
}
