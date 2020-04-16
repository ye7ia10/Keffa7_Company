package com.example.companyk;

import java.util.ArrayList;

public class Project {
    String Pname;
    String Pdesc;
    ArrayList<String> photos = new ArrayList<>();

    Project(){}

    public Project(String pname, String pdesc, ArrayList<String> photos) {
        Pname = pname;
        Pdesc = pdesc;
        this.photos = photos;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPdesc() {
        return Pdesc;
    }

    public void setPdesc(String pdesc) {
        Pdesc = pdesc;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }
}
