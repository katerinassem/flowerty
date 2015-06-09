package by.itechart.flowerty.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itechart.flowerty.persistence.model.Goods;
import by.itechart.flowerty.persistence.model.Item;
import by.itechart.flowerty.persistence.model.Order;
import by.itechart.flowerty.persistence.model.OrderAltering;
import by.itechart.flowerty.persistence.model.State;
import by.itechart.flowerty.persistence.model.User;
import by.itechart.flowerty.persistence.mongo.model.FinancialReport;
import by.itechart.flowerty.persistence.mongo.repository.FinancialReportRepository;
import by.itechart.flowerty.persistence.repository.GoodsRepository;
import by.itechart.flowerty.persistence.repository.OrderAlteringRepository;
import by.itechart.flowerty.persistence.repository.OrderRepository;
import by.itechart.flowerty.persistence.repository.StateRepository;
import by.itechart.flowerty.persistence.repository.UserRepository;
import by.itechart.flowerty.security.service.UserDetailsServiceImpl;
import by.itechart.flowerty.solr.model.OrderDocument;
import by.itechart.flowerty.solr.repository.OrderDocumentRepository;
import by.itechart.flowerty.web.model.OrderCreateBundle;
import by.itechart.flowerty.web.model.OrderEditBundle;
import by.itechart.flowerty.web.model.OrderHistoryBundle;

/**
 * Created by Катерина on 24.04.2015.
 *
 * Service for manipulating orders
 */

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired(required = true)
    private OrderDocumentRepository orderDocumentRepository;

    @Autowired
    private OrderAlteringRepository orderAlteringRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private FinancialReportRepository financialReportRepository;

    @Transactional
    public Order save(Order orderToCreate){

        //  checking if can checkout
        if(!availableOnWarehouse(orderToCreate.getItems())){
            return orderToCreate;
        }

        if(orderToCreate.getItems() != null){
            List<Item> tempItems = new ArrayList<Item>();
            for(Item item : orderToCreate.getItems()){
                if(!item.getQuantity().equals(0)) {
                    item.setOrder(orderToCreate);
                    tempItems.add(item);
                    goodsRepository.save(item.getGoods());
                }
            }
            orderToCreate.setItems(tempItems);
        }
        Order savedOrder = orderRepository.save(orderToCreate);
        orderDocumentRepository.save(savedOrder.getOrderDocument());

        //  Setting first state history
        OrderAltering newAltering = new OrderAltering(null, savedOrder, userDetailsService.getCurrentUser(),
                getStateByDescription(State.DESCRIPTION_TYPE.NEW), DateTime.now().toDate(), "");
        orderAlteringRepository.save(newAltering);

        financialReportRepository.save(prepareFinancialReport(newAltering));
        
        return savedOrder;
    }
    
    private FinancialReport prepareFinancialReport(OrderAltering order){
	Long companyId = order.getUser().getContact().getCompany().getId();
	Long orderId = order.getOrder().getId();
	Date now = order.getDate();
	Double cost = 0.0;
	for (Item item : order.getOrder().getItems()) {
	    cost += item.getQuantity() * item.getGoods().getCost();
	}
	
	FinancialReport financialReport = new FinancialReport(companyId, orderId, now, cost);
    
	return financialReport;
    }
	
    private boolean availableOnWarehouse(List<Item> items){
        boolean availableOnWarehouse = true;
        for(Item item : items){
            Goods goods = goodsRepository.findOne(item.getGoods().getId());
            if(goods.getRemain() < item.getQuantity()){

                //  if '0' then CANNOT make an order
                item.setQuantity(0);
                availableOnWarehouse = false;
            }
        }
        return availableOnWarehouse;
    }

    private State getStateByDescription(State.DESCRIPTION_TYPE type){
        List<State> states = stateRepository.findByDescription(type);
        return states.size() == 0 ? null : states.get(0);
    }

    @Transactional
    public Order saveChanges(OrderEditBundle orderEditBundle){
        if(orderEditBundle == null){
            return null;
        }
        if(orderEditBundle.getOrderAltering() != null) {
            LOGGER.info("saving order with orderAltering {}", orderEditBundle.getOrderAltering());
        }

        Order savedOrder = orderRepository.save(orderEditBundle.getOrder());
        if(orderEditBundle.getOrderAltering() == null){
            return savedOrder;
        }
        orderEditBundle.getOrderAltering().setOrder(savedOrder);
        orderEditBundle.getOrderAltering().setUser(userDetailsService.getCurrentUser());
        orderEditBundle.getOrderAltering().setDate(DateTime.now().toDate());
        orderAlteringRepository.save(orderEditBundle.getOrderAltering());
        return savedOrder;
    }

    public Page<Order> getPage(int page, int size){
        
	return getAvaliableOrders(userDetailsService.getCurrentUser(), new PageRequest(page, size));
    }

    public Page<Order> findBySearch (OrderDocument orderDocument, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
	List<Long> ids = orderDocumentRepository.findBySearch(orderDocument);
        if (ids == null) {
            return orderRepository.findByCompany(userDetailsService.getCurrentContact().getCompany(), pageRequest);
        
        } else if (ids.size() == 0) {
        
            return new PageImpl<Order>(new ArrayList<Order>());
        }
        
        return orderRepository.findByIdIsIn(ids, pageRequest);
    }

    public Order findOne(long id){
        return orderRepository.findOne(id);
    }

    public OrderEditBundle getOrderEditBundleById(long id){
        List<State> availableStates = new ArrayList<State>();
        OrderEditBundle orderEditBundle = new OrderEditBundle();
        orderEditBundle.setOrder(findOne(id));

        //  Getting info about user, editing the order
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userPrincipal = (UserDetails)auth.getPrincipal();
            if(userPrincipal != null){
                String editingUserRoleDescription = StringUtils.removeStart(userPrincipal.getAuthorities().toArray()[0].toString(), "ROLE_");

                //  Filter logic for this order and this user role
                List<State> allStates = orderEditBundle.getOrder().availableStatesStrategy().getAvailableOrderStates(editingUserRoleDescription);
                for(State state : allStates){
                    availableStates.add(getStateByDescription(state.getDescription()));
                }
                /*
                List<State> allStates = (List<State>)stateRepository.findAll();
                for(State state : allStates){
                    if(canChangeToThisState(editingUserRoleDescription, state, orderEditBundle.getOrder().getState())){
                        availableStates.add(state);
                    }
                }

                */
            }
        }
        orderEditBundle.setAvailableStates(availableStates);
        return orderEditBundle;
    }


    /*
    //TODO: make smart
    private boolean canChangeToThisState(String roleDescription, State newState, State currentState){
        switch (newState.getDescription()){
            case CANCELED:{
                return currentState.getDescription() != State.DESCRIPTION_TYPE.CANCELED;
            }
            case IMPOSSIBLE:{
                return  currentState.getDescription() != State.DESCRIPTION_TYPE.CANCELED
                        && currentState.getDescription() != State.DESCRIPTION_TYPE.IMPOSSIBLE;
            }
            case NEW:{
                return StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.SUPERVISOR.toString())
                        && currentState.getDescription() == State.DESCRIPTION_TYPE.IMPOSSIBLE;
            }
            case ACCEPTED:{
                return StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.ORDERS_MANAGER.toString())
                        && currentState.getDescription() == State.DESCRIPTION_TYPE.NEW;
            }
            case PROCESSING:{
                return StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.ORDERS_PROCESSOR.toString())
                        && currentState.getDescription() == State.DESCRIPTION_TYPE.ACCEPTED;
            }
            case READY:{
                return StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.ORDERS_PROCESSOR.toString())
                        && currentState.getDescription() == State.DESCRIPTION_TYPE.PROCESSING;
            }
            case DELIVERY:{
                return StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.DELIVERY_MANAGER.toString())
                        && currentState.getDescription() == State.DESCRIPTION_TYPE.READY;
            }
            case CLOSED:{
                return StringUtils.equalsIgnoreCase(roleDescription, Role.ROLE_TYPE.SUPERVISOR.toString())
                        && currentState.getDescription() == State.DESCRIPTION_TYPE.DELIVERY;
            }
            default:{
                return false;
            }
        }
    }

    */

    public OrderCreateBundle getOrderCreateBundle(){

        OrderCreateBundle orderCreateBundle = new OrderCreateBundle();

        //  Setting the state "NEW"
        orderCreateBundle.setState(getStateByDescription(State.DESCRIPTION_TYPE.NEW));

        //  Setting manager
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userPrincipal = (UserDetails) auth.getPrincipal();
            if (userPrincipal != null) {
                String login = userPrincipal.getUsername();
                orderCreateBundle.setManager(userRepository.findUserByLogin(login));
            }
        }
        return orderCreateBundle;
    }

    public OrderHistoryBundle getOrderHistoryBundle(Long id){
        Order order = orderRepository.findOne(id);
        List<OrderAltering> orderAlterings = orderAlteringRepository.findByOrder(order);
        return new OrderHistoryBundle(order, orderAlterings);
    }

    public Page<Order> getAvaliableOrders(User user, Pageable pageable) {
	switch (user.getRole().getName()) {
	case ORDERS_MANAGER:
	    return orderRepository.findAvailableByOrdersManager(user, pageable);
	case DELIVERY_MANAGER:
	    return orderRepository.findAvailableByDelivery(user, pageable);
	case ORDERS_PROCESSOR:
	    return orderRepository.findAvailableByStaff(user, pageable);
	case SUPERVISOR:
	    return orderRepository.findByCompany(userDetailsService.getCurrentContact().getCompany(), pageable);
	default:
	    return null;
	}
    }
}
