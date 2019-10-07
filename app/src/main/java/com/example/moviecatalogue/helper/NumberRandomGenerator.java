package com.example.moviecatalogue.helper;

import java.util.Random;

public class NumberRandomGenerator {

    public static int Generate(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}
