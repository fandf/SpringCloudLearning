package com.fandf.test.cpu;

/**
 * @author fandongfeng
 */
public class CpuTest {

    public int compute(){
        int a = 11;
        int b = 22;
        return a*b;
    }

    public static void main(String[] args) {
        CpuTest test = new CpuTest();
        while (true) {
            test.compute();
        }
    }


}
