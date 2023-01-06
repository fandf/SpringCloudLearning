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
//    与lombok一同使用时 pom 需增加下面插件
//    <build>
//        <plugins>
//            <plugin>
//                <groupId>org.apache.maven.plugins</groupId>
//                <artifactId>maven-compiler-plugin</artifactId>
//                <version>3.6.0</version>
//                <configuration>
//                    <source>1.8</source>
//                    <target>1.8</target>
//                    <annotationProcessorPaths>
//                        <path>
//                            <groupId>org.mapstruct</groupId>
//                            <artifactId>mapstruct-processor</artifactId>
//                            <version>1.5.3.Final</version>
//                        </path>
//                        <path>
//                            <groupId>org.projectlombok</groupId>
//                            <artifactId>lombok</artifactId>
//                            <version>1.18.24</version>
//                        </path>
//                        <path>
//                            <groupId>org.projectlombok</groupId>
//                            <artifactId>lombok-mapstruct-binding</artifactId>
//                            <version>0.2.0</version>
//                        </path>
//                    </annotationProcessorPaths>
//                </configuration>
//            </plugin>
//        </plugins>
//    </build>

    MapStruct INSTANCE = Mappers.getMapper(MapStruct.class);

    @Mapping(source = "name", target = "carName")
    CarDTO carToCarDTO(Car car);

}
