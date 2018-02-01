package com.dozenx.common.collection.stack;

/**
 * Created by dozen.zhang on 2017/12/16.
 */
public class ScoreItem<T> {
    Long score;
    T object;
    public ScoreItem(T object, long score){
        this.object = object;
        this.score = score;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
