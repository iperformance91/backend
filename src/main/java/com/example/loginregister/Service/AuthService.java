package com.example.loginregister.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loginregister.Entity.Order;
import com.example.loginregister.Entity.StoredImage;

import com.example.loginregister.Entity.User;
@Service
	
public interface AuthService {
	//yeaaaaaaaaaa buddy light weight!!
    User registerUser(User user);
    User authenticateUser(String email, String password, String name);
    boolean emailExists(String email);
    List<StoredImage> getAllStoredImages();
    StoredImage addStoredImage(StoredImage storedImage);
    List<Order> getOrdersByEmail(String email);
    List<StoredImage> getProductsByCategory(String product);
    List<StoredImage> getProductsByProduct(String product);
    List<StoredImage> getProductsByProductAndSubProduct(String product, String subProduct);
}

