# nooc-openfeign
这是一个nacos+openfeign学习项目

记录学习openfeign的笔记代码


添加openfeign依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

添加loadbalancer依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```
定义服务调用的接口
```java
@FeignClient(value = "userService")
public interface UserClient {
    @GetMapping("/user/{userId}")
    User selectUserById(@PathVariable("userId") Long userId);
}
```

调用远程服务
```java
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
```

使用代理池处理http请求，导入依赖
```xml
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-httpclient</artifactId>
</dependency>
```

配置openfeign代理池为apache httpclient
```yaml
feign:
  httpclient:
    enabled: true
```