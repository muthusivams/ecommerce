package com.ecommerce.cart.controller; import com.ecommerce.cart.dto.CartItem;import com.ecommerce.cart.service.CartService;
import lombok.RequiredArgsConstructor;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/cart") @RequiredArgsConstructor
public class CartController { private final CartService service;
 @PostMapping("/{userId}/items") public List<CartItem> add(@PathVariable Long userId,@RequestBody CartItem item){ return service.add(userId,item);} 
 @PutMapping("/{userId}/items") public List<CartItem> update(@PathVariable Long userId,@RequestBody CartItem item){ return service.update(userId,item);} 
 @DeleteMapping("/{userId}/items/{productId}") public List<CartItem> remove(@PathVariable Long userId,@PathVariable Long productId){ return service.remove(userId,productId);} 
 @GetMapping("/{userId}") public List<CartItem> list(@PathVariable Long userId){ return service.list(userId);} }
