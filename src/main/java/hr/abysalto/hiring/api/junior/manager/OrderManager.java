package hr.abysalto.hiring.api.junior.manager;

import java.util.List;

import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;

public interface OrderManager {
    List<Order> getAllOrders();

    void save(Order order);

    Order getById(Long id);

    void deleteById(Long id);

    Buyer getBuyerById(Long buyerId);

    BuyerAddress getBuyerAddressById(Long deliveryAddressId);

    List<OrderItem> getOrderItemsById(Long orderId);
}
