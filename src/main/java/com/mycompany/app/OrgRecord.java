package com.mycompany.app;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class OrgRecord {

    private int orgId;
    private int parentOrgId;
    private String name;
    private List<OrgRecord> children;
    private List<UserRecord> users;

    public OrgRecord(int orgId, int parentOrgId, String name) {
        setOrgId(orgId);
        setParentOrgId(parentOrgId);
        setName(name);

        this.users = new ArrayList<UserRecord>(0);
        this.children = new ArrayList<OrgRecord>(0);
    }

    public int getOrgId() {
        return orgId;
    }
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    public int getParentOrgId() {
        return parentOrgId;
    }
    public void setParentOrgId(int parentOrdId) {
        this.parentOrgId = parentOrdId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTotalNumUsers() {
        return users.size();
    }

    public int getTotalNumFiles() {
        return users.stream().collect(Collectors.summingInt(UserRecord::getNumFiles));
    }

    public List<OrgRecord> getChildOrgs() {

        return children;
    }

    public void addUserRecord(UserRecord userRecord) {
        userRecord.setOrgId(this.orgId);
        this.users.add(userRecord);
    }

    public void addChildOrg(OrgRecord childOrg) {
        childOrg.setParentOrgId(this.orgId);
        this.children.add(childOrg);
        System.out.println("child ADDED:: " + childOrg.name);
    }

}
