package com.example.authorization;

public class Doctor extends Person{
    private String name;
    private String proffession;

    public Doctor(String Name, String proffession){
        this.name = Name;
        this.proffession = proffession;
    }

    public String getProffession(){
        return proffession;
    }

    @Override
    public String getName() {
        return name;
    }
}