package com.lopo.clients;


import com.lopo.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userService")
public interface UserClient {
    @GetMapping("/user/{userId}")
    User selectUserById(@PathVariable("userId") Long userId);
}
