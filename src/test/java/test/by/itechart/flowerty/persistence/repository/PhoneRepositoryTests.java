package test.by.itechart.flowerty.persistence.repository;


import by.itechart.flowerty.persistence.model.Phone;
import by.itechart.flowerty.persistence.repository.PhoneRepository;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.by.itechart.flowerty.config.aware.JpaConfigurationAware;

/**
 * @author Мария 31.03.15
 */
@Ignore
public class PhoneRepositoryTests extends JpaConfigurationAware {
    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    @Ignore
    public void savePhone_ValidPhones_ReturnsPageOfPhones() {
	Phone phone = new Phone();
	phone.setNumber("1232121");
	phone.setCountry("375");
	phone.setComment("PhoneComment");
	phoneRepository.save(phone);
    }

    @Test
    public void findPhone_ValidId_ReturnsPhone() {
	Phone phone = phoneRepository.findOne(1l);
	Assert.assertEquals(phone.getNumber(), "1232121");
    }

    @Test
    public void findPhone_InvalidId_ReturnsNull() {
	Phone phone = phoneRepository.findOne(1000l);
	Assert.assertNull(phone);
    }
    @Ignore
    @Test
    public void delete(){
        phoneRepository.delete(1L);
    }
}