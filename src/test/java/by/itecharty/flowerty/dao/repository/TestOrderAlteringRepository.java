package by.itecharty.flowerty.dao.repository;

import by.itechart.flowerty.dao.repository.OrderAlteringRepository;
import by.itechart.flowerty.model.Order;
import by.itechart.flowerty.model.OrderAltering;
import by.itechart.flowerty.model.State;
import by.itechart.flowerty.model.User;
import by.itecharty.flowerty.config.JpaConfigurationAware;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 30.03.15
 * Time: 9:14
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class TestOrderAlteringRepository extends JpaConfigurationAware {
    @Autowired
    private OrderAlteringRepository orderAlteringRepository;

    @Test
    public void saveOrderAltering_ValidOrderAltering_ReturnsSameOrderAltering() {
        OrderAltering orderAltering = new OrderAltering();
        Order order = new Order();
        order.setId(1l);
        orderAltering.setDate(new Date());
        orderAltering.setOrder(order);
        State state = new State();
        state.setId(1l);
        orderAltering.setState(state);
        User user = new User();
        user.setId(1l);
        orderAltering.setUser(user);
        orderAltering.setComment("comment");
        orderAltering = orderAlteringRepository.save(orderAltering);
        Assert.assertEquals(orderAltering.getState(), state);
    }
    @Test
    public void findOne_ValidId_ReturnsOrder() {
        OrderAltering orderAltering = orderAlteringRepository.findOne(1l);
        Assert.assertEquals((Object) orderAltering.getState().getId(), 1l);
        Assert.assertEquals(orderAltering.getUser().getContact().getName(), "Sergey");
        Assert.assertEquals(orderAltering.getComment(), "comment");
    }
    @Test
    public void findOne_InvalidId_ReturnsNull() {
        OrderAltering orderAltering = orderAlteringRepository.findOne(1000l);
        Assert.assertNull(orderAltering);
    }
    @Test
    public void findByOrder_CorrectOrder_ReturnsPageOfOrderAltering() {
        Order order = new Order();
        order.setId(1l);
        Page<OrderAltering> page = orderAlteringRepository.findByOrder(order, new PageRequest(0, 10));
        Assert.assertNotEquals(page.getContent().size(), 0);
        Assert.assertEquals(page.getContent().get(0).getComment(), "comment");
    }
    @Test
    public void findByOrder_InvalidOrder_ReturnsEmptyPage() {
        Order order = new Order();
        order.setId(100l);
        Page<OrderAltering> page = orderAlteringRepository.findByOrder(order, new PageRequest(0, 10));
        Assert.assertEquals(page.getContent().size(), 0);
    }

}