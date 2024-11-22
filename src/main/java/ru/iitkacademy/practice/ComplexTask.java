package ru.iitkacademy.practice;

import java.util.UUID;

public class ComplexTask {
    private final UUID id = UUID.randomUUID();
    public long execute() {
        return mockCount();
    }
    private long mockCount(){
        long res =0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                res= res +  i + ((long) j *i);
            }
        }
        return res;
    }
}
