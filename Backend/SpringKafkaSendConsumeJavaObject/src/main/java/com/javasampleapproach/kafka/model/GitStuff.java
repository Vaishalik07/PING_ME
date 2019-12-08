package com.javasampleapproach.kafka.model;

public class GitStuff {
    String id;
    String repoName;
    String user;
    String action;
    String commitId;

    public GitStuff(String id, String repoName, String user, String action, String commitId)
    {
        this.id = id;
        this.repoName = repoName;
        this.user = user;
        this.action = action;
        this.commitId = commitId;
    }

    public GitStuff() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String toString(){
        String info = String.format("{ 'id': %s, 'RepoName': %s, 'User':%s, 'Action':%s, 'Commit':%s}", id, repoName,user,action, commitId );
        return info;
    }
}
