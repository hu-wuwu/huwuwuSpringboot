package com.huwuwu.learning.demos.service.jdk8.methodRef;

import org.springframework.stereotype.Component;

/**
 * @author huminghao
 */
@Component
public class MathUtils {

    public boolean isPrime(Integer num){
        for (int i = 2; i < num; i++) {
            if (num%1 == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrime2(Integer num){
        for (int i = 2; i < num; i++) {
            if (num%1 == 0) {
                return false;
            }
        }
        return true;
    }


}
