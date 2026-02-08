# Abysalto Technical Task

This project is a technical task for Abysalto.

## Getting Started

### Installation

```bash
git clone https://github.com/your-username/abysalto-tech-task.git
cd Abysalto_java_junior

```

### Running the App

```bash
./mvnw clean package
./mvnw spring-boot:run
```

The app will be available at [http://localhost:8080](http://localhost:8080).

### OrderController Routes and Their Purposes

- **GET `/order/`**  
  Shows the list of all orders.

- **GET `/order/add`**  
  Displays the form for creating a new order.

- **POST `/order/save`**  
  Handles form submission for creating or updating an order (saves order and its items).

- **GET `/order/update/{id}`**  
  Displays the form for updating an existing order status (by order ID).

- **POST `/order/status/update`**  
  Handles updating only the status of an order.

- **GET `/order/delete/{id}`**  
  Deletes the order with the given ID (and linked order items).

---

### BuyerController Routes and Their Purposes

- **GET `/buyer/`**  
  Shows the list of all buyers.

- **GET `/buyer/add`**  
  Displays the form for creating a new buyer.

- **POST `/buyer/save`**  
  Handles form submission for creating or updating a buyer.

- **GET `/buyer/update/{id}`**  
  Displays the form for updating an existing buyer (by buyer ID).

- **GET `/buyer/delete/{id}`**  
  Deletes the buyer with the given ID.

---

## License

This project is for technical evaluation purposes only.
