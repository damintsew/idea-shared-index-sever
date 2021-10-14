package ru.damintsew.webserver.service;

import java.util.Collection;

import com.intellij.indexing.shared.cdn.CdnEntry;
import com.intellij.indexing.shared.cdn.CdnIndexJsonItemInfo;
import com.intellij.indexing.shared.cdn.CdnSharedIndexLocalInfo;
import com.intellij.indexing.shared.cdn.CdnSnapshotLocal;
import com.intellij.indexing.shared.cdn.CdnUploadContext;
import com.intellij.indexing.shared.cdn.SharedIndexesXZCompressorSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocalCdnUploadContext implements CdnUploadContext {

    private final String baseUrl;

    public LocalCdnUploadContext(String baseUrl) {
        this.baseUrl = trimEnd(baseUrl);
    }

    @NotNull
    @Override
    public SharedIndexesXZCompressorSettings getXzCompressorSettings() {
        return DefaultImpls.getXzCompressorSettings(this);
    }

    @NotNull
    @Override
    public String downloadUrl(@NotNull CdnEntry cdnEntry) {
        return baseUrl + "/" + trimStart(cdnEntry.getKey());
    }

    @NotNull
    @Override
    public <E, R> Collection<R> loadContent(@NotNull Iterable<? extends E> entries,
                                            @NotNull kotlin.jvm.functions.Function1<? super E, ? extends CdnEntry> entry,
                                            @NotNull kotlin.jvm.functions.Function2<? super E, ? super byte[], ? extends R> processor) {
        return CdnSnapshotLocal.INSTANCE.loadLocalDiskContent(entries, entry, processor);
    }

    @Nullable
    @Override
    public CdnIndexJsonItemInfo processMetadata(@NotNull CdnIndexJsonItemInfo cdnIndexJsonItemInfo) {
        return DefaultImpls.processMetadata(this, cdnIndexJsonItemInfo);
    }

    @Nullable
    @Override
    public CdnSharedIndexLocalInfo processMetadata(@NotNull CdnSharedIndexLocalInfo cdnSharedIndexLocalInfo) {
        return DefaultImpls.processMetadata(this, cdnSharedIndexLocalInfo);
    }

    private String trimStart(String key) {
        if (key.startsWith("/")) {
            return key.substring(1);
        }
        return key;
    }

    private String trimEnd(String key) {
        if (key.endsWith("/")) {
            return key.substring(0, key.length() - 1);
        }
        return key;
    }
}
