package br.edu.utfpr.pb.pw25s.server.dto;


import br.edu.utfpr.pb.pw25s.server.model.Order;
import br.edu.utfpr.pb.pw25s.server.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {

    private Long id;
    private Product product;
    private BigDecimal price;
    private Integer quantity;


}
