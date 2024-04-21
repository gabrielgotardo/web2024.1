package br.edu.utfpr.pb.pw25s.server.service;

import br.edu.utfpr.pb.pw25s.server.dto.OrderDto;
import br.edu.utfpr.pb.pw25s.server.dto.OrderProductDto;
import br.edu.utfpr.pb.pw25s.server.model.*;

import java.util.List;

public interface IOrderService extends ICrudService<Order, Long> {


    List<Order> findByUser(User user);

    public Order saveDto(OrderDto orderDto);

    public void saveOrderProduct(List<OrderProductDto> lista, Order order);

    public List<OrderProduct> buscaOrderProducts(Order order);


}
