package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.dto.OrderProductDto;
import br.edu.utfpr.pb.pw25s.server.model.Category;
import br.edu.utfpr.pb.pw25s.server.model.Order;
import br.edu.utfpr.pb.pw25s.server.model.OrderProduct;
import br.edu.utfpr.pb.pw25s.server.model.Product;
import br.edu.utfpr.pb.pw25s.server.repository.CategoryRepository;
import br.edu.utfpr.pb.pw25s.server.repository.OrderProductRepository;
import br.edu.utfpr.pb.pw25s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw25s.server.service.IOrderProductService;
import br.edu.utfpr.pb.pw25s.server.service.IProductService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductServiceImpl extends CrudServiceImpl<OrderProduct, Long> implements IOrderProductService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    protected JpaRepository<OrderProduct, Long> getRepository() {
        return orderProductRepository;
    }


    public void saveOrderProduct(List<OrderProductDto> lista, Order order) {


        for (int i = 0; i < lista.size(); i++) {

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(lista.get(i).getProduct());
            orderProduct.setPrice(BigDecimal.TEN);
            orderProduct.setQuantity(lista.get(i).getQuantity());
            orderProduct.setOrder(order);
            getRepository().save(orderProduct);
            orderProduct = null;

        }

    }

    public List<OrderProduct> buscaOrderProducts(Order order) {

        return orderProductRepository.findByOrder(order);

    }

}
