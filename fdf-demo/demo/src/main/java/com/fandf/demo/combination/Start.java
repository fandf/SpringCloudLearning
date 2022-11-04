package com.fandf.demo.combination;

import com.fandf.demo.combination.adapt.PermissionAdapter;
import com.fandf.demo.combination.decorator.Component;
import com.fandf.demo.combination.decorator.DataComponent;
import com.fandf.demo.combination.service.DataProcessingService;
import com.fandf.demo.combination.service.serviceImpl.PermissionServiceImpl;
import com.fandf.demo.combination.service.serviceImpl.RoleProcessServiceImpl;
import com.fandf.demo.combination.service.serviceImpl.StateProcessServiceImpl;

/**
 * @author fandongfeng
 * @date 2022-11-4 13:46
 */
public class Start {

    public static void main(String[] args) {

        DataProcessingService adapter = new PermissionAdapter(new PermissionServiceImpl());
        adapter.process();
//

        Component component = new DataComponent();
        component.handler(new PermissionAdapter(new PermissionServiceImpl()));
        component.handler(new RoleProcessServiceImpl());
        component.handler(new StateProcessServiceImpl());

    }

}
