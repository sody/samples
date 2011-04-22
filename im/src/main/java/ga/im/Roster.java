package ga.im;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public interface Roster<C extends Contact> {

	Collection<C> getContacts();

}
