package com.ecommerce.inventory.controller; import com.ecommerce.inventory.entity.Inventory;import com.ecommerce.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/inventory") @RequiredArgsConstructor
public class InventoryController { private final InventoryService service;
 @PostMapping("/track/{productId}") public Inventory track(@PathVariable Long productId,@RequestParam Integer stock){ return service.track(productId,stock);} 
 @PostMapping("/reserve/{productId}") public Inventory reserve(@PathVariable Long productId,@RequestParam Integer qty){ return service.reserve(productId,qty);} 
 @PostMapping("/reduce/{productId}") public Inventory reduce(@PathVariable Long productId,@RequestParam Integer qty){ return service.reduce(productId,qty);} }
