package com.example.loginregister.EmployeeController;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.loginregister.Entity.Order;
import com.example.loginregister.Entity.StoredImage;
import com.example.loginregister.Entity.User;
import com.example.loginregister.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private List<Map<String, Object>> foodItems = new ArrayList<>();
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User authenticatedUser = authService.authenticateUser(user.getEmail(), user.getPassword(), user.getName());

        if (authenticatedUser != null) {
            logger.info("User authenticated: {}", authenticatedUser.getEmail());
            return ResponseEntity.ok(authenticatedUser);
        } else {
            logger.warn("Authentication failed for user with email: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = authService.registerUser(user);
        logger.info("User registered: {}", registeredUser.getEmail());
        return ResponseEntity.ok(registeredUser);
    }
    
//    @GetMapping("/display")
//    public Map<String, Object> displayProducts() {
//    	
//        Map<String, Object> response = new HashMap<>();
//        try {
//            response.put("food_items", foodItems);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("success", false);
//        }
//        return response;
//    }
    
//    @GetMapping("/displayProducts")
//    public Map<String, Object> displayProducts() {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            List<StoredImage> storedImages = authService.getAllStoredImages();
//            response.put("products", storedImages);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("success", false);
//        }
//        return response;
//    }
    
//    @GetMapping("/displayProducts")
//    public Map<String, Object> displayProducts() {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            List<StoredImage> storedImages = authService.getAllStoredImages();
//            List<StoredImage> dellProducts = authService.getProductsByCategory("dell");
//            List<StoredImage> acerProducts = authService.getProductsByCategory("acer");
//            // Add more categories as needed
//
//            response.put("products", storedImages);
//            response.put("dell", dellProducts);
//            response.put("acer", acerProducts);
//            // Add more categories to the response
//
//            response.put("success", true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("success", false);
//        }
//        return response;
//    }

    
    @GetMapping("/displayProducts")
    public Map<String, Object> displayProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<StoredImage> storedImages = authService.getAllStoredImages();
            List<StoredImage> dellProducts = authService.getProductsByCategory("dell");
            List<StoredImage> acerProducts = authService.getProductsByCategory("acer");
            // Add more categories as needed

            response.put("products", storedImages);
            response.put("dell", dellProducts);
            response.put("acer", acerProducts);
            // Add more categories to the response

            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response;
    }
    
    @GetMapping("/displayProductsByProduct")
    public Map<String, Object> displayProductsByProduct(@RequestParam String product) {
        Map<String, Object> response = new HashMap<>();
        try {
        	
            List<StoredImage> storedImages = authService.getProductsByProduct(product);
            System.out.println(product);
            System.out.println("stored images are"+storedImages);
            
            response.put("products", storedImages);
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response;
    }

    @GetMapping("/displayProductsByProductAndSubProduct")
    public Map<String, Object> displayProductsByProductAndSubProduct(@RequestParam String product, @RequestParam String subProduct) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<StoredImage> storedImages = authService.getProductsByProductAndSubProduct(product, subProduct);
            response.put("products", storedImages);
            
            System.out.println("stored images in prods sub prods are"+storedImages);
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response;
    }
    
    
    @PostMapping("/getOrderedItems")
    public ResponseEntity<?> getOrderedItems(@RequestBody User user) {
        try {
            String email = user.getEmail();
            List<Order> orderedData = authService.getOrdersByEmail(email);

            if (orderedData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the specified user.");
            }

            return ResponseEntity.ok(orderedData);
        } catch (Exception e) {
            logger.error("Internal server error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }
    
//    
//    @PostMapping("/getOrderedItems")
//    public ResponseEntity<?> getOrderedItems(@RequestBody User user) {
//        try {
//            String email = user.getEmail();
//            List<Order> orderedData = authService.getOrdersByEmail(email);
//
//            if (orderedData.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the specified user.");
//            }
//
//            // Assuming your Order entity has a structure similar to this, adjust as per your actual entity structure
//            List<Map<String, Object>> formattedOrders = new ArrayList<>();
//            for (Order order : orderedData) {
//                Map<String, Object> orderDetails = new HashMap<>();
//                orderDetails.put("order_date", order.getOrderDate()); // Assuming getOrderDate returns the order date
//                orderDetails.put("order_data", order.getOrderData()); // Assuming getOrderData returns the order data details
//                formattedOrders.add(orderDetails);
//            }
//
//            return ResponseEntity.ok(formattedOrders);
//        } catch (Exception e) {
//            logger.error("Error fetching orders for user: {}", user.getEmail(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
//        }
//    }

    
    @PostMapping("/stored-image")
    public ResponseEntity<StoredImage> addStoredImage(@RequestBody StoredImage storedImage) {
        StoredImage newStoredImage = authService.addStoredImage(storedImage);
        return ResponseEntity.ok(newStoredImage);
    }

    @GetMapping("/stored-images")
    public ResponseEntity<List<StoredImage>> getStoredImages() {
        List<StoredImage> storedImages = authService.getAllStoredImages();
        return ResponseEntity.ok(storedImages);
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = authService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/displayProductsByCategory")
    public Map<String, Object> displayProductsByCategory(@RequestParam String category) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<StoredImage> storedImages = authService.getProductsByCategory(category);
            response.put("products", storedImages);
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response;
    }
}
