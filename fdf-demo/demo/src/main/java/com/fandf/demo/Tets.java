package com.fandf.demo;

import com.fandf.demo.mapstruct.Car;
import com.fandf.demo.mapstruct.CarDTO;
import com.fandf.demo.mapstruct.MapStruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author fandongfeng
 * @date 2022/11/13 17:05
 */
@RestController
@RequestMapping("test1")
public class Tets {

    private Integer a;

    @PostConstruct
    public void init() {
        a = 1;
    }

    @GetMapping
    public Integer get() {
        Car car = Car.of(123L, "张三", 12, "173");
        CarDTO carDTO = MapStruct.INSTANCE.carToCarDTO(car);
        System.out.println(carDTO);
        return a;
    }

    public static void main(String[] args) {
        Car car = Car.of(123L, "张三", 12, "173");
        CarDTO carDTO = MapStruct.INSTANCE.carToCarDTO(car);
        System.out.println(carDTO);
    }

}
