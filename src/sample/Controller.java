package sample;

import com.sun.org.apache.bcel.internal.generic.TargetLostException;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import sample.buildings.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;



public class Controller {
    HashMap<String, String> types_map;
    private ObservableList<Obj> obj_list = FXCollections.observableArrayList();
    private final String PACK = "sample.buildings.";

    @FXML
    private ComboBox<String> cmb_class;

    @FXML
    private Button create_btn;
    @FXML
    private TableView<Obj> obj_table;

    @FXML
    private TableColumn<Obj, String> col_obj;

    @FXML
    private TableColumn<Obj, String> col_class;

    @FXML
    private TableColumn<Obj, Void> col_actions;




    @FXML
    void initialize() {
        col_obj.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_class.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        col_actions.setCellFactory(param -> new TableCell<Obj, Void>() {
            private final Button viewbutton = new Button("view");
            private final Button editButton = new Button("edit");
            private final Button deleteButton = new Button("delete");
            private final HBox pane = new HBox(viewbutton, editButton, deleteButton);

            {
                pane.setSpacing(20);
                deleteButton.setOnAction(event -> {
                    Obj del_obj = getTableView().getItems().get(getIndex());
                    del_obj.setObject(null);
                    obj_list.remove(del_obj);
                    System.out.println("Del");
                });

                editButton.setOnAction(event -> {
                    try {
                        Object my_obj = getTableView().getItems().get(getIndex()).getObject();

                        System.out.println(my_obj.getClass().toString());
                        Class<? extends sport_fac> clazz = (Class<? extends sport_fac>) Class.forName(getTableView().getItems().get(getIndex()).getCl_name());
                        System.out.println(clazz.toString());

                        ArrayList<Field> result = new ArrayList<Field>();
                        //Collections.addAll(result, clazz.getDeclaredFields());
                        //get all fields, render
                        Class<?> i = clazz;
                        while (i != null && i != Object.class) {
                            Collections.addAll(result, i.getDeclaredFields());
                            i = i.getSuperclass();
                        }
                        Collections.reverse(result);


                        VBox vb = new VBox();
                        vb.setSpacing(5);
                        vb.setPadding(new Insets(10,20, 10,10));

                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Create");
                        stage.sizeToScene();
                        //create controls
                        Button btn_OK = new Button("OK");
                        btn_OK.setDisable(false);
                        String name = "Безымянный";

                        List<Control> rlt = new ArrayList<>();
                        for(Field f : result){
                            //Текстовое поле
                            if (f.getType() == String.class) {
                                if(f.isAnnotationPresent(RusName.class)) {
                                    System.out.println(f.getName());
                                    System.out.println(clazz.toString());

                                    Method get_f = clazz.getMethod("get"+f.getName());
                                    String res = (String)get_f.invoke(my_obj);
                                    System.out.println("Попытка уст txt = "+ res);
                                    name = f.getAnnotation(RusName.class).r_name();
                                    Label lbl_text = new Label(name);
                                    vb.getChildren().add(lbl_text);
                                    TextFieldNew txt = new TextFieldNew(res, name);
                                    rlt.add(txt);
                                    vb.getChildren().add(txt);
                                }
                            }
                            //Целочисленное поле
                            if (f.getType() == int.class) {
                                name = f.getAnnotation(RusName.class).r_name();
                                Label lbl_num = new Label(name);
                                vb.getChildren().add(lbl_num);
                                System.out.println("Попытка уст int = ");
                                Method get_f = clazz.getMethod("get"+f.getName());
                                int res = (int)get_f.invoke(my_obj);
                                name = f.getAnnotation(RusName.class).r_name();
                                TextFieldNew txt = new TextFieldNew(Integer.toString(res), name);
                                txt.textProperty().addListener((observable, oldValue, newValue) -> {
                                    try {
                                        int x = Integer.parseInt(newValue);
                                    } catch (NumberFormatException e) {
                                        txt.setText(oldValue);
                                    }
                                });
                                rlt.add(txt);
                                vb.getChildren().add(txt);

                            }
                            //Вещественное поле
                            if (f.getType() == float.class) {
                                name = f.getAnnotation(RusName.class).r_name();
                                Label lbl_num = new Label(name);
                                vb.getChildren().add(lbl_num);
                                System.out.println("Попытка уст float = ");
                                Method get_f = clazz.getMethod("get"+f.getName());
                                float res = (float)get_f.invoke(my_obj);

                                TextFieldNew txt = new TextFieldNew(Float.toString(res), name);
                                txt.textProperty().addListener((observable, oldValue, newValue) -> {
                                    try {
                                        float x = Float.parseFloat(newValue);
                                    } catch (NumberFormatException e) {
                                        txt.setText(oldValue);
                                    }
                                });
                                rlt.add(txt);
                                vb.getChildren().add(txt);
                            }
                            //Перечисление(enum)
                            if (f.getType().isEnum()) {
                                if(f.isAnnotationPresent(RusName.class)) {
                                    name = f.getAnnotation(RusName.class).r_name();
                                    Label lbl_num = new Label(name);
                                    vb.getChildren().add(lbl_num);
                                }
                                System.out.println("Попытка уст enum = ");
                                // full list of constants of enum
                                Method get_f = clazz.getMethod("get"+f.getName());
                                String res = (String)get_f.invoke(my_obj);

                                ArrayList<Field> en_flds = new ArrayList<>();
                                Collections.addAll(en_flds, f.getType().getFields());
                                // list of fields-constants declared annotations
                                ArrayList<String> arr = new ArrayList<>();
                                Object il = null;
                                for(Field fld : en_flds){
                                    arr.add(fld.getAnnotation(RusName.class).r_name());
                                    if (fld.getName().equals(res)) il = fld.getAnnotation(RusName.class).r_name();
                                }

                                ChoiceBoxNew chBox = new ChoiceBoxNew(FXCollections.observableArrayList(arr),name, il);
                                rlt.add(chBox);
                                vb.getChildren().add(chBox);
                            }
                            //Объект(композиция)
                            if (f.getType().getSuperclass() == Composition.class) {
                                Separator sp1 = new Separator(Orientation.HORIZONTAL);
                                vb.getChildren().add(sp1);
                                ArrayList<Field> comp_fields = new ArrayList<>();
                                Collections.addAll(comp_fields, f.getType().getDeclaredFields());
                                System.out.println(comp_fields);
                                name = f.getAnnotation(RusName.class).r_name();
                                LabelNew lbl_num = new LabelNew(name, f.getType(), f.getName());
                                HBox hb = new HBox();
                                hb.setAlignment(Pos.CENTER);
                                hb.getChildren().add(lbl_num);
                                vb.getChildren().add(hb);

                                //get object
                                Class<?> comp_cl = f.getType();
                                System.out.println("? object in comp for "+ clazz.toString() + f.getName());
                                Method get_comp = clazz.getMethod("get"+f.getName());//?? YET
                                Object res = get_comp.invoke(my_obj);//get comp obj-> res
                                if (res != null) System.out.println("Here is  object in comp");
                                comp_cl.cast(res);
                                System.out.println(comp_fields);
                                for (Field fl : comp_fields) {
                                    //Текстовое поле
                                    if (fl.getType() == String.class) {
                                        System.out.println("Попытка уст str = ");
                                        if (fl.isAnnotationPresent(RusName.class)) {

                                            Method get_f = comp_cl.getMethod("get"+fl.getName());
                                            String resultt = (String)get_f.invoke(res);
                                            System.out.println("Поле обкта в композиции " + fl.getName() + "= "+ resultt);
                                            name = fl.getAnnotation(RusName.class).r_name();
                                            Label lbl_text = new Label(name);
                                            vb.getChildren().add(lbl_text);
                                            TextFieldNew txt = new TextFieldNew(resultt, name);
                                            rlt.add(txt);
                                            vb.getChildren().add(txt);
                                        }
                                    }
                                    //Целочисленное поле
                                    if (fl.getType() == int.class) {
                                        System.out.println("Попытка уст int = ");
                                        name = fl.getAnnotation(RusName.class).r_name();
                                        Label lbl_n = new Label(name);
                                        vb.getChildren().add(lbl_n);
                                        Method get_f = comp_cl.getMethod("get"+fl.getName());
                                        int rest = (int)get_f.invoke(res);
                                        TextFieldNew txt = new TextFieldNew(Integer.toString(rest), name);
                                        txt.textProperty().addListener((observable, oldValue, newValue) -> {
                                            try {
                                                int z = Integer.parseInt(newValue);
                                            } catch (NumberFormatException e) {
                                                txt.setText(oldValue);
                                            }
                                        });
                                        rlt.add(txt);
                                        vb.getChildren().add(txt);

                                    }
                                    //Вещественное поле
                                    if (fl.getType() == float.class) {
                                        System.out.println("Попытка уст float = ");
                                        name = fl.getAnnotation(RusName.class).r_name();
                                        Label lbl_n = new Label(name);
                                        vb.getChildren().add(lbl_n);
                                        Method get_f = comp_cl.getMethod("get"+fl.getName());
                                        float rest = (float)get_f.invoke(res);
                                        TextFieldNew txt = new TextFieldNew(Float.toString(rest), name);
                                        txt.textProperty().addListener((observable, oldValue, newValue) -> {
                                            try {
                                                float x = Float.parseFloat(newValue);
                                            } catch (NumberFormatException e) {
                                                txt.setText(oldValue);
                                            }
                                        });
                                        rlt.add(txt);
                                        vb.getChildren().add(txt);
                                    }
                                    //Перечисление(enum)
                                    if (fl.getType().isEnum()) {
                                        if (fl.isAnnotationPresent(RusName.class)) {
                                            name = fl.getAnnotation(RusName.class).r_name();
                                            Label lbl_numm = new Label(name);
                                            vb.getChildren().add(lbl_numm);
                                        }
                                        // full list of constants of enum
                                        Method get_f = comp_cl.getMethod("get"+fl.getName());
                                        String resl = (String)get_f.invoke(res);

                                        ArrayList<Field> en_flds = new ArrayList<>();
                                        Collections.addAll(en_flds, fl.getType().getFields());

                                        System.out.println("Попытка уст ch_b = ");

                                        // list of fields-constants declared annotations
                                        ArrayList<String> arr = new ArrayList<>();
                                        Object ill = null;
                                        for (Field fld : en_flds) {
                                            arr.add(fld.getAnnotation(RusName.class).r_name());
                                            if (fld.getName().equals(resl)) ill = fld.getAnnotation(RusName.class).r_name();
                                        }
                                        ChoiceBoxNew chBox = new ChoiceBoxNew(FXCollections.observableArrayList(arr), name, ill);

                                        rlt.add(chBox);
                                        vb.getChildren().add(chBox);
                                    }

                                    //Выбор(логическое поле)
                                    if ((fl.getType() == Boolean.class)) {
                                        if (fl.isAnnotationPresent(RusName.class)) {
                                            System.out.println("Попытка уст bool = ");
                                            name = fl.getAnnotation(RusName.class).r_name();
                                            Method get_f = comp_cl.getMethod("get"+fl.getName());
                                            Boolean rest = (Boolean) get_f.invoke(res);
                                            CheckBoxNew ch_b = new CheckBoxNew(name, name, rest.booleanValue());
                                            vb.getChildren().add(ch_b);
                                            rlt.add(ch_b);
                                        }

                                    }
                                }
                                Separator sp2 = new Separator(Orientation.HORIZONTAL);
                                vb.getChildren().add(sp2);
                                rlt.add(lbl_num);

                            }
                            //Выбор(логическое поле)
                            if ((f.getType() == Boolean.class)) {
                                if(f.isAnnotationPresent(RusName.class)) {
                                    name = f.getAnnotation(RusName.class).r_name();
                                    System.out.println(clazz.toString());
                                    Method get_f = clazz.getMethod("get"+f.getName());
                                    System.out.println("Попытка уст bool = ");
                                    Boolean res =  (Boolean) get_f.invoke(my_obj);
                                    if (res == null) {
                                        System.out.println("not value for bool ");
                                    }
                                    CheckBoxNew ch_b = new CheckBoxNew(name, name, res.booleanValue());
                                    rlt.add(ch_b);
                                    vb.getChildren().add(ch_b);
                                }

                            }
                            System.out.println(f);
                        }

                        btn_OK.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                String obj_name = "";
                                Constructor<?> constructor = null;
                                try {
                                    System.out.println(clazz.toString());
                                    ArrayList<Method> res = new ArrayList<>();
                                    //constructor = clazz.getConstructor();
                                    try {
                                        //Object instance = constructor.newInstance(); //here give data from controls
                                        //clazz.cast(instance);
                                        if (my_obj == null) System.out.println("Нет гл объекта");
                                        clazz.cast(my_obj);
                                        System.out.println("Создан inst " + my_obj.getClass().toString());
                                        //clazz.cast(instance);
                                        Class<?> p = clazz;
                                        while (p != null && p != Object.class) {
                                            Collections.addAll(res, p.getDeclaredMethods());
                                            p = p.getSuperclass();
                                        }
                                        for (Control r : rlt) {
                                            if(r.getType().equals("Название")) obj_name = (String)r.getVal();
                                            System.out.println(r.getClass().toString());
                                            if(r.getClass() == LabelNew.class) { // composition
                                                Method get_cl = r.getClass().getDeclaredMethod("getCl");
                                                Class clazzz = (Class) get_cl.invoke(r);
                                                constructor = clazzz.getConstructor();
                                                Object inst = constructor.newInstance();
                                                //Class comp_obj = (Class) get_comp.invoke(my_obj);
                                                clazzz.cast(inst);
                                                if(inst == null) System.out.println("cant create obj");
                                                //инициализ-ть объект внутренний
                                                ArrayList<Method> resul = new ArrayList<>();
                                                Collections.addAll(resul, clazzz.getDeclaredMethods());
                                                for (Method t : resul) {
                                                    if (t.isAnnotationPresent(RusName.class)) {
                                                        for (Control s : rlt) {
                                                            System.out.println(r.getClass().toString());
                                                            if (t.getAnnotation(RusName.class).r_name().equals("Уст" + s.getType())) {
                                                                t.invoke(inst, s.getVal());
                                                            }
                                                        }
                                                    }
                                                }
                                                Method set_obj = my_obj.getClass().getDeclaredMethod("set"+r.getType(), new Class[]{clazzz});
                                                set_obj.invoke(my_obj, inst);
                                                System.out.println("Устанавливаем комп-цию");
                                                Method get_obj = my_obj.getClass().getDeclaredMethod("get"+r.getType());
                                                if(get_obj.invoke(my_obj) != null) System.out.println("+ ref");
                                            }
                                        }
                                        for (Method m : res) {
                                            if (m.isAnnotationPresent(RusName.class)) {
                                                for (Control r : rlt) {

                                                    if (m.getAnnotation(RusName.class).r_name().equals("Уст" + r.getType())) {
                                                        System.out.println("Попытка уст "+r.getType());
                                                        System.out.println(r.getVal());
                                                        m.invoke(my_obj, r.getVal());
                                                        //r.setVal("Yes");
                                                    }
                                                }
                                            }
                                        }
                                        obj_list.remove(getTableView().getItems().get(getIndex()));
                                        obj_list.add(new Obj(obj_name, my_obj.getClass().getAnnotation(RusName.class).r_name(),my_obj, my_obj.getClass().toString()));
                                        stage.close();
                                        //obj_list.add(new Obj(obj_name, instance.getClass().getAnnotation(RusName.class).r_name(),instance, instance.getClass().toString()));

                                    } catch (ClassCastException el) {
                                        System.out.println("Can't cast!");
                                    }

                                    //System.out.println(instance.getClass().toString());
                                } catch (NoSuchMethodException|InstantiationException|IllegalAccessException|InvocationTargetException l) {
                                    l.printStackTrace();
                                }
                            }

                        });
                        HBox hb = new HBox();
                        hb.setAlignment(Pos.CENTER);
                        hb.getChildren().add(btn_OK);
                        vb.getChildren().add(hb);

                        stage.setScene(new Scene(vb, 400,700));



                        //
                        stage.show();


                    } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException| InvocationTargetException e) {
                        e.printStackTrace();
                    }

                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        obj_list.addListener(new ListChangeListener<Obj>() {
            @Override
            public void onChanged(Change<? extends Obj> c) {
                obj_table.setItems(obj_list);
            }

        });
        /*obj_list.addListener((ListChangeListener.Change<? extends Obj> change) -> {
            obj_table.setItems(obj_list);
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Add");
                }
                if (change.wasUpdated()) {
                    System.out.println("Update");
                }
            }
        });*/

        types_map = new HashMap<>();
        types_map.put("Лазерный тир", "laser_shoot");
        types_map.put("Пневманический тир", "pnum_shoot");
        //types_map.put("Теннисный корт", "laser_shoot");
        //types_map.put("Бассейн", "laser_shoot");
        //types_map.put("Легкоатлетический стадион", "laser_shoot");
        types_map.put("Футбольный стадион", "football_stadium");
        //ObservableList<Class<? extends sport_fac>> types = FXCollections.observableArrayList(laser_shoot.class);
        //ObservableList<Class<? extends spo>> types = FXCollections.observableArrayList();
        cmb_class.setItems(FXCollections.observableArrayList(types_map.keySet()));
        /*Iterator<String> it = types_map.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            cmb_class.setValue(key);
        }
        //cmb_class.setValue();
        //HashMap<Class, String> classes = new HashMap<>();
        //classes.put(laser_shoot.class, "Лазерный тир");
        //cmb_class.setItems(classes);*/
        //private ObservableList<Person> personData = FXCollections.observableArrayList();
    }

     /*class types_converter extends StringConverter<> {

        @Override
        public String toString(Class<? extends sport_fac> myClassinstance) {
            // convert a myClass instance to the text displayed in the choice box

            return myClassinstance.getRus_name();
        }



        @Override
        public Class<? extends sport_fac> fromString(String string) {
            return null;
        }

    }*/

    @FXML
    void create(ActionEvent event) {
        String selected_class = Func(types_map, cmb_class.getValue());
        System.out.println(selected_class);
        cmb_class.setValue(null);
        try {
            Class clazz =  Class.forName(PACK + selected_class);
            //System.out.println(clazz.toString());
            ArrayList<Field> result = new ArrayList<Field>();
            //Collections.addAll(result, clazz.getDeclaredFields());
            //get all fields, render
            Class<?> i = clazz;
            while (i != null && i != Object.class) {
                Collections.addAll(result, i.getDeclaredFields());
                i = i.getSuperclass();
            }
            Collections.reverse(result);


            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.setPadding(new Insets(10,20, 10,10));

            Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Create");
                stage.sizeToScene();
            //create controls
            Button btn_OK = new Button("OK");
            //btn_OK.setDisable(false);
            String name = "Безымянный";

            List<Control> rlt = new ArrayList<>();
            for(Field f : result){
                //Текстовое поле
                if (f.getType() == String.class) {
                    if(f.isAnnotationPresent(RusName.class)) {
                        name = f.getAnnotation(RusName.class).r_name();
                        Label lbl_text = new Label(name);
                        vb.getChildren().add(lbl_text);
                        TextFieldNew txt = new TextFieldNew("", name);
                        rlt.add(txt);
                        vb.getChildren().add(txt);
                    }
                }
                //Целочисленное поле
                if (f.getType() == int.class) {
                    name = f.getAnnotation(RusName.class).r_name();
                    Label lbl_num = new Label(name);
                    vb.getChildren().add(lbl_num);
                    TextFieldNew txt = new TextFieldNew("0", name);
                    txt.textProperty().addListener((observable, oldValue, newValue) -> {
                            try {
                                int x = Integer.parseInt(newValue);
                            } catch (NumberFormatException e) {
                                txt.setText(oldValue);
                            }
                    });
                    rlt.add(txt);
                    vb.getChildren().add(txt);

                }
                //Вещественное поле
                if (f.getType() == float.class) {
                    name = f.getAnnotation(RusName.class).r_name();
                    Label lbl_num = new Label(name);
                    vb.getChildren().add(lbl_num);
                    TextFieldNew txt = new TextFieldNew("0.0", name);
                    txt.textProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            float x = Float.parseFloat(newValue);
                        } catch (NumberFormatException e) {
                            txt.setText(oldValue);
                        }
                    });
                    rlt.add(txt);
                    vb.getChildren().add(txt);
                }
                //Перечисление(enum)
                if (f.getType().isEnum()) {
                    System.out.println(f.getType().getEnumConstants().toString());
                    if(f.isAnnotationPresent(RusName.class)) {
                        name = f.getAnnotation(RusName.class).r_name();
                        Label lbl_num = new Label(name);
                        vb.getChildren().add(lbl_num);
                    }
                    // full list of constants of enum
                    ArrayList<Field> en_flds = new ArrayList<>();
                    Collections.addAll(en_flds, f.getType().getFields());


                    // list of fields-constants declared annotations
                    ArrayList<String> arr = new ArrayList<>();
                    for(Field fld : en_flds){
                        arr.add(fld.getAnnotation(RusName.class).r_name());

                    }
                    ChoiceBoxNew chBox = new ChoiceBoxNew(FXCollections.observableArrayList(arr),name, null);

                    rlt.add(chBox);
                    vb.getChildren().add(chBox);
                }
                //Объект(композиция)
                if (f.getType().getSuperclass() == Composition.class) {
                    System.out.println("type "+f.getType());
                    Separator sp1 = new Separator(Orientation.HORIZONTAL);
                    vb.getChildren().add(sp1);
                    ArrayList<Field> comp_fields = new ArrayList<>();
                    Collections.addAll(comp_fields, f.getType().getDeclaredFields());
                    name = f.getAnnotation(RusName.class).r_name();
                    LabelNew lbl_num = new LabelNew(name, f.getType(), f.getName());
                    HBox hb = new HBox();
                    hb.setAlignment(Pos.CENTER);
                    hb.getChildren().add(lbl_num);
                    vb.getChildren().add(hb);

                    for (Field fl : comp_fields) {
                        //Текстовое поле
                        if (fl.getType() == String.class) {
                            if (fl.isAnnotationPresent(RusName.class)) {
                                name = fl.getAnnotation(RusName.class).r_name();
                                Label lbl_text = new Label(name);
                                vb.getChildren().add(lbl_text);
                                TextFieldNew txt = new TextFieldNew("", name);
                                rlt.add(txt);
                                vb.getChildren().add(txt);
                            }
                        }
                        //Целочисленное поле
                        if (fl.getType() == int.class) {
                            name = fl.getAnnotation(RusName.class).r_name();
                            Label lbl_n = new Label(name);
                            vb.getChildren().add(lbl_n);
                            TextFieldNew txt = new TextFieldNew("0", name);
                            txt.textProperty().addListener((observable, oldValue, newValue) -> {
                                try {
                                    int z = Integer.parseInt(newValue);
                                } catch (NumberFormatException e) {
                                    txt.setText(oldValue);
                                }
                            });
                            rlt.add(txt);
                            vb.getChildren().add(txt);

                        }
                        //Вещественное поле
                        if (fl.getType() == float.class) {
                            name = fl.getAnnotation(RusName.class).r_name();
                            Label lbl_n = new Label(name);
                            vb.getChildren().add(lbl_n);
                            TextFieldNew txt = new TextFieldNew("0.0", name);
                            txt.textProperty().addListener((observable, oldValue, newValue) -> {
                                try {
                                    float x = Float.parseFloat(newValue);
                                } catch (NumberFormatException e) {
                                    txt.setText(oldValue);
                                }
                            });
                            rlt.add(txt);
                            vb.getChildren().add(txt);
                        }
                        //Перечисление(enum)
                        if (fl.getType().isEnum()) {
                            if (fl.isAnnotationPresent(RusName.class)) {
                                name = fl.getAnnotation(RusName.class).r_name();
                                Label lbl_numm = new Label(name);
                                vb.getChildren().add(lbl_numm);
                            }
                            // full list of constants of enum
                            ArrayList<Field> en_flds = new ArrayList<>();
                            Collections.addAll(en_flds, fl.getType().getFields());



                            // list of fields-constants declared annotations
                            ArrayList<String> arr = new ArrayList<>();
                            for (Field fld : en_flds) {
                                arr.add(fld.getAnnotation(RusName.class).r_name());
                            }
                            ChoiceBoxNew chBox = new ChoiceBoxNew(FXCollections.observableArrayList(arr), name, null);

                            rlt.add(chBox);
                            vb.getChildren().add(chBox);
                        }

                        //Выбор(логическое поле)
                        if ((fl.getType() == Boolean.class)) {
                            if (fl.isAnnotationPresent(RusName.class)) {
                                name = fl.getAnnotation(RusName.class).r_name();
                                CheckBoxNew ch_b = new CheckBoxNew(name, name,false);
                                vb.getChildren().add(ch_b);
                                rlt.add(ch_b);
                            }

                        }
                    }
                        Separator sp2 = new Separator(Orientation.HORIZONTAL);
                        vb.getChildren().add(sp2);
                        rlt.add(lbl_num);

                }
                //Выбор(логическое поле)
                if ((f.getType() == Boolean.class)) {
                    if(f.isAnnotationPresent(RusName.class)) {
                        name = f.getAnnotation(RusName.class).r_name();
                        CheckBoxNew ch_b = new CheckBoxNew(name, name, false);
                        vb.getChildren().add(ch_b);
                        rlt.add(ch_b);
                    }

                }
                System.out.println(f);
            }

            btn_OK.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    String obj_name = "";
                    Constructor<?> constructor = null;
                    try {
                        System.out.println(clazz.toString());
                        ArrayList<Method> res = new ArrayList<>();
                        constructor = clazz.getConstructor();
                        try {
                        Object instance = constructor.newInstance(); //here give data from controls
                        clazz.cast(instance);
                        System.out.println("Создан inst " + instance.getClass().toString());
                        //clazz.cast(instance);
                        Class<?> p = clazz;
                        while (p != null && p != Object.class) {
                            Collections.addAll(res, p.getDeclaredMethods());
                            p = p.getSuperclass();
                        }
                        for (Control r : rlt) {
                            if(r.getType().equals("Название")) obj_name = (String)r.getVal();
                            System.out.println(r.getClass().toString());
                            if(r.getClass() == LabelNew.class) { // composition
                                    Method get_cl = r.getClass().getDeclaredMethod("getCl");
                                    Class clazzz = (Class) get_cl.invoke(r);
                                    constructor = clazzz.getConstructor();
                                    Object inst = constructor.newInstance();
                                    clazzz.cast(inst);
                                    if(inst == null) System.out.println("cant create obj");
                                    //инициализ-ть объект внутренний
                                    ArrayList<Method> resul = new ArrayList<>();
                                    Collections.addAll(resul, clazzz.getDeclaredMethods());
                                    for (Method t : resul) {
                                        if (t.isAnnotationPresent(RusName.class)) {
                                            for (Control s : rlt) {
                                                System.out.println(r.getClass().toString());
                                                if (t.getAnnotation(RusName.class).r_name().equals("Уст" + s.getType())) {
                                                    t.invoke(inst, s.getVal());
                                                }
                                            }
                                        }
                                    }
                                Method set_obj = instance.getClass().getDeclaredMethod("set"+r.getType(), new Class[]{clazzz});
                                set_obj.invoke(instance, inst);
                                Method get_obj = instance.getClass().getDeclaredMethod("get"+r.getType());
                                if(get_obj.invoke(instance) != null) System.out.println("+ ref");
                                }
                        }
                        for (Method m : res) {
                            if (m.isAnnotationPresent(RusName.class)) {
                                for (Control r : rlt) {

                                    if (m.getAnnotation(RusName.class).r_name().equals("Уст" + r.getType())) {
                                        System.out.println("Попытка уст "+r.getType());
                                        System.out.println(r.getVal());
                                        m.invoke(instance, r.getVal());
                                        //r.setVal("Yes");
                                    }
                                }
                            }
                        }
                    obj_list.add(new Obj(obj_name, instance.getClass().getAnnotation(RusName.class).r_name(),instance, instance.getClass().toString()));
                    stage.close();
                        } catch (ClassCastException el) {
                            System.out.println("Can't cast!");
                        }

                        //System.out.println(instance.getClass().toString());
                    } catch (NoSuchMethodException|InstantiationException|IllegalAccessException|InvocationTargetException l) {
                        l.printStackTrace();
                    }
                }
            });
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().add(btn_OK);
            vb.getChildren().add(hb);

                stage.setScene(new Scene(vb, 400,700));




                stage.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Searching in HM
    String Func(HashMap<String, String> HM, String K) {
        for (Map.Entry entry : HM.entrySet()) {
            if (entry.getKey().equals(K)) {
                return entry.getValue().toString();
            }
        }
        return "";
    }




}
