package com.lopo.service.impl;

import com.lopo.domain.Order;
import com.lopo.domain.User;
import com.lopo.mapper.OrderMapper;
import com.lopo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    private RestTemplate restTemplate;
    private DiscoveryClient discoveryClient;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Order getById(Integer orderId) {
        Order order = orderMapper.selectById(orderId);

        List<ServiceInstance> instances = discoveryClient.getInstances("userService");
        Random random = new Random();
        ServiceInstance serviceInstance = instances.get(random.nextInt(instances.size()));
        String url = serviceInstance.getUri() +"/user/" + order.getUserId();
        User user = restTemplate.getForObject(url, User.class);
        order.setUser(user);
        return order;
    }
}
