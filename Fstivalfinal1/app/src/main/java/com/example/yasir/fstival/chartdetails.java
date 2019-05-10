package com.example.yasir.fstival;

import java.io.Serializable;

/**
 * Created by yasir on 8/9/17.
 */

public class chartdetails implements Serializable{
    private String mlikes;
    private String m1best;
    private String m1good;
    private String m1average;

    private String m2best;
    private String m2good;
    private String m2average;

    private String m3best;
    private String m3good;
    private String m3average;

    private String m4best;
    private String m4good;
    private String m4average;


    chartdetails(String likes,String best,String good,String average,String best1,String good1,String average1
            ,String best2,String good2,String average2,String best3,String good3,String average3){
        mlikes = likes;
        m1best = best;
        m1good = good;
        m1average = average;

        m2best = best1;
        m2good = good1;
        m2average = average1;

        m3best = best2;
        m3good = good2;
        m3average = average2;

        m4best = best3;
        m4good = good3;
        m4average = average3;
    }

    public  String getlikes(){
        return mlikes;
    }

    public  String get1best(){
        return m1best;
    }

    public  String get1good(){
        return  m1good;
    }

    public  String get1average(){
        return m1average;
    }

    public  String get2best(){
        return m2best;
    }

    public  String get2good(){
        return  m2good;
    }

    public  String get2average(){
        return m2average;
    }

    public  String get3best(){
        return m3best;
    }

    public  String get3good(){
        return  m3good;
    }

    public  String get3average(){
        return m3average;
    }

    public  String get4best(){
        return m4best;
    }

    public  String get4good(){
        return  m4good;
    }

    public  String get4average(){
        return m4average;
    }
}
