package sample;

import javafx.scene.control.TextField;

public class TextFieldNew extends TextField implements Control{
    public String type;

    public TextFieldNew(String prompt, String type) {
        super(prompt);
        this.type = type;
    }

    @Override
    public String getVal() {
        return super.getText();
    }

    @Override
    public String getType() {

        return this.type;
    }



    public void setType(String type) {
        this.type = type;
    }


}
