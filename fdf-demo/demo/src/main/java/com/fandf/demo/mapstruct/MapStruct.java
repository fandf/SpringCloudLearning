package com.fandf.demo.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author fandongfeng
 * @date 2022-12-29 10:33
 */
@Mapper // 直接使用
//@Mapper(componentModel = "spring") // 整合 Spring，设置 componentModel = "spring"，需要使用的地方直接通过 @Resource 注入即可
public interface MapStruct {

    MapStruct INSTANCE = Mappers.getMapper(MapStruct.class);

    @Mapping(source = "name", target = "carName")
    CarDTO carToCarDTO(Car car);

}
