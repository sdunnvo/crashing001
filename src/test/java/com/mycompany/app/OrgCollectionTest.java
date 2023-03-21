package com.mycompany.app;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Arrays;


public class OrgCollectionTest {

    OrgRecord orgA = new OrgRecord(1, 0, "Foo");
    OrgRecord orgB = new OrgRecord(2, 1, "Bar");
    OrgRecord orgD = new OrgRecord(4, 3, "Baz");
    OrgRecord orgE = new OrgRecord(5, 2, "Qux");
    OrgRecord orgC = new OrgRecord(3, 0, "Xyzzy");

    List<OrgRecord> someOrgList = Arrays.asList(orgA, orgC);


    public OrgCollectionTest () {

        UserRecord usrA = new UserRecord(1,3,350);
        UserRecord usrB = new UserRecord(23,3,1001);
        UserRecord usrC = new UserRecord(27,1,931);
        UserRecord usrD = new UserRecord(30,5,87);
        UserRecord usrE = new UserRecord(39,4,76);
        UserRecord usrF = new UserRecord(45,5,567);
        UserRecord usrG = new UserRecord(46,1,115);
        UserRecord usrH = new UserRecord(51,2,12);

        orgA.addUserRecord(usrG);
        orgA.addUserRecord(usrA);
        orgB.addUserRecord(usrH);
        orgC.addUserRecord(usrB);
        orgC.addUserRecord(usrC);
        orgD.addUserRecord(usrE);
        orgE.addUserRecord(usrF);
        orgE.addUserRecord(usrD);

        orgA.addChildOrg(orgB);
        orgC.addChildOrg(orgD);
        orgB.addChildOrg(orgE);

        orgA.addChildOrg(new OrgRecord(52, 0, "Tweedle"));
        orgD.addChildOrg(new OrgRecord(59, 0, "CrashPlan"));
        orgB.addChildOrg(new OrgRecord(53, 0, "Meta"));
        orgC.addChildOrg(new OrgRecord(54, 0, "Jansport"));

    }

    @Test
    public void returnNull_WhenOrgIdNotFound() {
        OrgCollection underTest = new OrgCollection(someOrgList);
        OrgRecord testGet = underTest.getOrg(5000);  // 5000 does not exist?

        // assertTrue( testGet == null );
        assertNull("Org result is NULL", testGet);
    }

    @Test
    public void returnCorrectOrgRecord() {
        OrgCollection underTest = new OrgCollection(someOrgList);
        OrgRecord valid = underTest.getOrg(3);

        assertNotNull("Looks very un-NULL", valid);
        assertEquals("Xyzzy", valid.getName(), orgC.getName());
    }

    @Test
    public void assertThereAreNoChildOrgs() {
        OrgCollection underTest = new OrgCollection(someOrgList);
        List<OrgRecord> childlessList = underTest.getOrg(59).getChildOrgs();

        assertEquals( 0, childlessList.size() );
    }

    @Test
    public void assertTheOrgTreeIsTraversedBeyondRoot() {
        OrgCollection underTest = new OrgCollection(someOrgList);
        OrgRecord crashing = underTest.getOrg(59);
        crashing.addChildOrg(new OrgRecord(5001,0,"DeploymentPlan"));
        OrgRecord deploying = underTest.getOrg(5001);

        assertEquals( "DeploymentPlan", 
                        deploying.getName(),
                        crashing.getChildOrgs().get(0).getName());
    }

    @Test
    public void verifyNewOrgParentIdOverwritten_WhenAdded() {
        // whoops! this belogs in `OrgRecordTest` class
        OrgCollection underTest = new OrgCollection(someOrgList);
        OrgRecord crashing = underTest.getOrg(59);
        int oldParentId = 3;
        OrgRecord parentIdOverwrite = new OrgRecord(5005, oldParentId, "Has_Parent_Already");
        crashing.addChildOrg(parentIdOverwrite);

        assertTrue( oldParentId != parentIdOverwrite.getParentOrgId() );
        assertTrue( crashing.getOrgId() == parentIdOverwrite.getParentOrgId() );
    }

    @Ignore
    public void ignoreMe() {
        // would like to use some Spy's too
        assertFalse("ignore me please.", true);
    }
    
}
