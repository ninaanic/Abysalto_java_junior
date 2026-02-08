package hr.abysalto.hiring.api.junior.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import hr.abysalto.hiring.api.junior.repository.BuyerAddressRepository;
import hr.abysalto.hiring.api.junior.repository.BuyerRepository;
import hr.abysalto.hiring.api.junior.repository.OrderItemRepository;
import hr.abysalto.hiring.api.junior.repository.OrderRepository;

@Component
public class OrderManagerImpl implements OrderManager {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private BuyerAddressRepository buyerAddressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Order> getAllOrders() {
        // get orders sorted by price asc
        List<Order> orderList = new ArrayList<>();
        this.orderRepository.findAll().forEach(orderList::add);
        orderList.sort(Comparator.comparing(Order::getTotalPrice));
        return orderList;
    }

    @Override
    public void save(Order order) {
        Order savedOrder = this.orderRepository.save(order);

        for (OrderItem item : order.getOrderItems()) {
            item.setOrderId(savedOrder.getOrderId());
            orderItemRepository.save(item);
        }
    }

    @Override
    public Order getById(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public Buyer getBuyerById(Long buyerId) {
        return this.buyerRepository.findById(buyerId).orElse(null);
    }

    @Override
    public BuyerAddress getBuyerAddressById(Long deliveryAddressId) {
        return this.buyerAddressRepository.findById(deliveryAddressId).orElse(null);
    }

    @Override
    public List<OrderItem> getOrderItemsById(Long orderId) {
        return this.orderItemRepository.findByOrderId(orderId);
    }
}
