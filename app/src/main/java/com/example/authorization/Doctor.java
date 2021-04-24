package com.example.authorization;

public class Doctor {
    private String Name;
    private String proffession;

    public Doctor(String Name, String proffession){
        this.Name = Name;
        this.proffession = proffession;
    }

    public String getProffession(){
        return proffession;
    }

    public String getName() {
        return Name;
    }
}
