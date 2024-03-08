//package com.huwuwu.learning.jdk8;
//
//import com.huwuwu.learning.demos.entity.Student;
//import com.huwuwu.learning.demos.interfaces.Judgeable;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//
///**
// * @author huminghao
// */
//@Slf4j
//@RunWith(SpringRunner.class) // 实现Spring Boot单元测试
//@SpringBootTest // 标记Spring Boot测试，并加载应用容器
//public class NmNblTest {
//
//    //创建学生对象
//    public Student[] createStudents(){
//         return new Student[] {
//                new Student("zs","男",18,78.5),
//                new Student("ls","女",20,38.5),
//                new Student("ww","女",25,57.5),
//                new Student("ll","男",34,89.5),
//                new Student("tq","女",16,99.5),
//                new Student("wb","男",36,55.5)
//        };
//    }
//
//    @Test
//    public void Lambda(){
//        Student[] students = createStudents();
//        //lambda表达式
////        Judgeable judgeable = (s)-> {
////            return s.getAge()>20;
////        };
//        //方法体只有一条时，{}和;均可以省略,如果只有一个参数可以省略(),没有参数则不能省略()
//        Judgeable judgeable = s-> s.getAge()>20;
//        search(students,judgeable);
//        //等同于
//        search(students,s -> s.getAge()>20);
//
//    }
//
//
//    @Test
//    public void nmNblTest(){//匿名内部类
//        Student[] students = createStudents();
//        //因为内部类的类名叫什么不重要 ，所以使用匿名内部类 --》再次精简（使用Lambda表达式）
//        //创建一个接口的实现类对象Judgeable judgeable=new Judgeable() { 重写方法 }
//        search(students, new Judgeable() {
//            @Override
//            public Boolean judge(Student student) {
//                return student.getAge()>20;
//            }
//        });
//
//    }
//
//
//    @Test
//    public void nblTest(){//内部类测试
//        Student[] students = createStudents();
////        searchByAge(students);
////        searchByScore(students);
//        //通用方法--这样需要创建多个类
////        search(students, new JudgeByAge());
////        search(students,new JudgeByScore());
//
//        //这个类只是在这个方法中使用一次，则使用内部类
//        class nblByAgeTest1 implements Judgeable{
//            @Override
//            public Boolean judge(Student student) {
//                return student.getAge()>20;
//            }
//        }
//        search(students, new nblByAgeTest1());
//
//    }
//
//    //写出一个通用的可以根据任何条件进行查找的方法
//    public void search(Student[] students, Judgeable judge){//多态--Judgeable父类引用指向JudgeByAge子类对象
//        for (int i = 0; i < students.length; i++) {
//            if (judge.judge(students[i])){
//                students[i].showInfo();
//            }
//        }
//    }
//
//
//
//
//    //根据年龄查找符合条件的学生并输出
//    public void searchByAge(Student[] students){
//        for (int i = 0; i < students.length; i++) {
//            if(students[i].getAge()>20){
//                students[i].showInfo();
//            }
//        }
//    }
//
//    //根据分数查找符合条件的学生并输出
//    public void searchByScore(Student[] students) {
//        for (int i = 0; i < students.length; i++) {
//            if (students[i].getScore() > 60) {
//                students[i].showInfo();
//            }
//        }
//    }
//
//    @Test
//    public void test() throws IOException {
//        InputStream in = NmNblTest.class.getClassLoader().getResourceAsStream("application.yml");
//        Properties properties = new Properties();
//        properties.load(in);
//        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
//        for (Map.Entry<Object, Object> entry : entries) {
//            Object key = entry.getKey();
//            Object value = entry.getValue();
//            System.out.println(key);
//            System.out.println(value);
//        }
//        System.out.println("propertise集合的元素有："+properties.size());
//
//    }
//
//
//}
