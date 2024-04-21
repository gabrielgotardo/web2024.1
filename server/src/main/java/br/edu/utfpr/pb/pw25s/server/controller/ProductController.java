package br.edu.utfpr.pb.pw25s.server.controller;


import br.edu.utfpr.pb.pw25s.server.dto.ProductDto;
import br.edu.utfpr.pb.pw25s.server.model.Category;
import br.edu.utfpr.pb.pw25s.server.model.Product;
import br.edu.utfpr.pb.pw25s.server.service.impl.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductServiceImpl service;


    private final ModelMapper modelMapper;

    public ProductController(ProductServiceImpl service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }


    private ProductDto convertToDto(Product product) {
        if (product != null) {
            return modelMapper.map(product, ProductDto.class);
        }
        return null;
    }


    private Product convertToEntity(ProductDto productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllComDto() {
        List<ProductDto> productDtos = service.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> findOneComDto(@PathVariable Long id) {
        ProductDto productDto = convertToDto(service.findOne(id));
        if (productDto != null) {
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("{id}/category")
    public ResponseEntity<List<ProductDto>> findByCategoryComDto(@PathVariable Long id) {
        List<ProductDto> productDtos = service.findByCategory(id).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        if (!productDtos.isEmpty()) {
            return ResponseEntity.ok(productDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}

