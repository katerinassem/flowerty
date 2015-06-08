package by.itechart.flowerty.persistence.repository;

import by.itechart.flowerty.persistence.model.Contact;
import by.itechart.flowerty.persistence.model.QContact;
import by.itechart.flowerty.persistence.model.QUser;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Maria Date: 17.04.15
 */
public class ContactRepositoryImpl extends QueryDslRepositorySupport implements ContactRepositoryCustom {
    private static final QContact CONTACT = QContact.contact;
    private static final QUser USER = QUser.user;
    
    public ContactRepositoryImpl(){
	super(Contact.class);
    }
    
    @Override
    @Transactional
    public int deleteIdIsIn(List<Long> list) {
        return (int) delete(CONTACT).where(CONTACT.id.in(list)).execute();
    }

    @Override
    public List<Long> findContactsWithoutUser() {
	// id of contacts that has user
	List<Long> ids = from(USER).where(USER.contact.isNotNull()).list(USER.contact.id);

	// id of contacts that hasn't user
	List<Long> result = from(CONTACT).where(CONTACT.id.notIn(ids)).list(CONTACT.id);

	return result;
    }
}
