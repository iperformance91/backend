package com.example.loginregister.serviceImpl;

import org.slf4j.Logger;
import com.example.loginregister.exception.UserLockedException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginregister.Entity.User;
import com.example.loginregister.Repo.OrderRepository;
import com.example.loginregister.Repo.StoredImageRepository;
import com.example.loginregister.Repo.UserRepository;
import com.example.loginregister.Service.AuthService;
import com.example.loginregister.Entity.Order;
import com.example.loginregister.Entity.StoredImage;
import com.example.loginregister.Repo.UserRepository;
import com.example.loginregister.Repo.StoredImageRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StoredImageRepository storedImageRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    
    
    @Override
    public boolean emailExists(String email) { 
    	// Check if the email already exists in the database
        return userRepository.findByEmail(email) != null;  
    }
    
    @Override
    public List<Order> getOrdersByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    @Override
    public User registerUser(User user) {
    	
    	 // Set lock_status to 'Y' before saving the user
        user.setLock_status("N");
        
        user.setSystemReceivedDateTime(formattedDateTime);
        
        // Proceed with user registration
    	User savedUser = userRepository.save(user);
        logger.info("\n\nUser registered successfully: {}", savedUser.getEmail() +"  " +formattedDateTime+"\n");
        return savedUser;
    }

    @Override
    public User authenticateUser(String email, String password, String name) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            if ("Y".equals(user.getLock_status())) {
                String message = "User account is locked.";
                System.out.println(message);
                logger.error(message);
                try {
                    throw new UserLockedException(message);
                } catch (UserLockedException e) {
                    e.printStackTrace(); // Print stack trace
                    logger.error("Stack Trace:", e); // Log exception with stack trace
                    throw e; 
                }
            }
            
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy HHmmss");
//            String formattedDateTime = now.format(formatter);
            // Add log statement for successful login
            logger.info("\n\nAuthentication successful for user: " + user.getEmail()+"  "+formattedDateTime+"\n");
            return user;
        }
        return null;
    }

    @Override
    public List<StoredImage> getAllStoredImages() {
        return storedImageRepository.findAll();
    }

    @Override
    public StoredImage addStoredImage(StoredImage storedImage) {
        return storedImageRepository.save(storedImage);
    }

    @Override
    public List<StoredImage> getProductsByCategory(String product) {
        return storedImageRepository.findByCategoryName(product);
    }

//    @Override
//    public List<StoredImage> getProductsByProduct(String product) {
//        return storedImageRepository.findByProduct(product);
//    }
    
    @Override
    public List<StoredImage> getProductsByProduct(String product) {
        return storedImageRepository.findByProduct(product);
    }
    
//    @Override
//    public List<StoredImage> getProductsByProduct(String product) {
//        List<StoredImage> storedImages = storedImageRepository.findByProduct(product);
//        storedImages.forEach(image -> logger.info("Retrieved image: {}", image.getImg()));
//        return storedImages;
//    }

    @Override
    public List<StoredImage> getProductsByProductAndSubProduct(String product, String subProduct) {
        return storedImageRepository.findByProductAndSubProduct(product, subProduct);
    }
    
}
