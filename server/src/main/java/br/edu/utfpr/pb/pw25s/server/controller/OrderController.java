package br.edu.utfpr.pb.pw25s.server.controller;

import br.edu.utfpr.pb.pw25s.server.dto.CategoryDto;
import br.edu.utfpr.pb.pw25s.server.dto.OrderDto;
import br.edu.utfpr.pb.pw25s.server.dto.OrderProductDto;
import br.edu.utfpr.pb.pw25s.server.dto.ProductDto;
import br.edu.utfpr.pb.pw25s.server.model.Category;
import br.edu.utfpr.pb.pw25s.server.model.Order;
import br.edu.utfpr.pb.pw25s.server.model.OrderProduct;
import br.edu.utfpr.pb.pw25s.server.model.Product;
import br.edu.utfpr.pb.pw25s.server.service.ICrudService;
import br.edu.utfpr.pb.pw25s.server.service.IOrderProductService;
import br.edu.utfpr.pb.pw25s.server.service.IOrderService;
import br.edu.utfpr.pb.pw25s.server.service.UserService;
import br.edu.utfpr.pb.pw25s.server.service.impl.OrderProductServiceImpl;
import br.edu.utfpr.pb.pw25s.server.service.impl.OrderServiceImpl;
import br.edu.utfpr.pb.pw25s.server.shared.GenericResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("orders")
public class OrderController {

    private final IOrderService service;

    private final ModelMapper modelMapper;

    private final UserService userService;

    public OrderController(IOrderService service, ModelMapper modelMapper, UserService userService) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private Order convertToEntity(OrderDto orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    private OrderProductDto convertToDtoPO(OrderProduct orderProduct) {
        return modelMapper.map(orderProduct, OrderProductDto.class);
    }

    private OrderProduct convertToEntityPO(OrderProductDto orderProductDto) {
        return modelMapper.map(orderProductDto, OrderProduct.class);
    }

    @PostMapping("/save")
    public GenericResponse create(
            @RequestBody @Valid OrderDto pedido) {

        Order order =

                order = service.saveDto(pedido);
        //salva primeiro a order

        //salva os produtos da order
        service.saveOrderProduct(pedido.getListaItens(), order);


        return GenericResponse.builder().message("Order saved.").build();


    }


    @GetMapping("/myorders")
    public ResponseEntity<List<OrderDto>> findPedidos() {
        List<Order> orders = service.findByUser(userService.getUserDoToken());
        List<OrderProduct> orderProducts = new ArrayList<>();
        List<OrderDto> orderDtos = new ArrayList<>();
        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(convertToDto(order));
        }
        //lista para passar de ORDER para ORDERDTO


        for (OrderDto orderDto : orderDtos) {  //passar por todos os pedidos do cliente
            orderProducts = service.buscaOrderProducts(convertToEntity(orderDto)); //busco a lista de produtos de um pedido especifico
            for (OrderProduct orderProduct : orderProducts) { //for para passar ORDERPRODUCT para ORDERPRODUCTDTO
                orderProductDtos.add(convertToDtoPO(orderProduct));
            }
            orderDto.setListaItens(orderProductDtos);
        }
        return ResponseEntity.ok(orderDtos);
    }
}
