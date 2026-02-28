package com.ecommerce.product.controller;
import com.ecommerce.product.dto.ProductDto;import com.ecommerce.product.service.ProductService;import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;
@RestController @RequestMapping("/api/products") @RequiredArgsConstructor
public class ProductController {
 private final ProductService service;
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public ProductDto create(@Valid @RequestBody ProductDto dto){ return service.create(dto);} 
 @PutMapping("/{id}") public ProductDto update(@PathVariable Long id,@Valid @RequestBody ProductDto dto){ return service.update(id,dto);} 
 @GetMapping("/{id}") public ProductDto get(@PathVariable Long id){ return service.get(id);} 
 @GetMapping public Page<ProductDto> list(@RequestParam(required=false) String category,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){ return service.list(category,page,size);} }
