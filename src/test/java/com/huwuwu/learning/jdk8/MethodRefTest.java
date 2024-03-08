//package com.huwuwu.learning.jdk8;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Comparator;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.function.Supplier;
//
///**
// * @author huminghao
// */
//@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
////@SpringBootTest // 标记Spring Boot测试，并加载应用容器
//public class MethodRefTest {
//
//    @Test
//    public void isPrime(){
//        //判断一个数是否为质数
//        Predicate<Integer> predicate = num -> {
//            for (int i = 2; i < num; i++) {
//                if (num%1 == 0)
//                    return false;
//            }
//            return true;
//        };
//
//        //使用这个断言
//        boolean flag = predicate.test(101);
//        System.out.println("这个数是否是质数："+flag);
//
//        //上面的lambda体很复杂
//        //如果一个Lambda体的逻辑非常复杂建议使用方法引用
//        //1.对象名::方法名 针对非静态方法
//        //2.类名::方法名 引用类的静态方法
//        //2.1 我们还可以引用JDK的方法
//        //3.类名::非静态方法【特殊的情况】
//        //4.构造器引用 ： 类名::new
//    }
//
//    @Test
//    public void methodRefTest1(){
//        //1.对象名::方法名 针对非静态方法
//        //注：方法名后面不要添加(),这是方法的引用不是调用
//        Predicate<Integer> predicate = new MathUtils()::isPrime;
//
//        boolean flag = predicate.test(33);
//        System.out.println("这个数是否是质数："+flag);
//    }
//
//    @Test
//    public void methodRefTest2(){
//        //2.类名::方法名 引用类的静态方法
//        Predicate<Integer> predicate = MathUtils::isPrime2;
//
//        boolean flag = predicate.test(33);
//        System.out.println("这个数是否是质数："+flag);
//
//        //2.1 我们还可以引用JDK的方法，例如：println()
//        //println这个方法时有参无返回，所以函数式接口要用消费型Consumer
////        PrintStream out = System.out;//这是一个对象
//        Consumer<String> consumer = System.out::println;
//        consumer.accept("hello stream!!");
//    }
//
//
//    @Test
//    public void methodRefTest3(){
//        Employee[] emps = new Employee[]{
//                new Employee(1,"张三","male","市场部",5000),
//                new Employee(3,"王五","female","开发部",7800)
//        };
//        //正常情况
////        Comparator<Integer> preCompar = (n1,n2) -> n1.compareTo(n2);
////        preCompar.compare(10,20);
//        //3.类名::非静态方法【特殊的情况】-->第一个参数要作为方法调用者（也就是第一个参数要是方法的对象）
//        //第一个参数作为compareTo方法的调用者，第二个参数作为compareTo方法的参数
//        Comparator<Integer> comparator = Integer::compareTo;//不符合接口，但是满足上一行的条件就可以
//        int compare = comparator.compare(emps[0].getSalay(), emps[1].getSalay());
//        System.out.println("比较薪资(大于0或者小于0,等于则是0，-1，1)："+compare);
//
//    }
//    @Test
//    public void methodRefTest3_1(){
//        Employee[] emps = new Employee[]{
//                new Employee(1,"张三","male","市场部",5000),
//                new Employee(3,"王五","female","开发部",7800)
//        };
//
//        //例2
//        //不使用方法的引用
////        Function<Employee,String> preFunction = emp -> emp.getName();
////        System.out.println(preFunction.apply(emps[1]));
//        //使用应用  类名::非静态方法
////        Supplier<String> supplier = new Employee()::getName;//符合规范
//        //参数作为getName方法的调用者，没有第二个参数，getName方法也没有参数，和上面的案例一样
//        Function<Employee,String> function = Employee::getName;//不符合规范
//        System.out.println(function.apply(emps[1]));
//
//    }
//
//    @Test
//    public void methodRefTest4(){
//        Employee[] emps = new Employee[]{
//                new Employee(1,"张三","male","市场部",5000),
//                new Employee(3,"王五","female","开发部",7800)
//        };
//        //不使用方法的引用
//        Supplier<Employee> preSupplier = () ->new Employee();
//        //4.构造器引用 ： 类名::new
//        //构造器 是无参又返回，返回的是对象
//        Supplier<Employee> supplier = Employee::new;
//        //创建Employee对象
//        Employee employee = supplier.get();
//
//    }
//
//
//}
