package com.fandf.demo.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fandongfeng
 * @date 2022-12-29 10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String carName;
    private int age;
    private String weight;


}


