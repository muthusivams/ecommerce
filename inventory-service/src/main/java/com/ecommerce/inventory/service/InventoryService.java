package com.ecommerce.inventory.service;
import com.ecommerce.inventory.entity.Inventory;import com.ecommerce.inventory.event.StockEvent;import com.ecommerce.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;import org.springframework.kafka.core.KafkaTemplate;import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class InventoryService { private final InventoryRepository repo; private final KafkaTemplate<String,Object> kafka;
 public Inventory track(Long productId,Integer stock){ Inventory inv=repo.findByProductId(productId).orElse(Inventory.builder().productId(productId).availableStock(0).build()); inv.setAvailableStock(stock); Inventory saved=repo.save(inv); kafka.send("stock-events",new StockEvent(productId,stock,"TRACKED")); return saved; }
 public Inventory reserve(Long productId,Integer qty){ Inventory inv=repo.findByProductId(productId).orElseThrow(()->new RuntimeException("Missing inventory")); if(inv.getAvailableStock()<qty) throw new IllegalStateException("Insufficient stock"); inv.setAvailableStock(inv.getAvailableStock()-qty); Inventory saved=repo.save(inv); kafka.send("stock-events",new StockEvent(productId,saved.getAvailableStock(),"RESERVED")); return saved; }
 public Inventory reduce(Long productId,Integer qty){ return reserve(productId,qty); }
}
