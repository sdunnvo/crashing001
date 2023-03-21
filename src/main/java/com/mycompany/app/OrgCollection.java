package com.mycompany.app;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class OrgCollection {

    private List<OrgRecord> orgRecords;
    private Map<Integer, OrgRecord> orgMap;

    public OrgCollection(List<OrgRecord> list_o_records) {
        setOrgRecords(list_o_records);
    }
    
    public List<OrgRecord> getOrgRecords() {
        this.orgRecords = orgMap.values()
            .stream()
            .collect(toList());

        return orgRecords;
    }

    public void setOrgRecords(List<OrgRecord> orgRecords) {
        this.orgRecords = orgRecords;
        this.orgMap = this.orgRecords.stream()
            .collect(Collectors.toMap(OrgRecord::getOrgId, Function.identity()));
    }

    public OrgRecord getOrgRec(int orgId, List<OrgRecord> recList) {

        if (recList == null || recList.size() == 0) {
            return null;
        }

        OrgRecord found = recList.stream()
            .filter(rec -> rec.getOrgId() == orgId)
            .findFirst()
            .orElse(null);
        
        if (found == null) {

            for (int i = 0; i < recList.size(); i++) {
                if (recList.get(i).getChildOrgs() != null && recList.get(i).getChildOrgs().size() > 0) {

                    System.out.println("... recursing with org-name::" + recList.get(i).getName());

                    found = getOrgRec(orgId, recList.get(i).getChildOrgs());
                    if (found == null) {
                        continue;
                    }
                    else {
                        return found;
                    }
                }
                else {
                    System.out.println("Child-List is emptyfor :: " + recList.get(i).getName());
                }
            }

            return null;
        }

        else {
            return found;
        }
    }

    public OrgRecord getOrg(int orgId) {
        if (null == orgMap.get(orgId)) {

            OrgRecord recur = null;
            
            for(int i=0;i<this.orgRecords.size();i++){
                recur = getOrgRec(orgId, orgRecords.get(i).getChildOrgs());
                if (recur != null) break;
            } 

            if (recur == null) System.out.println("ORG-ID not found.");
            else System.out.println("Found orgId {"+orgId+"} --> {"+recur.getName()+"}");
           
            // List<OrgRecord> flattened = this.orgRecords.stream()
            //     .flatMap(orec -> orec.getChildOrgs().stream())
            //     .collect(Collectors.toList());
            // flattened.forEach(or -> System.out.println("fltMAPPin --> " + or.getName()));

            // OrgRecord flatOne = this.orgRecords.stream()
            //     .flatMap(orec -> orec.getChildOrgs().stream())
            //     .filter(child -> child.getOrgId() == orgId)
            //     .findFirst()
            //     .orElse(null);

            return recur;
        }
        else {
            return orgMap.get(orgId);
        }
    }

    public List<OrgRecord> getOrgTree(int nodeOrgId, boolean inclusive) {
        OrgRecord nodeOrg = this.orgMap.get(nodeOrgId);
        if (nodeOrg != null && nodeOrg.getChildOrgs().size() > 0) {
            // see `getOrgRec(int,List<OrgRecord>)`
            return nodeOrg.getChildOrgs(); // \[._.]/
        }
        else {
            return null;
        }
    }

    public String visualized() {
        return "VISUALIZED!";
    }

}
