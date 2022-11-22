package com.fandf.mongo.core.base;

import com.fandf.mongo.core.base.dao.UserDao;
import com.fandf.mongo.core.base.entity.User;
import org.junit.Test;

import java.util.*;

/**
 * @author fandongfeng
 * @date 2022/11/19 14:13
 */
public class CurdTest extends BasicTest{

    @Test
    public void testInsert() {
        connectDB();
        User user = getUser();
        UserDao userDao = new UserDao();
        userDao.insert(user);
        User result = userDao.query().eq("contact.phone", "110").result();
        System.out.println(result);
        disconnectDB();
    }

    private User getUser() {
        User user = new User();
        user.setUsername("李四");
        user.setAge(20);
        user.setRegisterTime(new Date());
        user.setValid(true);
        Float[] floats = new Float[1];
        floats[0] = 1.1f;
        user.setScores(floats);
        user.setContact(User.Contact.builder().phone("110").email("110@google.com").build());
        user.setAddressList(Collections.singletonList(User.Address.builder().province("陕西").city("西安").build()));
        HashMap<String, List<Integer>> map = new HashMap<>(8);
        map.put("首页", Arrays.asList(1, 2, 3));
        map.put("设置", Arrays.asList(11, 22, 33));
        user.setPermissions(map);
        return user;
    }


    @Test
    public void testQuery() {
        connectDB();
        UserDao userDao = new UserDao();
        User result = userDao.query().eq("username", "张三").result();
        System.out.println(result);
        User one = userDao.findOne();
        System.out.println(one);
        one = userDao.findOne("age", 20);
        System.out.println(one);

        one = userDao.findOne("6378cdae7788d337b277c351");
        System.out.println(one);

        one = userDao.findOneReturnFields("6378cdae7788d337b277c351", new String[]{"age", "username"});
        System.out.println("findOneReturnFields:"+ one);

        List<User> all = userDao.findAll();
        System.out.println("findAll :"+ all);


        userDao.update().set("username", "haha...").inc("age", 1).execute(one);
        disconnectDB();
    }

}
