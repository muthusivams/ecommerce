package com.ecommerce.product.service;
import com.ecommerce.product.dto.ProductDto;import com.ecommerce.product.entity.Product;import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;import org.modelmapper.ModelMapper;import org.springframework.data.domain.*;import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class ProductService {
 private final ProductRepository repo; private final ModelMapper mapper;
 public ProductDto create(ProductDto dto){ return mapper.map(repo.save(mapper.map(dto,Product.class)),ProductDto.class);} 
 public ProductDto update(Long id, ProductDto dto){ Product p=repo.findById(id).orElseThrow(()->new RuntimeException("Product not found")); p.setName(dto.getName());p.setCategory(dto.getCategory());p.setDescription(dto.getDescription());p.setPrice(dto.getPrice());return mapper.map(repo.save(p),ProductDto.class);} 
 public ProductDto get(Long id){ return mapper.map(repo.findById(id).orElseThrow(()->new RuntimeException("Product not found")),ProductDto.class);} 
 public Page<ProductDto> list(String category,int page,int size){ Pageable pg=PageRequest.of(page,size); Page<Product> products=category==null?repo.findAll(pg):repo.findByCategory(category,pg); return products.map(p->mapper.map(p,ProductDto.class)); }
}
