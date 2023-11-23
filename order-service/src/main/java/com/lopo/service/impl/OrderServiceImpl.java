package com.lopo.service.impl;

import com.lopo.clients.UserClient;
import com.lopo.domain.Order;
import com.lopo.domain.User;
import com.lopo.mapper.OrderMapper;
import com.lopo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;

    private UserClient userClient;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public Order getById(Integer orderId) {
        Order order = orderMapper.selectById(orderId);
        User user = userClient.selectUserById(order.getUserId());
        order.setUser(user);
        return order;
    }
}
