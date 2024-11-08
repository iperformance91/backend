package com.example.loginregister.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.loginregister.Entity.StoredImage;

public interface StoredImageRepository extends JpaRepository<StoredImage, Long> {
	List<StoredImage> findByCategoryName(@Param("product") String product);
    List<StoredImage> findByProduct(@Param("product") String product);
    List<StoredImage> findByProductAndSubProduct(@Param("product") String product, String subProduct);
	
}

