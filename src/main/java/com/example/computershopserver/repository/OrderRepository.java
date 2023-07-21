package com.example.computershopserver.repository;

import com.example.computershopserver.entity.Order;
import com.example.computershopserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByUser(User user);

    //    @Query(value = "SELECT orders.ngaydat, sum(order_detail.detail_price) as money FROM orders inner join order_detail on orders.id = order_detail.order_id \n" +
//            "WHERE DATE(orders.ngaydat) BETWEEN \"2022-12-07\" AND \"2022-12-12\"\n" +
//            "GROUP by ngaydat order by ngaydat", nativeQuery = true)
//    @Query(value = "SELECT orders.ngaydat, sum(order_detail.detail_price) as money FROM `orders` inner join order_detail on orders.id = order_detail.order_id \n" +
//            "WHERE DATE(orders.ngaydat) BETWEEN :startDate AND :endDate\n" +
//            "GROUP by ngaydat order by ngaydat\n" +
//            "\n", nativeQuery = true)
//    List<Object[]> getReportbyDate(@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);
    @Query(value = "SELECT orders.ngaydat, sum(order_detail.detail_price) as money FROM `orders` inner join order_detail on orders.id = order_detail.order_id \n" +
            "WHERE DATE(orders.ngaydat) BETWEEN :startDate AND :endDate \n" +
            "AND (orders.status = 'Delivered' OR orders.payment = 'paypal' )\n" +
            "GROUP by ngaydat order by ngaydat\n", nativeQuery = true)
    List<Object[]> getReportbyDate(@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

    //    use cputer;
//    SELECT orders.ngaydat, sum(order_detail.detail_price) as money FROM orders inner join order_detail on orders.id = order_detail.order_id
//    WHERE month(orders.ngaydat)=11
//    GROUP by ngaydat order by ngaydat
    @Query(value = "SELECT product.id, product.name,product.image, sum(o.detail_qty) as sells from order_detail as o inner join product on o.product_id = product.id group by product.id order by sells desc limit 5", nativeQuery = true)
    List<Object[]> getTopFiveProduct();

    @Query(value = "SELECT product.id, product.name,product.image, sum(o.detail_qty) as sells from order_detail as o inner join product on o.product_id = product.id group by product.id order by sells desc limit 5", nativeQuery = true)
    List<Object[]> getTopFive();
}

