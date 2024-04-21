package br.edu.utfpr.pb.pw25s.server.service;

import br.edu.utfpr.pb.pw25s.server.dto.OrderDto;
import br.edu.utfpr.pb.pw25s.server.dto.OrderProductDto;
import br.edu.utfpr.pb.pw25s.server.model.Order;
import br.edu.utfpr.pb.pw25s.server.model.OrderProduct;

import java.util.List;

public interface IOrderProductService extends ICrudService<OrderProduct, Long> {

    public void saveOrderProduct(List<OrderProductDto> lista, Order order);

}
