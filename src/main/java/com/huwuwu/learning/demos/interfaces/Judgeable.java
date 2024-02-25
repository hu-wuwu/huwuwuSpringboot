package com.huwuwu.learning.demos.interfaces;

import com.huwuwu.learning.demos.entity.Student;

/**
 * @author huminghao
 */
@FunctionalInterface
public interface Judgeable {

    Boolean judge(Student student);
}
