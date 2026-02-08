package hr.abysalto.hiring.api.junior.controller;

import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hr.abysalto.hiring.api.junior.components.DatabaseInitializer;
import hr.abysalto.hiring.api.junior.manager.OrderManager;
import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Item;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import hr.abysalto.hiring.api.junior.model.OrderStatus;
import hr.abysalto.hiring.api.junior.model.PaymentOption;
import hr.abysalto.hiring.api.junior.repository.BuyerAddressRepository;
import hr.abysalto.hiring.api.junior.repository.BuyerRepository;
import hr.abysalto.hiring.api.junior.repository.ItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Orders", description = "for handling orders")
@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private BuyerAddressRepository buyerAddressRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Operation(summary = "Get all orders", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))),
            @ApiResponse(description = "Precondition failed", responseCode = "412", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(description = "Error", responseCode = "500", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/list")
    public ResponseEntity list() {
        if (!this.databaseInitializer.isDataInitialized()) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Data not initialized");
        }
        try {
            return ResponseEntity.ok(this.orderManager.getAllOrders());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ex);
        }
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Order> orders = this.orderManager.getAllOrders();
        model.addAttribute("orderList", orders);

        for (Order order : orders) {
            order.setBuyer(orderManager.getBuyerById(order.getBuyerId()));
            order.setDeliveryAddress(orderManager.getBuyerAddressById(order.getDeliveryAddressId()));
            order.setOrderItems(orderManager.getOrderItemsById(order.getOrderId()));
        }

        return "order/index";
    }

    @GetMapping("/add")
    public String addNewOrder(Model model) {
        Order order = new Order();

        // init empty list
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem());
        order.setOrderItems(items);

        model.addAttribute("order", order);
        model.addAttribute("buyers", buyerRepository.findAll());
        model.addAttribute("addresses", buyerAddressRepository.findAll());
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("paymentOptions", PaymentOption.values());

        return "order/neworder";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") Order order,
            @RequestParam String addressCity,
            @RequestParam String addressStreet,
            @RequestParam(required = false) String addressHomeNumber) {

        // create buyer address record
        BuyerAddress address = new BuyerAddress();
        address.setCity(addressCity);
        address.setStreet(addressStreet);
        address.setHomeNumber(addressHomeNumber);
        BuyerAddress savedAddress = buyerAddressRepository.save(address);

        // set buyer address id in order
        order.setDeliveryAddressId(savedAddress.getBuyerAddressId());

        // init total price
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem orderItem : order.getOrderItems()) {
            // get current item from db
            Item item = itemRepository.findById(orderItem.getItemId()).orElseThrow();

            // set item's current name and price to order item
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());

            // calculate item total price
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

            // add item total price to total order price
            total = total.add(itemTotal);
        }

        // set order currency and price
        order.setCurrency("EUR");
        order.setTotalPrice(total);

        // save and redirect
        this.orderManager.save(order);
        return "redirect:/order/";
    }

    @PostMapping("/status/update")
    public String updateStatus(@ModelAttribute("order") Order order) {
        Order existingOrder = orderManager.getById(order.getOrderId());
        existingOrder.setOrderStatus(order.getOrderStatus());
        orderManager.save(existingOrder);
        return "redirect:/order/";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        Order order = this.orderManager.getById(id);
        model.addAttribute("order", order);
        model.addAttribute("orderStatuses", OrderStatus.values());
        return "order/updateorder";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(value = "id") long id) {
        this.orderManager.deleteById(id);
        return "redirect:/order/";
    }

}
