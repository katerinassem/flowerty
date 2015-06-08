package test.by.itechart.flowerty.persistence.repository;

import by.itechart.flowerty.persistence.model.Order;
import by.itechart.flowerty.persistence.repository.OrderRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.by.itechart.flowerty.config.aware.JpaConfigurationAware;
import test.by.itechart.flowerty.persistence.repository.helper.OrderRepositoryHelperTest;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Rostislav on 16-May-15
 */

public class OrderRepositoryTest extends JpaConfigurationAware {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findOne_ShouldReturnAOrder() {

        Order expected = OrderRepositoryHelperTest.getOrderWithIdOne();

        Order actual = orderRepository.findOne(1L);

        assertNotNull(actual);
        assertThat(actual, allOf(
                hasProperty("id", is(expected.getId())),
//                hasProperty("state", is(expected.getState())),
                hasProperty("cost", is(expected.getCost())),
                hasProperty("customer", is(expected.getCustomer())),
                hasProperty("receiver", is(expected.getReceiver())),
                hasProperty("description", is("15 Red roses bouquet"))
        ));
        assertThat(actual.getStaff(), allOf(
                hasProperty("id", is(expected.getStaff().getId())),
                hasProperty("login", is(expected.getStaff().getLogin())),
                hasProperty("password", is(expected.getStaff().getPassword()))
        ));
        assertThat(actual.getManager(), allOf(
                hasProperty("id", is(expected.getManager().getId())),
                hasProperty("login", is(expected.getManager().getLogin())),
                hasProperty("password", is(expected.getManager().getPassword()))
        ));
        assertThat(actual.getDelivery(), allOf(
                hasProperty("id", is(expected.getDelivery().getId())),
                hasProperty("login", is(expected.getDelivery().getLogin())),
                hasProperty("password", is(expected.getDelivery().getPassword()))
        ));
    }
}
