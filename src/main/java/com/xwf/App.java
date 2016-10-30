package com.xwf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        for(int index = 0; index < 10; index++) {
            Runnable run = new Runnable() {
                public void run() {
                    long time = (long) (Math.random() * 1000);
                    System.out.println("Sleeping" + time + "ms");
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                    }
                }
            };
            exec.execute(run);
        }
// must shutdown
        System.out.println("end before");
//        exec.shutdown();
        System.out.println("end");
    }

}
