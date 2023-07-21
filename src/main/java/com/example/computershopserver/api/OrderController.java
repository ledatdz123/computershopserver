package com.example.computershopserver.api;

import com.example.computershopserver.dto.OrderDTO;
import com.example.computershopserver.dto.ResponseDTO;
import com.example.computershopserver.dto.responsecode.ErrorCode;
import com.example.computershopserver.dto.responsecode.SuccessCode;
import com.example.computershopserver.entity.DetailOrder;
import com.example.computershopserver.entity.Order;
import com.example.computershopserver.entity.Product;
import com.example.computershopserver.exception.*;
import com.example.computershopserver.repository.DetailOrderRepository;
import com.example.computershopserver.repository.OrderRepository;
import com.example.computershopserver.repository.ProductRepository;
import com.example.computershopserver.response.MessageResponse;
import com.example.computershopserver.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/orders")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DetailOrderRepository detailRepository;
    @Autowired
    private ProductRepository productrepository;
    //find all order
    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllOrder() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<OrderDTO> orderDTOS = orderService.retrieveOrders();
            List list = Collections.synchronizedList(new ArrayList<>(orderDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(orderDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_ORDER_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_ORDER_ERROR);
        }
        return ResponseEntity.ok(response);
    }
    //find order by order_id
    @GetMapping("/{order_id}")
    public ResponseEntity<ResponseDTO> findOrder(@PathVariable("order_id") Long orderId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<OrderDTO> orderDTO = orderService.getOrder(orderId);
            responseDTO.setData(orderDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_ORDER_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }
    //find order by user_id
    @GetMapping("/user/{user_id}")
    public ResponseEntity<ResponseDTO> findOrderByUser(@PathVariable("user_id") @NotBlank Long userId) throws ResourceNotFoundException {
        System.out.println(userId);
        ResponseDTO responseDTO = new ResponseDTO();
        List<OrderDTO> orderDTOS = orderService.findOrderByUser(userId);
        responseDTO.setData(orderDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    // create order
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createOrder( @RequestBody OrderDTO orderDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO dto = orderService.saveOrder(orderDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_ORDER_SUCCESS);
        } catch (Exception e){
            log.error("Order controller:" + e.getMessage());
            throw new AddDataFail(""+ ErrorCode.ADD_ORDER_ERROR +":" + e.getMessage());
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //update
    @PutMapping("/{order_id}")
//    {
//        "status": "completed",
//            "total_price": 1000
//    }
    public ResponseEntity<ResponseDTO> updateOrder(@PathVariable(value = "order_id") Long orderId,
                                                   @Valid @RequestBody OrderDTO orderDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO updateOrder = orderService.updateOrder(orderId, orderDTO);

            responseDTO.setData(updateOrder);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/order/{order_id}")
    public ResponseEntity<ResponseDTO> deleteOrder(@PathVariable(value = "order_id") Long orderId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = orderService.deleteOrder(orderId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //update status order
    @PutMapping("/status/{order_id}")
//    {
//        "status": "processing"
//    }
    public ResponseEntity<ResponseDTO> updateOrderStatus(@PathVariable(value = "order_id") Long orderId,
                                                         @Valid @RequestBody OrderDTO orderDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO updateOrder = orderService.updateOrderStatus(orderId, orderDTO);
            responseDTO.setData(updateOrder);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/updateStatusStep/{order_id}")
    public ResponseEntity<ResponseDTO> updateStatusOrder(@PathVariable(value = "order_id") Long orderId) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderDTO updateOrder = orderService.updateStatusOrder(orderId);

            responseDTO.setData(updateOrder);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/statusCancel/{order_id}")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> statusCancel(@PathVariable(value = "order_id") Long orderId) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: " + orderId));
        String status = orderExist.getStatus();
        if (!status.equals("Pending")) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Pending status has confirmed !"));
        }
        else {
            orderExist.setStatus("Canceled");
            Order order = new Order();
            order = orderRepository.save(orderExist);

            Optional<Order> orderExistNew = orderRepository.findById(orderId);
            if (!orderExistNew.isPresent()) {
                throw new ResourceNotFoundException("" + ErrorCode.FIND_ORDER_ERROR);
            }
            Order orderNew = orderExistNew.get();

            List<DetailOrder> list = null;
            list = detailRepository.findOrderDetailsByOrder(order);
            for (DetailOrder orderItem : list
            ) {
                Product product = productrepository.findById(orderItem.getProduct().getId()).orElseThrow(() ->
                        new ResourceNotFoundException("product not found for this id: " + orderItem.getProduct().getId()));
                product.setQuantity(orderItem.getDetail_qty() + product.getQuantity());
                productrepository.save(product);
            }
        }

        return ResponseEntity.ok(new MessageResponse("Cancel successed!"));
    }
}

