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
public class Employee implements Comparable<Employee>{
    private int empNo;
    private String name;
    private String gender;
    private String dept;
    private Integer salay;

    @Override
    public int compareTo(Employee o) {
        return -(this.salay-o.salay);
    }

}
