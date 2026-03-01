package com.ecommerce.cart.service; import com.ecommerce.cart.dto.CartItem;import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;import org.springframework.stereotype.Service;import java.util.*;
@Service @RequiredArgsConstructor
public class CartService { private final RedisTemplate<String,Object> redis;
 private String key(Long userId){ return "cart:"+userId; }
 @SuppressWarnings("unchecked") private List<CartItem> get(Long userId){ Object existing=redis.opsForValue().get(key(userId)); return existing==null?new ArrayList<>():(List<CartItem>) existing; }
 public List<CartItem> add(Long userId,CartItem item){ List<CartItem> items=get(userId); items.removeIf(i->i.getProductId().equals(item.getProductId())); items.add(item); redis.opsForValue().set(key(userId),(Object)items); return items; }
 public List<CartItem> remove(Long userId,Long productId){ List<CartItem> items=get(userId); items.removeIf(i->i.getProductId().equals(productId)); redis.opsForValue().set(key(userId),(Object)items); return items; }
 public List<CartItem> update(Long userId,CartItem item){ return add(userId,item);} public List<CartItem> list(Long userId){ return get(userId);} }
