package com.jeff.game.Util;

import java.util.Random;

public class MathUtil {
    static Random random = new Random();
    public static int randomInt(int ceil) {
        return (Math.abs(random.nextInt()) % ceil);
    }
}
