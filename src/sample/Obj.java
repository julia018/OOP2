package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import sample.buildings.sport_fac;

public class Obj {
    Object object;

    public String getCl_name() {
        return cl_name;
    }

    public void setCl_name(String cl_name) {
        this.cl_name = cl_name;
    }

    String cl_name;
    private final SimpleStringProperty name;

    public String getClass_name() {
        return class_name.get();
    }

    public void setClass_name(String class_name) {
        this.class_name.set(class_name);
    }

    private final SimpleStringProperty class_name;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }


    public SimpleStringProperty class_nameProperty() {
        return class_name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }





    public Obj(String nameee, String cl, Object obct, String cl_n) {
        this.name = new SimpleStringProperty(nameee);
        class_name = new SimpleStringProperty(cl);
        //but_edit = new SimpleObjectProperty(new Button("Edit"));
        //but_delete = new SimpleObjectProperty(new Button("Delete"));
        this.object = obct;
        this.cl_name = cl_n;
        this.cl_name = this.cl_name.replace("class ","");
    }





}
