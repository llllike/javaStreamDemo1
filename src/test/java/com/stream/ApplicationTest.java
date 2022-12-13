package com.stream;

import com.stream.pojo.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class ApplicationTest {
    public static List<Student> students=new ArrayList<>();
    @BeforeAll
    public static void init(){
        students.add(new Student(1,"张三",true,19));
        students.add(new Student(2,"LiHua",false,30));
        students.add(new Student(3,"王五",false,45));
        students.add(new Student(4,"李四",true,10));
        students.add(new Student(5,"老六",false,10));
    }
    @Test
    public void stream(){
        // 创建出一个新的stream串行流对象
        Stream<Student> stream = students.stream();
        Stream<Student> stream1 = Arrays.stream(students.toArray(new Student[0]));
    }
    @Test
    public void parallelStream(){
        // 创建出一个可并行执行的stream流对象
        Stream<Student> studentStream = students.parallelStream();
    }
    @Test
    public void StreamOf(){
        // 通过给定的一系列元素创建一个新的Stream串行流对象
        Stream<Integer> integerStream = Stream.of(10, 20, 30, 40);
    }
    @Test
    public void filter(){
        // 按照条件过滤符合要求的元素， 返回新的stream流
        students.stream().filter(o -> o.getAge() > 20).forEach(System.out::println);
    }
    @Test
    public void map(){
        // 将已有元素转换为另一个对象类型，一对一逻辑，返回新的stream流
        students.stream().map(Student::getName).forEach(System.out::println);
    }
    @Test
    public void flatMap(){
        // 将已有元素转换为另一个对象类型，一对多逻辑，即原来一个元素对象可能会转换为1个或者多个新类型的元素，返回新的stream流
        students.stream().flatMap(o-> Arrays.stream(new char[][]{o.getName().toCharArray()})).forEach(System.out::println);
    }
    @Test
    public void limit(){
        // 仅保留集合前面指定个数的元素，返回新的stream流
        students.stream().filter(o->o.getAge()>20).limit(1).forEach(System.out::println);
    }
    @Test
    public void skip(){
        // 跳过集合前面指定个数的元素，返回新的stream流
        students.stream().skip(2).forEach(System.out::println);
    }
    @Test
    public void concat(){
        // 将两个流的数据合并起来为1个新的流，返回新的stream流
        List<Student> students1 = students.stream().filter(student -> student.getSex()).collect(Collectors.toList());
        List<Student> students2 = students.stream().filter(student -> student.getAge() > 20).collect(Collectors.toList());
        Stream.concat(students1.stream(),students2.stream()).forEach(System.out::println);
    }
    @Test
    public void distinct(){
        // 对Stream中所有元素进行去重，返回新的stream流
        students.stream().map(Student::getAge).distinct().forEach(System.out::println);
    }
    @Test
    public void sorted(){
        // 对stream中所有的元素按照指定规则进行排序，返回新的stream流
        students.stream().sorted(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge()-o2.getAge();
            }
        }).forEach(System.out::println);
    }
    @Test
    public void peek(){
        // 对stream流中的每个元素进行逐个遍历处理，返回处理后的stream流
        long count = students.stream().peek(student -> System.out.println(student.getName())).count();
        System.out.println(count);
    }
    @Test
    public void count(){
        // 返回stream处理后最终的元素个数
        long count = students.stream().count();
        System.out.println(count);
    }
    @Test
    public void max(){
        // 返回stream处理后的元素最大值
        Optional<Integer> max = students.stream().map(Student::getAge).max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(max.get());
    }
    @Test
    public void min(){
        // 返回stream处理后的元素最大值
        Optional<Integer> min = students.stream().map(Student::getAge).min(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(min.get());
    }
    @Test
    public void findFirst(){
        // 找到第一个符合条件的元素时则终止流处理
        Optional<Student> first = students.stream().findFirst();
        System.out.println(first.get());
    }
    @Test
    public void anyMatch(){
        // 返回一个boolean值，类似于isContains(),用于判断是否有符合条件的元素
        boolean b = students.stream().anyMatch(student -> student.getAge() > 30);
        System.out.println(b);
    }
    @Test
    public void allMatch(){
        // 返回一个boolean值，用于判断是否所有元素都符合条件
        boolean b = students.stream().allMatch(student -> !student.getSex());
        System.out.println(b);
    }
    @Test
    public void noneMatch(){
        // 返回一个boolean值， 用于判断是否所有元素都不符合条件
        boolean b = students.stream().noneMatch(student -> student.getAge() < 9);
        System.out.println(b);
    }
    @Test
    public void collect(){
        // 将流转换为指定的类型，通过Collectors进行指定
        List<Student> collect = students.stream().collect(Collectors.toList());
        collect.forEach(System.out::println);
        Map<Integer, String> collectMap = students.stream().collect(Collectors.toMap(Student::getId, Student::getName));
        collectMap.forEach((k,v)-> System.out.println(k+":"+v));
    }
    @Test
    public void toArray(){
        // 将流转换为数组
        Student[] students1 =students.stream().toArray(Student[]::new);
        Arrays.stream(students1).forEach(System.out::println);
    }
}
