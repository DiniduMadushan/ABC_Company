package com.ABC_Company.order.service;

import com.ABC_Company.order.dto.OrderDTO;
import com.ABC_Company.order.model.Orders;
import com.ABC_Company.order.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders(){
        List<Orders> ordersList = orderRepo.findAll();
        return modelMapper.map(ordersList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderDTO saveOrder(OrderDTO orderDTO){
        orderRepo.save(modelMapper.map(orderDTO,Orders.class));
        return orderDTO;
    }

    public OrderDTO updateOrder(OrderDTO orderDTO){
        orderRepo.save(modelMapper.map(orderDTO,Orders.class));
        return orderDTO;
    }

    public String deleteOrder(Integer orderId){
        orderRepo.deleteById(orderId);
        return "order deleted successfully";
    }

}
