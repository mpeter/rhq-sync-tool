/**
 * 
 */
package org.jboss.rhq.sync.tool.query;

import java.util.Set;

import org.rhq.core.domain.resource.group.GroupDefinition;

/**
 * @author Romain PELISSE - <belaran@redhat.com>
 *
 */
public interface GroupsQuery {

	public Set<GroupDefinition> getallGroups();
}