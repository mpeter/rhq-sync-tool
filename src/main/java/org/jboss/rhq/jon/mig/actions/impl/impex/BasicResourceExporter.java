package org.jboss.rhq.jon.mig.actions.impl.impex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.rhq.jon.mig.BaseRemote;
import org.jboss.rhq.jon.mig.LoginConfiguration;
import org.jboss.rhq.jon.mig.actions.JONAction;
import org.jboss.rhq.jon.mig.query.ResourceQuery;
import org.jboss.rhq.jon.mig.query.ResourceQueryImpl;
import org.rhq.core.domain.resource.Resource;


/**
 * Created by IntelliJ IDEA.
 * User: imckinle
 * Date: 7/14/11
 * Time: 1:45 AM
 * define basic functionailty to export a list of resources inventory
 * subtype one need to define a method that gives a list of resources
 * This class can basically import and export anything.
 * There is one captcha. It also tries to import configurations that may not be update-able;
 * JVM login type(Object name is one of these)
 * <p/>
 * Also, Should follow the dot-notation to expression a tree hiereachy  or the object grapgh
 */
public class BasicResourceExporter extends AbstractResourceInventoryExporter {
	
	public static final String BASIC_RESOURCE_EXPORT_OUTPUT_FILENAME = "basic.resource.export.filename";
	
	private  String resourceName;
	
    public BasicResourceExporter(){
    	resourceName = null;
    }
	
    public BasicResourceExporter(LoginConfiguration loginConfiguration,
			BaseRemote baseRemote, String resourceName, String filename) {
    	super(loginConfiguration, baseRemote, filename);    	
    	this.resourceName = resourceName;    	
	}


    @Override
    protected List<Resource> findResourcesToExport(Map<String, String> values) {
        String resourceName = (String) values.get("resourceName");
        if (resourceName == null && this.resourceName == null ) {        	
            throw new IllegalArgumentException("no resourcename defined as a parameter. cannot perform export");
        } else
        	resourceName = this.resourceName;

        ResourceQuery query = new ResourceQueryImpl();

        //    return query.getResourceByName(resourceName);
        //todo this querycan fetch with a graph. need to added check forthe following String "=>"

        List<Resource> t = new ArrayList<Resource>();
        t.add(query.getResourceByParents(resourceName));
        return t;
    }
	
    //todo IVAN.. LOOK NEXT THING. TRY EXPORT A LOGEVENT SOURCE FORM A JBOSS RESOURCE. IT DOESN'T GET EXPORTED CORRECTLY. THIS PROBABLY HAS
	// SOMEHTING TO DO WITH THE NEW SERIALIZER I WROTE. CHECK THIS NEXT TIME U RESTART YOUR IDE
    public static void main(String[] args) {
        JONAction t = new BasicResourceExporter();
        Map<String, String> map = new HashMap<String, String>();
        map.put("resourceName", "imckinle.csb-embedded > postgres");
        map.put("resourceName", "imckinle.csb-embedded > localhost.localdomain:1099 web >");
        map.put("resourceName", "\n" +
                "imckinle.csb-embedded > localhost.localdomain:1099 web > Service Binding Manager");
        //todo, make sure the full path here is exported to the file.
        map.put(BASIC_RESOURCE_EXPORT_OUTPUT_FILENAME, "Sevicebinding");
        t.doAction(map);
    }
}
