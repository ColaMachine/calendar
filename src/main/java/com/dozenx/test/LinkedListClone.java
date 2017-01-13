package com.dozenx.test;

import java.util.LinkedList;

/**
 * Created by dozen.zhang on 2017/1/13.
 */
public class LinkedListClone {

    public void testCloneEqual(){
        LinkedList a =new LinkedList();
        PoolThread aThread = new PoolThread("a");
        a.add(aThread);
        aThread.start();

        System.out.println("begin clone:"+System.currentTimeMillis());
        LinkedList b=(LinkedList)a.clone();
        System.out.println("end clone:"+System.currentTimeMillis());
        a.add( new PoolThread("a"));
        System.out.println(a.equals(b));
    }
    public static void main(String args[]){

        LinkedListClone test =new LinkedListClone();
        test.testCloneEqual();

    }
    class PoolThread extends Thread{
        PoolThread(String s){
            this.name=s;
        }
        private  String name ;
        @Override
        public void run() {

            for(int i=0;i<100;i++){
                System.out.println(name+":"+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
