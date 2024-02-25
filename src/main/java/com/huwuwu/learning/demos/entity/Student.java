package com.huwuwu.learning.demos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huminghao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String gender;
    private int age;
    private double score;


    public void showInfo(){
        System.out.println(this.name+","+this.gender+","+this.age+","+this.score);
    }


}
