package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ChoiceBoxNew extends ChoiceBox implements Control{
    public String type;

    public ChoiceBoxNew(ObservableList items, String type, Object o) {
        super(items);
        super.getSelectionModel().select(o);
        this.type = type;
    }


    @Override
    public String getVal() {
        String name = super.getSelectionModel().getSelectedItem().toString();
        System.out.println(name);
        return name;
    }

    @Override
    public String getType() {
        return this.type;
    }

}
