package com.huwuwu.learning.demos.service.jdk8.nbl;

import com.huwuwu.learning.demos.entity.Student;
import com.huwuwu.learning.demos.interfaces.Judgeable;
import org.springframework.stereotype.Service;

/**
 * @author huminghao
 */
@Service
public class JudgeByAge implements Judgeable {

    @Override
    public Boolean judge(Student student) {
        return student.getAge()>20;
    }
}
