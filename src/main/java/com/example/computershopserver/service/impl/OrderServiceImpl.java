package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.OrderDTO;
import com.example.computershopserver.dto.responsecode.ErrorCode;
import com.example.computershopserver.entity.DetailOrder;
import com.example.computershopserver.entity.Order;
import com.example.computershopserver.entity.Product;
import com.example.computershopserver.entity.User;
import com.example.computershopserver.exception.ResourceNotFoundException;
import com.example.computershopserver.exception.UpdateDataFail;
import com.example.computershopserver.repository.DetailOrderRepository;
import com.example.computershopserver.repository.OrderRepository;
import com.example.computershopserver.repository.ProductRepository;
import com.example.computershopserver.repository.UserRepository;
import com.example.computershopserver.response.MessageResponse;
import com.example.computershopserver.service.EmailService;
import com.example.computershopserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private DetailOrderRepository detailRepository;
    @Autowired
    private ProductRepository productrepository;

    @Override
    public List<OrderDTO> retrieveOrders() {
        List<Order> orders = orderRepository.findAll();

        return new OrderDTO().entityToDTO(orders);
    }

    @Override
    public Optional<OrderDTO> getOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: " + orderId));
        return Optional.of(new OrderDTO().entityToDTO(order));
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(orderDTO.getId_user()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: " + orderDTO.getId_user()));
//        Order order = new OrderDTO().dtoToEntity(orderDTO);
//        order.setUser(user);
//        order.setNgaydat(java.time.LocalDate.now());
//        return new OrderDTO().entityToDTO(orderRepository.save(order));

        Order order=new Order();
        order.setUser(user);
        order.setTotal_price(orderDTO.getTotal_price());
        order.setStatus("Pending");
        order.setNgaydat(java.time.LocalDate.now());
        order.setPayment(orderDTO.getPayment());
        order.setAddress(orderDTO.getAddress());
        order.setPhone(orderDTO.getPhone());
        Order saveOrder=orderRepository.save(order);

        String email=saveOrder.getUser().getEmail();
        String firstName=saveOrder.getUser().getFirstName();
        String lastName=saveOrder.getUser().getLastName();
        String address=saveOrder.getAddress();
        String phone=saveOrder.getPhone();
        String ngaydat=saveOrder.getNgaydat().toString();
        Float total=saveOrder.getTotal_price();
        Long orderId=saveOrder.getId();
        String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"color-scheme\" content=\"light dark\" />\n" +
                "    <meta name=\"supported-color-schemes\" content=\"light dark\" />\n" +
                "    <title></title>\n" +
                "    <style type=\"text/css\" rel=\"stylesheet\" media=\"all\">\n" +
                "    /* Base ------------------------------ */\n" +
                "    \n" +
                "    @import url(\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,700&display=swap\");\n" +
                "    body {\n" +
                "      width: 100% !important;\n" +
                "      height: 100%;\n" +
                "      margin: 0;\n" +
                "      -webkit-text-size-adjust: none;\n" +
                "    }\n" +
                "    \n" +
                "    a {\n" +
                "      color: #3869D4;\n" +
                "    }\n" +
                "    \n" +
                "    a img {\n" +
                "      border: none;\n" +
                "    }\n" +
                "    \n" +
                "    td {\n" +
                "      word-break: break-word;\n" +
                "    }\n" +
                "    \n" +
                "    .preheader {\n" +
                "      display: none !important;\n" +
                "      visibility: hidden;\n" +
                "      mso-hide: all;\n" +
                "      font-size: 1px;\n" +
                "      line-height: 1px;\n" +
                "      max-height: 0;\n" +
                "      max-width: 0;\n" +
                "      opacity: 0;\n" +
                "      overflow: hidden;\n" +
                "    }\n" +
                "    /* Type ------------------------------ */\n" +
                "    \n" +
                "    body,\n" +
                "    td,\n" +
                "    th {\n" +
                "      font-family: \"Nunito Sans\", Helvetica, Arial, sans-serif;\n" +
                "    }\n" +
                "    \n" +
                "    h1 {\n" +
                "      margin-top: 0;\n" +
                "      color: #333333;\n" +
                "      font-size: 22px;\n" +
                "      font-weight: bold;\n" +
                "      text-align: left;\n" +
                "    }\n" +
                "    \n" +
                "    h2 {\n" +
                "      margin-top: 0;\n" +
                "      color: #333333;\n" +
                "      font-size: 16px;\n" +
                "      font-weight: bold;\n" +
                "      text-align: left;\n" +
                "    }\n" +
                "    \n" +
                "    h3 {\n" +
                "      margin-top: 0;\n" +
                "      color: #333333;\n" +
                "      font-size: 14px;\n" +
                "      font-weight: bold;\n" +
                "      text-align: left;\n" +
                "    }\n" +
                "    \n" +
                "    td,\n" +
                "    th {\n" +
                "      font-size: 16px;\n" +
                "    }\n" +
                "    \n" +
                "    p,\n" +
                "    ul,\n" +
                "    ol,\n" +
                "    blockquote {\n" +
                "      margin: .4em 0 1.1875em;\n" +
                "      font-size: 16px;\n" +
                "      line-height: 1.625;\n" +
                "    }\n" +
                "    \n" +
                "    p.sub {\n" +
                "      font-size: 13px;\n" +
                "    }\n" +
                "    /* Utilities ------------------------------ */\n" +
                "    \n" +
                "    .align-right {\n" +
                "      text-align: right;\n" +
                "    }\n" +
                "    \n" +
                "    .align-left {\n" +
                "      text-align: left;\n" +
                "    }\n" +
                "    \n" +
                "    .align-center {\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    \n" +
                "    .u-margin-bottom-none {\n" +
                "      margin-bottom: 0;\n" +
                "    }\n" +
                "    /* Buttons ------------------------------ */\n" +
                "    \n" +
                "    .button {\n" +
                "      background-color: #3869D4;\n" +
                "      border-top: 10px solid #3869D4;\n" +
                "      border-right: 18px solid #3869D4;\n" +
                "      border-bottom: 10px solid #3869D4;\n" +
                "      border-left: 18px solid #3869D4;\n" +
                "      display: inline-block;\n" +
                "      color: #FFF;\n" +
                "      text-decoration: none;\n" +
                "      border-radius: 3px;\n" +
                "      box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16);\n" +
                "      -webkit-text-size-adjust: none;\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "    \n" +
                "    .button--green {\n" +
                "      background-color: #22BC66;\n" +
                "      border-top: 10px solid #22BC66;\n" +
                "      border-right: 18px solid #22BC66;\n" +
                "      border-bottom: 10px solid #22BC66;\n" +
                "      border-left: 18px solid #22BC66;\n" +
                "    }\n" +
                "    \n" +
                "    .button--red {\n" +
                "      background-color: #FF6136;\n" +
                "      border-top: 10px solid #FF6136;\n" +
                "      border-right: 18px solid #FF6136;\n" +
                "      border-bottom: 10px solid #FF6136;\n" +
                "      border-left: 18px solid #FF6136;\n" +
                "    }\n" +
                "    \n" +
                "    @media only screen and (max-width: 500px) {\n" +
                "      .button {\n" +
                "        width: 100% !important;\n" +
                "        text-align: center !important;\n" +
                "      }\n" +
                "    }\n" +
                "    /* Attribute list ------------------------------ */\n" +
                "    \n" +
                "    .attributes {\n" +
                "      margin: 0 0 21px;\n" +
                "    }\n" +
                "    \n" +
                "    .attributes_content {\n" +
                "      background-color: #F4F4F7;\n" +
                "      padding: 16px;\n" +
                "    }\n" +
                "    \n" +
                "    .attributes_item {\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    /* Related Items ------------------------------ */\n" +
                "    \n" +
                "    .related {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 25px 0 0 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .related_item {\n" +
                "      padding: 10px 0;\n" +
                "      color: #CBCCCF;\n" +
                "      font-size: 15px;\n" +
                "      line-height: 18px;\n" +
                "    }\n" +
                "    \n" +
                "    .related_item-title {\n" +
                "      display: block;\n" +
                "      margin: .5em 0 0;\n" +
                "    }\n" +
                "    \n" +
                "    .related_item-thumb {\n" +
                "      display: block;\n" +
                "      padding-bottom: 10px;\n" +
                "    }\n" +
                "    \n" +
                "    .related_heading {\n" +
                "      border-top: 1px solid #CBCCCF;\n" +
                "      text-align: center;\n" +
                "      padding: 25px 0 10px;\n" +
                "    }\n" +
                "    /* Discount Code ------------------------------ */\n" +
                "    \n" +
                "    .discount {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 24px;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "      background-color: #F4F4F7;\n" +
                "      border: 2px dashed #CBCCCF;\n" +
                "    }\n" +
                "    \n" +
                "    .discount_heading {\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    \n" +
                "    .discount_body {\n" +
                "      text-align: center;\n" +
                "      font-size: 15px;\n" +
                "    }\n" +
                "    /* Social Icons ------------------------------ */\n" +
                "    \n" +
                "    .social {\n" +
                "      width: auto;\n" +
                "    }\n" +
                "    \n" +
                "    .social td {\n" +
                "      padding: 0;\n" +
                "      width: auto;\n" +
                "    }\n" +
                "    \n" +
                "    .social_icon {\n" +
                "      height: 20px;\n" +
                "      margin: 0 8px 10px 8px;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    /* Data table ------------------------------ */\n" +
                "    \n" +
                "    .purchase {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 35px 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_content {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 25px 0 0 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_item {\n" +
                "      padding: 10px 0;\n" +
                "      color: #51545E;\n" +
                "      font-size: 15px;\n" +
                "      line-height: 18px;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_heading {\n" +
                "      padding-bottom: 8px;\n" +
                "      border-bottom: 1px solid #EAEAEC;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_heading p {\n" +
                "      margin: 0;\n" +
                "      color: #85878E;\n" +
                "      font-size: 12px;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_footer {\n" +
                "      padding-top: 15px;\n" +
                "      border-top: 1px solid #EAEAEC;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_total {\n" +
                "      margin: 0;\n" +
                "      text-align: right;\n" +
                "      font-weight: bold;\n" +
                "      color: #333333;\n" +
                "    }\n" +
                "    \n" +
                "    .purchase_total--label {\n" +
                "      padding: 0 15px 0 0;\n" +
                "    }\n" +
                "    \n" +
                "    body {\n" +
                "      background-color: #F2F4F6;\n" +
                "      color: #51545E;\n" +
                "    }\n" +
                "    \n" +
                "    p {\n" +
                "      color: #51545E;\n" +
                "    }\n" +
                "    \n" +
                "    .email-wrapper {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "      background-color: #F2F4F6;\n" +
                "    }\n" +
                "    \n" +
                "    .email-content {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "    }\n" +
                "    /* Masthead ----------------------- */\n" +
                "    \n" +
                "    .email-masthead {\n" +
                "      padding: 25px 0;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    \n" +
                "    .email-masthead_logo {\n" +
                "      width: 94px;\n" +
                "    }\n" +
                "    \n" +
                "    .email-masthead_name {\n" +
                "      font-size: 16px;\n" +
                "      font-weight: bold;\n" +
                "      color: #A8AAAF;\n" +
                "      text-decoration: none;\n" +
                "      text-shadow: 0 1px 0 white;\n" +
                "    }\n" +
                "    /* Body ------------------------------ */\n" +
                "    \n" +
                "    .email-body {\n" +
                "      width: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .email-body_inner {\n" +
                "      width: 570px;\n" +
                "      margin: 0 auto;\n" +
                "      padding: 0;\n" +
                "      -premailer-width: 570px;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "      background-color: #FFFFFF;\n" +
                "    }\n" +
                "    \n" +
                "    .email-footer {\n" +
                "      width: 570px;\n" +
                "      margin: 0 auto;\n" +
                "      padding: 0;\n" +
                "      -premailer-width: 570px;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    \n" +
                "    .email-footer p {\n" +
                "      color: #A8AAAF;\n" +
                "    }\n" +
                "    \n" +
                "    .body-action {\n" +
                "      width: 100%;\n" +
                "      margin: 30px auto;\n" +
                "      padding: 0;\n" +
                "      -premailer-width: 100%;\n" +
                "      -premailer-cellpadding: 0;\n" +
                "      -premailer-cellspacing: 0;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    \n" +
                "    .body-sub {\n" +
                "      margin-top: 25px;\n" +
                "      padding-top: 25px;\n" +
                "      border-top: 1px solid #EAEAEC;\n" +
                "    }\n" +
                "    \n" +
                "    .content-cell {\n" +
                "      padding: 45px;\n" +
                "    }\n" +
                "    /*Media Queries ------------------------------ */\n" +
                "    \n" +
                "    @media only screen and (max-width: 600px) {\n" +
                "      .email-body_inner,\n" +
                "      .email-footer {\n" +
                "        width: 100% !important;\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "    @media (prefers-color-scheme: dark) {\n" +
                "      body,\n" +
                "      .email-body,\n" +
                "      .email-body_inner,\n" +
                "      .email-content,\n" +
                "      .email-wrapper,\n" +
                "      .email-masthead,\n" +
                "      .email-footer {\n" +
                "        background-color: #333333 !important;\n" +
                "        color: #FFF !important;\n" +
                "      }\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol,\n" +
                "      blockquote,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      span,\n" +
                "      .purchase_item {\n" +
                "        color: #FFF !important;\n" +
                "      }\n" +
                "      .attributes_content,\n" +
                "      .discount {\n" +
                "        background-color: #222 !important;\n" +
                "      }\n" +
                "      .email-masthead_name {\n" +
                "        text-shadow: none !important;\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "    :root {\n" +
                "      color-scheme: light dark;\n" +
                "      supported-color-schemes: light dark;\n" +
                "    }\n" +
                "    </style>\n" +
                "    <!--[if mso]>\n" +
                "    <style type=\"text/css\">\n" +
                "      .f-fallback  {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "      }\n" +
                "    </style>\n" +
                "  <![endif]-->\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <span class=\"preheader\">This is a receipt for your recent purchase on {{ purchase_date }}. No payment is due with this receipt.</span>\n" +
                "    <table class=\"email-wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "      <tr>\n" +
                "        <td align=\"center\">\n" +
                "          <table class=\"email-content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "            <tr>\n" +
                "              <td class=\"email-masthead\">\n" +
                "                <a href=\"https://example.com\" class=\"f-fallback email-masthead_name\">\n" +
                "                "+ "TTB COMPUTER SHOP"+"  \n" +
                "              </a>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <!-- Email Body -->\n" +
                "            <tr>\n" +
                "              <td class=\"email-body\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <table class=\"email-body_inner\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                  <!-- Body content -->\n" +
                "                  <tr>\n" +
                "                    <td class=\"content-cell\">\n" +
                "                      <div class=\"f-fallback\">\n" +
                "                        <h1>Hi "+firstName+" "+lastName+",</h1>\n" +
                "<p>Thanks for using TTB Computer Shop. This email is the receipt for your purchase. No payment is due.</p>"+
                "               <p>This purchase will appear on your order page in our website. Need more information?  Please  check your order in button below or contact us</p>"+
                "                        <!-- Discount -->\n" +
                "                        <!-- <table class=\"discount\" align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                          <tr>\n" +
                "                            <td align=\"center\">\n" +
                "                              <h1 class=\"f-fallback discount_heading\">10% off your next purchase!</h1>\n" +
                "                              <p class=\"f-fallback discount_body\">Thanks for your support! Here's a coupon for 10% off your next purchase if used by {{expiration_date}}.</p>\n" +
                "                              <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">\n" +
                "                                <tr>\n" +
                "                                  <td align=\"center\">\n" +
                "                                    <a href=\"http://example.com\" class=\"f-fallback button button--green\" target=\"_blank\">Use this discount now...</a>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table> -->\n" +
                "                        <table class=\"purchase\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                          <tr>\n" +
                "                            <td>\n" +
                "                              <h3>"+"Order Number: "+orderId +"</h3></td>\n" +
                "                            <td>\n" +
                "                              <h3 class=\"align-right\">"+"Date: "+ngaydat+"</h3></td>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td colspan=\"2\">\n" +
                "                              <!-- <table class=\"purchase_content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                <tr>\n" +
                "                                  <th class=\"purchase_heading\" align=\"left\">\n" +
                "                                    <p class=\"f-fallback\">Description</p>\n" +
                "                                  </th>\n" +
                "                                  <th class=\"purchase_heading\" align=\"right\">\n" +
                "                                    <p class=\"f-fallback\">Amount</p>\n" +
                "                                  </th>\n" +
                "                                </tr>\n" +
                "                                {{#each receipt_details}}\n" +
                "                                <tr>\n" +
                "                                  <td width=\"80%\" class=\"purchase_item\"><span class=\"f-fallback\">{{description}}</span></td>\n" +
                "                                  <td class=\"align-right\" width=\"20%\" class=\"purchase_item\"><span class=\"f-fallback\">{{amount}}</span></td>\n" +
                "                                </tr>\n" +
                "                                {{/each}}\n" +
                "                                <tr>\n" +
                "                                  <td width=\"80%\" class=\"purchase_footer\" valign=\"middle\">\n" +
                "                                    <p class=\"f-fallback purchase_total purchase_total--label\">Total</p>\n" +
                "                                  </td>\n" +
                "                                  <td width=\"20%\" class=\"purchase_footer\" valign=\"middle\">\n" +
                "                                    <p class=\"f-fallback purchase_total\">{{total}}</p>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table> -->\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                        <p>If you have any questions about this receipt, simply reply to this email or reach out to our <a href=\"{{support_url}}\">support team</a> for help.</p>\n" +
                "                        <p>Cheers,\n" +
                "                          <br>The "+"TTB COMPUTER SHOP"+" team</p>\n" +
                "                        <!-- Action -->\n" +
                "                        <table class=\"body-action\" align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                          <tr>\n" +
                "                            <td align=\"center\">\n" +
                "                              <!-- Border based button\n" +
                "           https://litmus.com/blog/a-guide-to-bulletproof-buttons-in-email-design -->\n" +
                "                              <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">\n" +
                "                                <tr>\n" +
                "                                  <td align=\"center\">\n" +
                "                                    <a href=\"http://localhost:3000\" class=\"f-fallback button button--blue\" target=\"_blank\">View detail order</a>\n" +
                "                                  </td>\n" +
                "                                </tr>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                        <!-- Sub copy -->\n" +
                "                        <!-- <table class=\"body-sub\" role=\"presentation\">\n" +
                "                          <tr>\n" +
                "                            <td>\n" +
                "                              <p class=\"f-fallback sub\"><strong>Need a printable copy for your records?</strong> You can <a href=\"{{action_url}}\">download a PDF version</a>.</p>\n" +
                "                              <p class=\"f-fallback sub\">Moved recently? Have a new credit card? You can easily <a href=\"{{billing_url}}\">update your billing information</a>.</p>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </table> -->\n" +
                "                      </div>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td>\n" +
                "                <table class=\"email-footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                "                  <tr>\n" +
                "                    <td class=\"content-cell\" align=\"center\">\n" +
                "                      <p class=\"f-fallback sub align-center\">\n" +
                "                        [Company Name, LLC]\n" +
                "                        <br>1234 Street Rd.\n" +
                "                        <br>Suite 1234\n" +
                "                      </p>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </table>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";
        String ht="<h2>Order Successful</h1>"
                +"<h1>Email: "+ email+"</h1>"
                +"<h1>Address: "+ address+"</h1>"
                +"<h1>Phone: "+ phone+"</h1>"
                +"<h1>Order Date: "+ ngaydat+"</h1>"
                +"<h1>Total: "+ total+"</h1>";
        emailService.sendMailWithAttachment(email, "TTP SHOP ORDER", "<h1>Check attachment for image!</h1>"
                , html);
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setNgaydat(order.getNgaydat());
        dto.setStatus(order.getStatus());
        dto.setTotal_price(order.getTotal_price());
        dto.setAddress((order.getAddress()));
        dto.setPayment(order.getPayment());
        dto.setPhone(order.getPhone());
        dto.setId_user(order.getUser().getId());
        return dto;
    }

    @Override
    public Boolean deleteOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: " + orderId));
        this.orderRepository.delete(order);
        return true;
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO order) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: " + orderId));


        orderExist.setStatus(order.getStatus());
        orderExist.setTotal_price(order.getTotal_price());

        Order orderr = new Order();
        orderr = orderRepository.save(orderExist);
        String email = orderExist.getUser().getEmail();
        String name= orderExist.getUser().getFirstName();
        String address=orderExist.getAddress();
        String phone=orderExist.getPhone();
        String ngaydat=orderExist.getNgaydat().toString();
        String total=orderExist.getTotal_price().toString();
        String ht="<h1>Check attachment for image!</h1>";
        emailService.sendMailWithAttachment(email, "Hi", "<h1>Check attachment for image!</h1>"
                , ht);
        return new OrderDTO().entityToDTO(orderr);
    }

    @Override
    public List<OrderDTO> findOrderByUser(Long userId) throws ResourceNotFoundException {
        Optional<User> userExist = userRepository.findById(userId);
        if (!userExist.isPresent()) {
            throw new ResourceNotFoundException("" + "No User");
        }
        User user = userExist.get();

        List<Order> list = null;
        list = orderRepository.getOrderByUser(user);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        orderDTOS = new OrderDTO().entityToDTO(list);
        return orderDTOS;
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderDTO order) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: " + orderId));

        Order orderr = new Order();
        orderr = orderRepository.save(orderExist);
        return new OrderDTO().entityToDTO(orderr);
    }

    @Override
    public OrderDTO updateStatusOrder(Long orderId) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: " + orderId));
        String status = orderExist.getStatus();
        System.out.println(status);
        if (status.equals("Pending")) {
            orderExist.setStatus("Shipping");
        } else if (status.equals("Shipping")) {
            orderExist.setStatus("Delivered");
//            String email=orderExist.getUser().getEmail();
//            String address=orderExist.getAddress();
//            String phone=orderExist.getPhone();
//            String ht="";
//            Optional<Order> orders = orderRepository.findById(orderId);
//            if (!orders.isPresent()) {
//                throw new ResourceNotFoundException("" + ErrorCode.FIND_ORDER_ERROR);
//            }
//            Order order = orders.get();
//
//            List<DetailOrder> listOrder = null;
//            listOrder = detailRepository.findOrderDetailsByOrder(order);
//            for (DetailOrder listElement: listOrder
//                 ) {
//                ht+= "<h1>Product Name: "+listElement.getProduct().getName()+"</h1>";
//                ht+="<h1>Price: "+listElement.getDetail_price().toString()+"</h1>";
//            }
//            emailService.sendMailWithAttachment(email, "TTP SHOP ORDER", "<h1>Check attachment for image!</h1>"
//                    , ht);
        } else {
            orderExist.setStatus("Delivered");
        }

        //Product product = new Product();
        Order order = new Order();
        order = orderRepository.save(orderExist);
//        if(order.getStatus().equals("confirmed")){
//            order.getOrderDetails().forEach(detail -> {
//                if(detail.getProduct().getProduct_id() == product.getProduct_id()){
//                    product.setProductQty(product.getProductQty() - detail.getDetailQty());
//                    productrepository.save(product);
//                }
//            });
//        }
        return new OrderDTO().entityToDTO(order);
    }

//    @Override
//    public OrderDTO cancelStatusOrder(Long orderId) throws ResourceNotFoundException, UpdateDataFail {
//        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
//                new ResourceNotFoundException("order not found for this id: " + orderId));
//        String status = orderExist.getStatus();
//        if (!status.equals("Pending")) {
//            throw new UpdateDataFail("" + ErrorCode.UPDATE_ORDER_ERROR);
//        }
//        orderExist.setStatus("Canceled");
//        Order order = new Order();
//        order = orderRepository.save(orderExist);
//
//
//
//
//        Optional<Order> orderExistNew = orderRepository.findById(orderId);
//        if (!orderExistNew.isPresent()) {
//            throw new ResourceNotFoundException("" + ErrorCode.FIND_ORDER_ERROR);
//        }
//        Order orderNew = orderExistNew.get();
//
//        List<DetailOrder> list = null;
//        list = detailRepository.findOrderDetailsByOrder(order);
//        for (DetailOrder orderItem:list
//        ) {
//            Product product = productrepository.findById(orderItem.getProduct().getId()).orElseThrow(() ->
//                    new ResourceNotFoundException("product not found for this id: " + orderItem.getProduct().getId()));
//            product.setQuantity(orderItem.getDetail_qty()+product.getQuantity());
//            productrepository.save(product);
//        }
//
//
//        return new OrderDTO().entityToDTO(order);
//    }

    @Override
    public ResponseEntity<MessageResponse> cancelStatusOrder(Long orderId) throws ResourceNotFoundException, UpdateDataFail {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: " + orderId));
        String status = orderExist.getStatus();
        if (!status.equals("Pending")) {
            throw new ResourceNotFoundException("order not found for this id: " + orderId);
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

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public String restoreCancelStatus(Long orderId) throws ResourceNotFoundException {
        Optional<Order> orderExist = orderRepository.findById(orderId);
        if (!orderExist.isPresent()) {
            throw new ResourceNotFoundException("" + ErrorCode.FIND_ORDER_ERROR);
        }
        Order order = orderExist.get();

        List<DetailOrder> list = null;
        list = detailRepository.findOrderDetailsByOrder(order);
        for (DetailOrder orderItem:list
        ) {
            Product product = productrepository.findById(orderItem.getProduct().getId()).orElseThrow(() ->
                    new ResourceNotFoundException("product not found for this id: " + orderItem.getProduct().getId()));
            product.setQuantity(orderItem.getDetail_qty()+product.getQuantity());
            productrepository.save(product);
        }
        return "";
    }
}
