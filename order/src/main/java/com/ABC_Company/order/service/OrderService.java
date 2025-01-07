package com.ABC_Company.order.service;

import com.ABC_Company.inventory.dto.InventoryDTO;
import com.ABC_Company.order.common.ErrorOrderResponse;
import com.ABC_Company.order.common.OrderResponse;
import com.ABC_Company.order.common.SuccessOrderResponse;
import com.ABC_Company.order.dto.OrderDTO;
import com.ABC_Company.order.model.Orders;
import com.ABC_Company.order.repo.OrderRepo;
import com.ABC_Company.product.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient,WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders(){
        List<Orders> ordersList = orderRepo.findAll();
        return modelMapper.map(ordersList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();

        try {
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

            Integer productId = inventoryResponse.getProductId();

            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId) )
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();


            assert  productResponse != null;


            if(inventoryResponse.getQuantity() > 0){

                if(productResponse.getForSale() == 1){
                    orderRepo.save(modelMapper.map(orderDTO,Orders.class));
                    return new SuccessOrderResponse(orderDTO);
                }else{
                    return new ErrorOrderResponse("Item is not for sale");
                }

            }else{
                return new ErrorOrderResponse("Item is not available");
            }

        }catch (WebClientResponseException e){
            if(e.getStatusCode().is5xxServerError()){
                return new ErrorOrderResponse("Item not found");
            }
        }

        return null;
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
