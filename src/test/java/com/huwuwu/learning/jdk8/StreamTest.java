package com.huwuwu.learning.jdk8;

import com.huwuwu.learning.demos.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author huminghao
 */
@Slf4j
@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
//@SpringBootTest // 标记Spring Boot测试，并加载应用容器
public class StreamTest {

    public Employee[] createEmployees(){
        Employee[] emps = new Employee[]{
                new Employee(1, "张三", "male", "市场部", 5000),
                new Employee(3, "王五", "female", "开发部", 7800),
                new Employee(4, "赵六", "male", "市场部", 3800),
                new Employee(5, "张七", "female", "市场部", 5500),
                new Employee(6, "孙八", "female", "开发部", 6000),
                new Employee(2, "李四", "male", "行政部", 6000),
                new Employee(7, "刘九", "male", "开发部", 6000),
                new Employee(8, "李十", "female", "行政部", 5800),
                new Employee(9, "黄十七", "female", "行政部", 7800),
                new Employee(10, "胡十二", "male", "开发部", 9200),
                new Employee(10, "胡十二", "male", "开发部", 9200)
        };
        return emps;
    }

    @Test
    public void StreamTest1() {
        Employee[] emps = createEmployees();
        List<Employee> employees = Arrays.asList(emps);

        //需求：显示员工中开发部工资最高的前两名的姓名
        List<String> list1 = employees.stream().filter(e -> e.getDept().equals("开发部")).sorted(Comparator.comparingDouble(Employee::getSalay).reversed()).limit(2).map(Employee::getName).collect(Collectors.toList());
        System.out.println(list1);
    }


    @Test
    public void StreamTest2() {
        //获取薪资大于9000的员工
        Employee[] emps = createEmployees();
        List<Employee> employees = Arrays.asList(emps);

        //Stream流的使用步骤：
        //第一步：获取操作流对象---调用集合对象的stream方法，获取某个集合容器的流对象
//        Stream<Employee> stream = Arrays.stream(emps);
        Stream<Employee> stream = employees.stream();

        //第二步：执行中间处理操作---调用流对象的相关方法完成对集合中数据的中间处理操作
        //中间操作后返回的是持有操作结果的一个新的流对象【流不保存数据】
        //filter sorted skip limit distinct map
        //filter 底层还是采用迭代
        Stream<Employee> stream1 = stream.filter(employee -> employee.getSalay() > 90000);

        //第三步：执行终止操作【收集或打印结果】 forEach
        stream1.forEach(System.out::println);

    }

    @Test
    public void StreamTest3(){
        Employee[] emps = createEmployees();
        List<Employee> employees = Arrays.asList(emps);

        //需求：按照工资排序排序
//        employees.stream().sorted().forEach(System.out::println);

        //需求：按照部门排序
//        employees.stream().sorted((e1,e2)->e1.getDept().compareTo(e2.getDept())).forEach(System.out::println);

        //需求：输出工资最高的前三名
//        employees.stream().sorted(Comparator.comparingDouble(Employee::getSalay).reversed()).limit(3).forEach(System.out::println);
        //排重
//        employees.stream().distinct().sorted(Comparator.comparingDouble(Employee::getSalay).reversed()).limit(3).forEach(System.out::println);

        //输出每个女员工的名字
//        employees.stream().filter(emp->emp.getGender().equals("female") && emp.getDept().equals("开发部")).map(Employee::getName).forEach(System.out::println);
//        employees.stream().filter(emp->emp.getGender().equals("female") && emp.getDept().equals("开发部")).map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);

        //获取最高工资
        //Optional 是容器
//        Optional<Integer> collect = employees.stream().filter(emp -> emp.getGender().equals("female") && emp.getDept().equals("开发部")).map(Employee::getSalay).collect(Collectors.maxBy(Integer::compareTo));
//        //从容器中获取结果
//        Integer maxSalary = collect.get();
//        System.out.println("最高工资是："+maxSalary);

        Map<String, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getDept));
        

    }

}
