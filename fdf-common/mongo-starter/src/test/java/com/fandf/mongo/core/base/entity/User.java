package com.fandf.mongo.core.base.entity;

import com.fandf.mongo.core.SimpleEntity;
import com.fandf.mongo.core.annotations.Embed;
import com.fandf.mongo.core.annotations.EmbedList;
import com.fandf.mongo.core.annotations.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/11/19 14:17
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends SimpleEntity {

    private static final long serialVersionUID = -2729542583843567774L;
    private String username;
    private int age;
    private boolean valid;
    private Date registerTime;
    @Embed
    private Contact contact;
    @EmbedList
    private List<Address> addressList;
    private Map<String, List<Integer>> permissions;
    private Float[] scores;


    @Data
    @Builder
    public static class Contact {
        private String phone;
        private String email;

        @Tolerate
        public Contact() {
        }
    }

    @Data
    @Builder
    public static class Address {
        private String province;
        private String city;

        @Tolerate
        public Address() {
        }
    }
}
