package com.dozenx.common.collection.stack;

import java.util.LinkedList;

/**
 * Created by dozen.zhang on 2017/12/16.
 */
public class ScoreList<T> {
    //整个队列是以从小到大排序的 小的在前面
    LinkedList<ScoreItem<T>> list = new LinkedList<>();


    //5分钟3次
    //24小时 20次
    //
    public int shortTime ;
    public int shortTimes;
    public int dayTimes;



    public int minStep;//每次塞入的时间间隔

    /**
     * 塞入数据 如果超过多少个 就会报错
     * 如果请求间隔时间 小于指定时间 就会报错
     * 如果 指定时间内
     * @param object
     * @throws SoFastException
     */
    public void put(T object,long score ) throws SoFastException {
       //long nowTime  = System.currentTimeMillis();
        if(!list.isEmpty()){// 判断是否操作时间小于时间间隔
            ScoreItem<T> sect = list.getLast();
            if(sect.getScore()-score < minStep){
                throw new SoFastException();
            }
        }
        list.offer(new ScoreItem<T>(object,System.currentTimeMillis()/1000));
    }
    public ScoreItem<T> getLast(){
        return list.getLast();
    }
    public int countByScore(long min,long max){
        int count =0;
        for(int i=0,length = list.size();i<length;i++){
            ScoreItem scoreItem = list.get(i);
            if(scoreItem.getScore()>=min&& scoreItem.getScore() <=max){
                count++;
            }
        }
        return count;
    }



    /**
     * 将过期某个时间段的数据清理掉
     * @param min
     * @param max
     * @return
     */

    public void remScoreByRange(long min,long max ){
        for(int i= list.size()-1;i>=0;i--){
            ScoreItem item = list.get(i);
            if(item.getScore()>min & item.getScore() < max ){
                list.remove(i);
            }
        }
    }



}
