package com.fandf.demo.propertyeditor;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * 这是JDK中提供的类型转化工具类
 *
 * @author fandongfeng
 */
public class StringToPersonPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Person person = new Person();
        String[] split = text.split(",");
        person.setId(Long.valueOf(split[0]));
        person.setName(split[1]);
        person.setAge(Integer.valueOf(split[2]));
        this.setValue(person);
    }

    public static void main(String[] args) {
        StringToPersonPropertyEditor propertyEditor = new StringToPersonPropertyEditor();
        propertyEditor.setAsText("1,张三,15");
        Person value = (Person) propertyEditor.getValue();
        System.out.println(value);
    }
}
