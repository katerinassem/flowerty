package test.by.itechart.flowerty.persistence.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.by.itechart.flowerty.config.aware.JpaConfigurationAware;
import by.itechart.flowerty.persistence.model.OrderAltering;
import by.itechart.flowerty.persistence.repository.OrderAlteringRepository;

/**
 * Created by Rostislav on 13-May-15
 */

public class OrderAlteringRepositoryTest extends JpaConfigurationAware {

    @Autowired
    private OrderAlteringRepository orderAlteringRepository;

    @Ignore
    @Test
    public void findOne() {

        OrderAltering actual = orderAlteringRepository.findOne(1L);

        Assert.assertNotNull(actual);
        assertThat(actual.getId(), equalTo(1L));
    }
}
