package com.ecommerce.order.client; import org.springframework.cloud.openfeign.FeignClient;import org.springframework.web.bind.annotation.*;
@FeignClient(name="inventory-service") public interface InventoryClient { @PostMapping("/api/inventory/reserve/{productId}") void reserve(@PathVariable("productId") Long productId, @RequestParam("qty") Integer qty); }
