package eason.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
    private final Stack<Scene> sceneStack = new Stack<>();
    private School cur_school = null;
    private Restaurant cur_restaurant = null;
    private Vendor cur_vendor = null;
    private Item cur_item = null;
    private Stage primaryStage;
    private double maxwidth;
    private double maxheight;
    private final int font = 21;
    private final int textfont = 30;
    private final int sceneBoxSpace = 0;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setMaximized(true);
        primaryStage.setTitle("SYS GUI");
        Image icon = new Image(getClass().getResourceAsStream("/donut.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        maxwidth = primaryStage.getWidth();
        maxheight = primaryStage.getHeight()/30*29;

        VBox layout1 = new VBox(sceneBoxSpace);
        layout1.setAlignment(Pos.TOP_CENTER);
        layout1.setStyle("-fx-background-color: black;");
        HBox funcHBox = createfuncBox();
        HBox pathHBox = createpathBox();
        ComboBox<String> schoolBox = createBox("School", getjsonName());
        layout1.getChildren().addAll(funcHBox, pathHBox, schoolBox);
        Scene scene1 = new Scene(layout1, maxwidth, maxheight);

        sceneStack.push(scene1);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private ComboBox<String> createBox(String prompt, ArrayList<String> namelist) {
        ComboBox<String> Box = new ComboBox<>();
        Box.setStyle(String.format("-fx-font-weight:bold;;-fx-font-size:%dpx;", font));
        Box.getItems().addAll(namelist);
        Box.setPromptText("Choose " + prompt);
        Box.setOnHidden(e -> {
            String selectedname = Box.getValue();
            if (selectedname != null && !selectedname.contains("Choose")) {
                switch (prompt) {
                    case "School" -> {
                        getSchoolClass(selectedname);
                        goToRestaurant();
                    }
                    case "Restaurant" -> {
                        goToVendor(selectedname);
                    }
                    case "Vendor" -> {
                        goToMenu(selectedname);

                    }
                }
            }
        });
        return Box;
    }

    private HBox createpathBox() {
        HBox hBox = new HBox();
        hBox.setStyle(" -fx-background-color:lightyellow;-fx-padding: 10px;-fx-border-color: black;");
        hBox.setAlignment(Pos.CENTER);
        Text text = new Text("GoTo:  ");
        Text pathText = new Text();
        switch (sceneStack.size()) {
            case 0 -> {
            }
            case 1 -> {
                pathText.setText(cur_school.getName());
            }
            case 2 -> {
                pathText.setText(cur_school.getName() + "/" + cur_restaurant.getName());
            }
            case 3 -> {
                pathText.setText(
                        cur_school.getName() + "/" + cur_restaurant.getName() + "/" + cur_vendor.getName());
            }
            case 4 -> {
                pathText.setText(
                        cur_school.getName() + "/" + cur_restaurant.getName() + "/" + cur_vendor.getName());
            }
        }
        text.setStyle(String.format("-fx-font-size: %dpx;-fx-font-weight:bold;", textfont));
        pathText.setStyle(String.format("-fx-font-size: %dpx; -fx-fill:blue;-fx-font-weight:bold;", textfont));
        hBox.getChildren().addAll(text, pathText);
        return hBox;
    }

    private HBox createfuncBox() {
        HBox hBOX = new HBox();
        hBOX.setStyle("-fx-background-color: lightyellow; -fx-padding: 10px;");
        hBOX.setAlignment(Pos.CENTER);
        // back
        VBox backBox = new VBox();
        backBox.setPrefWidth(maxwidth / 3);
        backBox.setAlignment(Pos.CENTER);
        Button backbutton = new Button("Back");
        backbutton.setStyle(String.format(
                "-fx-border-color:black;-fx-border-width: 2px;;-fx-font-size:%dpx;-fx-font-weight:bold;", font));
        backbutton.setOnAction(e -> goBack());
        backBox.getChildren().add(backbutton);
        // home
        VBox homeBox = new VBox();
        homeBox.setPrefWidth(maxwidth / 3);
        homeBox.setAlignment(Pos.CENTER);
        Button homeButton = new Button("Home");
        homeButton.setStyle(String.format(
                "-fx-border-color:black;-fx-border-width: 2px;;-fx-font-size:%dpx;-fx-font-weight:bold;", font));
        homeButton.setOnAction(e -> goHome());
        homeBox.getChildren().add(homeButton);
        // random
        VBox randomBox = new VBox();
        randomBox.setPrefWidth(maxwidth / 3);
        randomBox.setAlignment(Pos.CENTER);
        Button randomButton = new Button("Random");
        randomButton.setStyle(String.format(
                "-fx-border-color:black;-fx-border-width: 2px;;-fx-font-size:%dpx;-fx-font-weight:bold;", font));
        randomButton.setOnAction(e -> {
            ArrayList<String> schoolList = getjsonName();
            Random random = new Random();
            switch (sceneStack.size()) {
                case 1 -> {
                    getSchoolClass(schoolList.get(random.nextInt(schoolList.size())));
                    cur_restaurant = cur_school.getRestaurantList()
                            .get(random.nextInt(cur_school.getRestaurantList().size()));
                    cur_vendor = cur_restaurant.getVendorList()
                            .get(random.nextInt(cur_restaurant.getVendorList().size()));
                    cur_item = cur_vendor.getItemList().get(random.nextInt(cur_vendor.getItemList().size()));
                    goToRestaurant();
                    goToVendor(cur_restaurant.getName());
                    goToMenu(cur_vendor.getName());
                }
                case 2 -> {
                    cur_restaurant = cur_school.getRestaurantList()
                            .get(random.nextInt(cur_school.getRestaurantList().size()));
                    cur_vendor = cur_restaurant.getVendorList()
                            .get(random.nextInt(cur_restaurant.getVendorList().size()));
                    cur_item = cur_vendor.getItemList().get(random.nextInt(cur_vendor.getItemList().size()));
                    goToVendor(cur_restaurant.getName());
                    goToMenu(cur_vendor.getName());
                }
                case 3 -> {
                    cur_vendor = cur_restaurant.getVendorList()
                            .get(random.nextInt(cur_restaurant.getVendorList().size()));
                    cur_item = cur_vendor.getItemList().get(random.nextInt(cur_vendor.getItemList().size()));
                    goToMenu(cur_vendor.getName());
                }
                case 4 -> {
                    goBack();
                    cur_item = cur_vendor.getItemList().get(random.nextInt(cur_vendor.getItemList().size()));
                    goToMenu(cur_vendor.getName());
                }
            }
        });
        randomBox.getChildren().add(randomButton);

        hBOX.getChildren().addAll(backBox, homeBox, randomBox);
        return hBOX;
    }

    private HBox createnoteBox(String note) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: lightyellow; -fx-padding: 10px;");
        hBox.setAlignment(Pos.CENTER_LEFT);
        Text text = new Text();
        text.setStyle(String.format("-fx-font-size: %dpx; -fx-fill:gray;-fx-font-weight:bold;", textfont));
        text.setText("Notes: " + note);
        hBox.getChildren().add(text);
        return hBox;
    }

    private ArrayList<String> getjsonName() {
        ArrayList<String> namelist = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");
        File folder = new File(currentDirectory);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    if (file.getName().contains(".json")) {
                        String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                        namelist.add(name);
                    }
                }
            }
        }
        return namelist;
    }

    private void getSchoolClass(String prompt) {
        cur_school = json_operator.readjson(prompt);
    }

    private void goToRestaurant() {
        VBox layout2 = new VBox(sceneBoxSpace);
        layout2.setAlignment(Pos.TOP_CENTER);
        layout2.setStyle("-fx-background-color: black;");
        ArrayList<String> namelist = new ArrayList<>();
        for (Restaurant r : cur_school.getRestaurantList()) {
            namelist.add(r.getName());
        }
        ComboBox<String> restaurantBox = createBox("Restaurant", namelist);
        HBox funcHBox = createfuncBox();
        HBox pathHBox = createpathBox();
        layout2.getChildren().addAll(funcHBox, pathHBox, restaurantBox);
        Scene scene2 = new Scene(layout2, maxwidth, maxheight);
        sceneStack.push(scene2);
        primaryStage.setScene(scene2);
    }

    private void goToVendor(String restaurant) {
        VBox layout3 = new VBox(sceneBoxSpace);
        layout3.setAlignment(Pos.TOP_CENTER);
        layout3.setStyle("-fx-background-color: black;");
        ArrayList<String> namelist = new ArrayList<>();
        for (Restaurant r : cur_school.getRestaurantList()) {
            if (r.getName().equals(restaurant)) {
                cur_restaurant = r;
                for (Vendor v : r.getVendorList()) {
                    namelist.add(v.getName());
                }
                break;
            }
        }
        ComboBox<String> vendorBox = createBox("Vendor", namelist);
        HBox funcHBox = createfuncBox();
        HBox pathHBox = createpathBox();
        layout3.getChildren().addAll(funcHBox, pathHBox, vendorBox);
        Scene scene3 = new Scene(layout3, maxwidth, maxheight);
        sceneStack.push(scene3);
        primaryStage.setScene(scene3);
    }

    private void goToMenu(String vendor) {
        VBox layout4 = new VBox(sceneBoxSpace);
        layout4.setStyle("-fx-background-color: black;");
        HBox funcHBox = createfuncBox();
        FlowPane menuLayout = new FlowPane();
        menuLayout.setHgap(15);
        menuLayout.setVgap(15);
        menuLayout.setPadding(new Insets(10));
        for (Vendor v : cur_restaurant.getVendorList()) {
            if (v.getName().equals(vendor)) {
                cur_vendor = v;
                for (Item i : v.getItemList()) {
                    VBox itemBox = new VBox(2);
                    if (cur_item != null && i.getName().equals(cur_item.getName())) {
                        itemBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    } else {
                        itemBox.setStyle("-fx-border-color: transparent; -fx-border-width: 2px;");
                    }
                    itemBox.setAlignment(Pos.CENTER_LEFT);
                    itemBox.setPrefWidth(450);
                    Text infoText = new Text(i.getName() + " " + String.format("%d", i.getPrice()));
                    infoText.setStyle(String.format("-fx-font-size: %dpx; -fx-fill: white;", textfont));
                    itemBox.addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> itemBox.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;"));
                    itemBox.addEventHandler(MouseEvent.MOUSE_EXITED,
                            e -> itemBox.setStyle("-fx-border-color: transparent; -fx-border-width: 2px;"));
                    itemBox.getChildren().add(infoText);
                    menuLayout.getChildren().add(itemBox);
                }
                break;
            }
        }
        HBox pathHBOX = createpathBox();
        ScrollPane scrollPane = new ScrollPane(menuLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: black; -fx-border-color: gray;");
        HBox noteHBox = createnoteBox(cur_vendor.getNote());
        layout4.getChildren().addAll(funcHBox, pathHBOX, noteHBox, scrollPane);
        Scene scene4 = new Scene(layout4, maxwidth, maxheight);
        sceneStack.push(scene4);
        primaryStage.setScene(scene4);
    }

    private void goBack() {
        if (sceneStack.size() > 1) {
            sceneStack.pop();
            Scene previousScene = sceneStack.peek();
            primaryStage.setScene(previousScene);
            cur_item = null;
        }
    }

    private void goHome() {
        while (sceneStack.size() > 1) {
            sceneStack.pop();
        }
        Scene previousScene = sceneStack.peek();
        primaryStage.setScene(previousScene);
        cur_item = null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
