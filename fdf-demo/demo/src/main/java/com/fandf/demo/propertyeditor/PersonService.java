package com.fandf.demo.propertyeditor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fandongfeng
 */
@Service
public class PersonService {

    @Value("${test.person}")
    private Person person;

    public void test() {
        System.out.println(person);
    }


}
