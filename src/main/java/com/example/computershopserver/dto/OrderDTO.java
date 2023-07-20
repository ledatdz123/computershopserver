package com.example.computershopserver.dto;

import com.example.computershopserver.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate ngaydat;
    private String status;
    private Float total_price;
    private Long id_user;
    private String payment;
    private String address;
    private String phone;
    @JsonProperty("order_details")
    private List<OrderDetailResponseDTO> orderDetailResponseDTOS;

    public OrderDTO(Long id, LocalDate ngaydat, String status, Float total_price, Long id_user, String payment,String address, String phone, List<OrderDetailResponseDTO> orderDetailResponseDTOS) {
        this.id = id;
        this.ngaydat = ngaydat;
        this.status = status;
        this.total_price = total_price;
        this.id_user = id_user;
        this.payment=payment;
        this.address = address;
        this.phone = phone;
        this.orderDetailResponseDTOS = orderDetailResponseDTOS;
    }

    public Order dtoToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setTotal_price(dto.getTotal_price());
        order.setStatus("Pending");
        order.setNgaydat(dto.getNgaydat());
        order.setPayment(dto.getPayment());
        order.setAddress(dto.getAddress());
        order.setPhone(dto.getPhone());
//        DetailOrderDTO orderDTO = new DetailOrderDTO();
//        order.setDetailOrders(orderDTO.dtoToEntity(dto.getDetailOrderDTOS()));
        return order;
    }

    public OrderDTO entityToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setNgaydat(order.getNgaydat());
        dto.setStatus(order.getStatus());
        dto.setTotal_price(order.getTotal_price());
        dto.setAddress((order.getAddress()));
        dto.setPhone(order.getPhone());
        dto.setId_user(order.getUser().getId());
        List<OrderDetailResponseDTO> list = new ArrayList<>();
        if  (Objects.nonNull(order.getDetailOrders())){
            list = order.getDetailOrders().stream().map(detailOrder -> {
                return new OrderDetailResponseDTO().convertToDto(detailOrder);
            }).collect(Collectors.toList());
        }
        dto.setOrderDetailResponseDTOS(list);

        return dto;
    }

    public List<Order> dtoToEntity(List<OrderDTO> dtos) {
        List<Order> list = new ArrayList<>();
        if (Objects.nonNull(dtos)) {
            dtos.forEach(x -> list.add(this.dtoToEntity(x)));
        }
        return list;
    }

    public List<OrderDTO> entityToDTO(List<Order> orders) {
        List<OrderDTO> list = new ArrayList<>();
        if (Objects.nonNull(orders)) {
            orders.forEach(x -> {
                list.add(entityToDTO(x));
            });
        }
        return list;
    }


}

