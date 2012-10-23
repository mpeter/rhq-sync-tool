package org.jboss.rhq.jon.mig.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.rhq.core.domain.auth.Subject;
import org.rhq.core.domain.authz.Role;

/**
 * <p>
 * Regroup handy or utility method related to domain object such as
 * {@link Subject} or {@link Role}.
 * </p>
 * 
 * @author Romain PELISSE - <belaran@redhat.com>
 * 
 */
public final class DomainCollectionsUtils {

	private DomainCollectionsUtils() {
	}

	/**
	 * <p>
	 * *BE CAREFUL* Huge simplification is done here, we assume that 'username'
	 * are UNIQUES.
	 * </p>
	 * 
	 * @param roles
	 * @return
	 */
	public static Map<String, Subject> indexSubjectsFromRolesByName(
			Collection<Role> roles) {
		Map<String /* username */, Subject> rolesSubjects = new HashMap<String, Subject>();
		for (Role role : roles) {
			for (Subject subject : role.getSubjects())
				rolesSubjects.put(subject.getName(), subject);
		}
		return rolesSubjects;
	}

	public static Map<String, Subject> indexSubjectByName(
			Collection<Subject> subjects) {
		Map<String, Subject> subjectIndexedByName = new HashMap<String, Subject>();
		for (Subject subject : subjects) {
			subjectIndexedByName.put(subject.getName(), subject);
		}
		return subjectIndexedByName;
	}

	public static <T> Map<String, T> removeExistingItemsFromProvided(
			Map<String, T> existingItems, Map<String, T> providedItems) {
		Map<String, T> unexistingItems = new HashMap<String, T>(
				providedItems.size());
		for (Entry<String, T> entry : providedItems.entrySet())
			if (!existingItems.containsKey(entry.getKey()))
				unexistingItems.put(entry.getKey(), entry.getValue());
		return unexistingItems;
	}

	public static Map<String,Role> indexRoleByName(Collection<Role> roles) {
		Map<String,Role> indexedRoleByName = new HashMap<String, Role>(roles.size());
		for ( Role role : roles )
			indexedRoleByName.put(role.getName(),role);
		return indexedRoleByName;
	}
	
	public static Set<Subject> extractsSubjectsFromRole(Role role) {
		if ( role.getSubjects() != null && ! role.getSubjects().isEmpty() ) {
			Set<Subject> subjects = role.getSubjects();
			role.setSubjects(new HashSet<Subject>(0));
			return subjects;
		}
		return new HashSet<Subject>(0);
	}
	
	public static Subject findSubject(Subject subject, Map<String, Subject> existingSubject,Map<String, Subject> createdSubjects) {
		final String name = subject.getName();
		subject =  existingSubject.get(name);
		if ( subject == null ) {
			subject = createdSubjects.get(name);
			if ( subject == null )
				throw new IllegalStateException("Subject " + name + " is still not created, and should have been by now.");
		}
		return subject;
	}
}
