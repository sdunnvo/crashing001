package com.mycompany.app;

public class UserRecord {
    
    private int userId;
    private int orgId;
    private int numFiles;

    public UserRecord(int usrId, int orgId, int numFiles) {
        setUserId(usrId);
        setOrgId(orgId);
        setNumFiles(numFiles);
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getOrgId() {
        return orgId;
    }
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    public int getNumFiles() {
        return numFiles;
    }
    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

}
