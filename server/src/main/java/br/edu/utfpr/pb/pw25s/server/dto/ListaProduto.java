package br.edu.utfpr.pb.pw25s.server.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ListaProduto {

    private String name;
    private String description;
    private BigDecimal price;
    private String descricao_category;
}
