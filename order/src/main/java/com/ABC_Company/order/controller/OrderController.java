package com.ABC_Company.order.controller;

import com.ABC_Company.order.dto.OrderDTO;
import com.ABC_Company.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getorders")
    public List<OrderDTO> getOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping("/addorder")
    public OrderDTO saveOrder(@RequestBody OrderDTO orderDTO){
        return orderService.saveOrder(orderDTO);
    }

    @PutMapping("/updateorder")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId){
        return orderService.deleteOrder(orderId);
    }
}
