package com.example.yasir.fstival;

/**
 * Created by giri on 20/8/17.
 */

public class Feedback {

    private String name,sch_name,description,stallname;
    private int logo,like,delete,comment,id;

    public Feedback(String name,String sch_name,String description,String stallname){
        this.name=name;
        this.sch_name=sch_name;
        this.description=description;
        this.stallname=stallname;
    }

    public String getName(){
        return name;
    }

    public String getTimestamp(){
        return sch_name;
    }

    public String getDescription(){
        return description;
    }

    public String getStallname(){
        return stallname;
    }

}
