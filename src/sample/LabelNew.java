package sample;

import javafx.scene.control.Label;

public class LabelNew extends Label implements Control {
    private Class  cl;
    private Composition obj;
    private String type;

    public Composition getObj() {
        return obj;
    }

    public void setObj(Composition obj) {
        this.obj = obj;
    }



    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }



    public LabelNew(String text, Class cl, String t) {
        super(text);
        this.cl = cl;
        this.obj= null;
        this.type = t;
    }

    @Override
    public Object getVal() {
        return this.obj;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
