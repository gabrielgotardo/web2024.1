package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.dto.OrderDto;
import br.edu.utfpr.pb.pw25s.server.dto.OrderProductDto;
import br.edu.utfpr.pb.pw25s.server.model.Order;
import br.edu.utfpr.pb.pw25s.server.model.OrderProduct;
import br.edu.utfpr.pb.pw25s.server.model.Product;
import br.edu.utfpr.pb.pw25s.server.model.User;
import br.edu.utfpr.pb.pw25s.server.repository.OrderProductRepository;
import br.edu.utfpr.pb.pw25s.server.repository.OrderRepository;
import br.edu.utfpr.pb.pw25s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw25s.server.service.IOrderService;
import br.edu.utfpr.pb.pw25s.server.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements IOrderService {

    private final OrderRepository orderRepository;


    private final OrderProductRepository orderProductRepository;

    private final ProductRepository productRepository;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductRepository productRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    protected JpaRepository<OrderProduct, Long> getRepositoryOP() {
        return orderProductRepository;
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }


    public Order saveDto(OrderDto orderDto) {

        //save padrão porem pegando o usuario autenticado

        Order order = new Order();
        order.setUser(userService.getUserDoToken());
        order.setData(new Date());
        order.setPagamento(orderDto.getPagamento());
        return getRepository().save(order);
    }


    public void saveOrderProduct(List<OrderProductDto> lista, Order order) {


        for (int i = 0; i < lista.size(); i++) {

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(lista.get(i).getProduct());

            Optional<Product> product = productRepository.findById(lista.get(i).getProduct().getId());

            //seta o valor do produto --- multiplica valor do banco de dados pela quantidade
            if (product != null) {

                orderProduct.setPrice(product.get().getPrice().multiply(BigDecimal.valueOf(lista.get(i).getQuantity())));
            } else {
                orderProduct.setPrice(BigDecimal.ZERO);
            }


            orderProduct.setQuantity(lista.get(i).getQuantity());

            orderProduct.setOrder(order);

            getRepositoryOP().save(orderProduct);

            orderProduct = null;


        }


    }

    public List<OrderProduct> buscaOrderProducts(Order order) {

        return orderProductRepository.findByOrder(order);
    }
}
