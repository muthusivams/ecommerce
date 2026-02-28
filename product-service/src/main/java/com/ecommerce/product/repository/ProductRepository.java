package com.ecommerce.product.repository;
import com.ecommerce.product.entity.Product;import org.springframework.data.domain.*;import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product,Long>{ Page<Product> findByCategory(String category, Pageable pageable); }
