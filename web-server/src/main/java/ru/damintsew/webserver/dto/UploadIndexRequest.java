package ru.damintsew.webserver.dto;

//public record UploadIndexRequest(String commitId, String projectId) {
//}

public class UploadIndexRequest {

    String commitId;
    String projectId;

    public UploadIndexRequest() {
    }

    public UploadIndexRequest(String commitId, String projectId) {
        this.commitId = commitId;
        this.projectId = projectId;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
