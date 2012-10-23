package org.jboss.rhq.jon.mig.actions.impl.impex;


import org.jboss.rhq.jon.mig.BaseRemote;
import org.jboss.rhq.jon.mig.LoginConfiguration;
import org.jboss.rhq.jon.mig.actions.JONAction;
import org.rhq.core.domain.resource.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: imckinle
 * Date: 7/22/11
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicResourceImport extends AbstractResourceImporter {

    public BasicResourceImport() {
        super();
        registerPropertyOverride(new PropertyValueOverrideLogPath("logFilePath"));
    }
    
    public BasicResourceImport(BaseRemote remote,LoginConfiguration login) {
        super(login,remote);
        registerPropertyOverride(new PropertyValueOverrideLogPath("logFilePath"));
    }
    
    @Override
    public List<Resource> filterResourceToUpdate(List<Resource> resourceToUpdate) {
        return resourceToUpdate;
    }

    /**
     * todo
     * we need to define a super class called get resources.
     * we can then override this and filter based on the resource;s parent platform.
     * and return a cleaned list of resources that will be updated
     * <p/>
     * <p/>
     * also. we need to add to the list of property pre processors. those things than change the value before its commited
     *
     * @param args
     */

    public static void main(String[] args) {
        JONAction rs = new BasicResourceImport();
        Map<String, String> map = new HashMap<String, String>();
        map.put("fileName", "json/rhqAgent_new_EXPORT.json");
        map.put("fileName", "json/postgres2__EXPORT.json");
        map.put("fileName", "json/myJbossResource_EXPORT.json");
  //      map.put("fileName", "json/mySweetPostgres_EXPORT.json");
   //     map.put("fileName", "json/Sevicebinding_EXPORT.json");
        rs.doAction(map);
    }

}
