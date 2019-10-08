package com.easysui.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @since 2019/7/16
 **/
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class Student extends SuperStudent {
    private Long id;
    private String name;
    private int age;

    public static void main(String[] args) {
        Student student = (Student) Student.of()
                .setName("")
                .setAge(12)
                .setWord("")
                .setCountry("China");
        System.out.println(student);
    }
}