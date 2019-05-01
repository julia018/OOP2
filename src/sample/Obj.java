package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import sample.buildings.sport_fac;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Obj {
    Object object;
    Class cl_name;

    public Class getCl_name() {
        return cl_name;
    }

    public void setCl_name(Class cl_name) {
        this.cl_name = cl_name;
    }

    private transient SimpleStringProperty name;

    public String getClass_name() {
        return class_name.get();
    }

    public void setClass_name(String class_name) {
        this.class_name.set(class_name);
    }

    private transient SimpleStringProperty class_name;

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

    public void setObject(Class<? extends sport_fac> object) {
        this.object = object;
    }


    public Obj(String name, String nameToDisplay, Object obj, Class objClass) {
        this.name = new SimpleStringProperty(name);
        class_name = new SimpleStringProperty(nameToDisplay);
        this.object = obj;
        this.cl_name = objClass;
    }

    public void deleteObject() {
        sport_fac obj = sport_fac.class.cast(getObject());
        obj.deleteObject();
        setObject(null);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeBytes(getName());
        s.writeBytes(getClass_name());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        class_name = new SimpleStringProperty(s.readLine());
        name = new SimpleStringProperty(s.readLine());
    }
}
