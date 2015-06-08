package test.by.itechart.flowerty.persistence.repository;

import by.itechart.flowerty.persistence.model.Contact;
import by.itechart.flowerty.persistence.repository.ContactRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.by.itechart.flowerty.config.aware.JpaConfigurationAware;
import test.by.itechart.flowerty.persistence.repository.helper.ContactRepositoryHelperTest;

import java.util.List;

import static org.springframework.util.Assert.notEmpty;

public class ContactRepositoryTest extends JpaConfigurationAware {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void findContactsWithoutUser(){
	List<Long> contacts = contactRepository.findContactsWithoutUser();
	
	Assert.assertNotNull(contacts);
    }
    
    @Test
    public void findOne_PassValidId_RetursContact() {
        final Contact expected = ContactRepositoryHelperTest.getContactWithIdOne();

        final Contact actual = contactRepository.findOne(1L);

        Assert.assertNotNull(actual);
        
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getSurname(), actual.getSurname());
        Assert.assertEquals(expected.getFathername(), actual.getFathername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getBirthday(), actual.getBirthday());

        Assert.assertEquals(expected.getCompany().getId(), actual.getCompany().getId());
        Assert.assertEquals(expected.getCompany().getName(), actual.getCompany().getName());
        Assert.assertEquals(expected.getCompany().getWebsite(), actual.getCompany().getWebsite());

        Assert.assertEquals(expected.getAddress().getId(), actual.getAddress().getId());
        Assert.assertEquals(expected.getAddress().getCountry(), actual.getAddress().getCountry());
        Assert.assertEquals(expected.getAddress().getFlat(), actual.getAddress().getFlat());
        Assert.assertEquals(expected.getAddress().getHouse(), actual.getAddress().getHouse());
        Assert.assertEquals(expected.getAddress().getStreet(), actual.getAddress().getStreet());
        Assert.assertEquals(expected.getAddress().getTown(), actual.getAddress().getTown());
    }

    @Test
    public void findAll_RetursNotNullCollection() {
        final List<Contact> actual = (List<Contact>) contactRepository.findAll();

        Assert.assertNotNull(actual);
        notEmpty(actual);
    }

    @Test
    public void findBySurnameStartingWithAndCompany_PassValidParameters_ReturnsNotNullCollectionWithTargetContact() {
        final Contact expected = ContactRepositoryHelperTest.getContactWithIdOne();

        final List<Contact> actuals = contactRepository.findBySurnameStartingWithAndCompany(expected.getSurname(), expected.getCompany());

        Assert.assertNotNull(actuals);
        notEmpty(actuals);
        //TODO: add more assert
    }
}
