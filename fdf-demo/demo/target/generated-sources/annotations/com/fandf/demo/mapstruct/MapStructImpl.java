package com.fandf.demo.mapstruct;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-26T11:49:26+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_141 (Oracle Corporation)"
)
public class MapStructImpl implements MapStruct {

    @Override
    public CarDTO carToCarDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setCarName( car.getName() );
        carDTO.setId( car.getId() );
        carDTO.setAge( car.getAge() );
        carDTO.setWeight( car.getWeight() );

        return carDTO;
    }
}
