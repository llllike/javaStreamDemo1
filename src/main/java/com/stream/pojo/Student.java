package com.stream.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.function.IntFunction;

/**
 * @author yzy
 * @create 2022-12-13-12:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student implements Serializable {
    private Integer id;
    private String name;
    private Boolean sex;
    private Integer age;
}
