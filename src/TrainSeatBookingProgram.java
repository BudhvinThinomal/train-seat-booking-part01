import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bson.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TrainSeatBookingProgram extends Application {
    private static final int SEATING_CAPACITY = 42;         //create static variable for seat capacity


    @Override
    public void start(Stage primaryStage) throws Exception {
        HashMap<String, HashMap> tripOne = new HashMap<String, HashMap>(); //create Hashmap to store trip one data
        HashMap<String, HashMap> tripTwo = new HashMap<String, HashMap>(); //create Hashmap to store trip two data

        Scanner scan = new Scanner(System.in);              //Train menu options
        while (true) {
            System.out.println("----------------------------------------------Denuwara Menike Train seat booking program!!-----------------------------------------------------\n");
            System.out.println("Note: If you load data please remind to store data before quit the program!!\n      Otherwise data'll be lost!!\n");
            System.out.println("Enter \"A\" to add a customer ");
            System.out.println("Enter \"V\" to view all the seat ");
            System.out.println("Enter \"E\" to view empty seats ");
            System.out.println("Enter \"D\" to delete a booked seats ");
            System.out.println("Enter \"F\" to find a seat by customer name ");
            System.out.println("Enter \"O\" to view seat ordered alphabetically by name ");
            System.out.println("Enter \"S\" to store Data ");
            System.out.println("Enter \"L\" to load Data");
            System.out.println("Enter \"Q\" to quit ");
            System.out.print("Please enter the given command : ");
            String option = scan.next();

            switch (option) {                        //Switch case for options
                case "A":
                case "a":
                    System.out.println("Enter number\"A\" for Colombo to Badulla or Enter number \"B\" for Badulla to Colombo");
                    String choiceA = scan.next();
                    switch (choiceA){
                        case "A":
                        case "a":
                            addCust(tripOne);           //calling to the method
                            break;
                        case "B":
                        case "b":
                            addCust(tripTwo);            //calling to the method
                            break;
                        default:
                            System.out.println("Given Command is invalid!!");
                    }
                    break;
                case "V":
                case "v":
                    System.out.println("Enter number\"A\" for Colombo to Badulla or Enter number \"B\" for Badulla to Colombo");
                    String choiceV = scan.next();
                    switch (choiceV) {
                        case "A":
                        case "a":
                            allSeats(tripOne);           //calling to the method
                            break;
                        case "B":
                        case "b":
                            allSeats(tripTwo);           //calling to the method
                            break;
                        default:
                            System.out.println("Given Command is invalid!!");
                    }
                    break;
                case "E":
                case "e":
                    System.out.println("Enter number\"A\" for Colombo to Badulla or Enter number \"B\" for Badulla to Colombo");
                    String choiceE = scan.next();
                    switch (choiceE) {
                        case "A":
                        case "a":
                            emptySeats(tripOne);         //calling to the method
                            break;
                        case "B":
                        case "b":
                            emptySeats(tripTwo);         //calling to the method
                            break;
                        default:
                            System.out.println("Given Command is invalid!!");
                    }
                    break;
                case "D":
                case "d":
                    System.out.println("Enter number\"A\" for Colombo to Badulla or Enter number \"B\" for Badulla to Colombo");
                    String choiceD = scan.next();
                    switch (choiceD) {
                        case "A":
                        case "a":
                            deleteSeats(tripOne);        //calling to the method
                            break;
                        case "B":
                        case "b":
                            deleteSeats(tripTwo);        //calling to the method
                            break;
                        default:
                            System.out.println("Given Command is invalid!!");
                    }
                    break;
                case "F":
                case "f":
                    System.out.println("Enter number\"A\" for Colombo to Badulla or Enter number \"B\" for Badulla to Colombo");
                    String choiceF = scan.next();
                    switch (choiceF) {
                        case "A":
                        case "a":
                            findSeats(tripOne);          //calling to the method
                            break;
                        case "B":
                        case "b":
                            findSeats2(tripTwo);         //calling to the method
                            break;
                        default:
                            System.out.println("Given Command is invalid!!");
                    }
                    break;
                case "O":
                case "o":
                    System.out.println("Enter number\"A\" for Colombo to Badulla or Enter number \"B\" for Badulla to Colombo");
                    String choiceO = scan.next();
                    switch (choiceO) {
                        case "A":
                        case "a":
                            alphabetically(tripOne);         //calling to the method
                            break;
                        case "B":
                        case "b":
                            alphabetically(tripTwo);         //calling to the method
                            break;
                        default:
                            System.out.println("Given Command is invalid!!");
                    }
                    break;
                case "S":
                case "s":
                    storeData(tripOne);              //calling to the methods
                    storeData2(tripTwo);
                    break;
                case "L":
                case "l":
                    loadData(tripOne);               //calling to the methods
                    loadData2(tripTwo);
                    break;
                case "Q":
                case "q":
                    System.out.println("Thank you!!");
                    System.exit(0);
                default:
                    System.out.println("Please enter the given command!!");
            }
        }
    }

    private void loadData2(HashMap<String, HashMap> tripTwo) {                 //defined method to load tripOne data from mongodb
        try {
            MongoClient mongoClient= MongoClients.create();                     //connect with the mongodb
            MongoDatabase mongoDatabase= mongoClient.getDatabase("seatBooking");
            MongoCollection<Document>collection=mongoDatabase.getCollection("newTripTwo");

            HashMap<String, Integer> passengerMap = new HashMap<String, Integer>();             //create inner Hashmap to store trip two data
            String enteredDate = null;
            String previousDate;
            List<Document> names = (List<Document>) collection.find().into(new ArrayList<Document>()); //gathered data from database
            for (Document a : names) {
                List<Document> date = (List<Document>) a.get("DataBaseDate");           //get data to list
                List<Document> details = (List<Document>) a.get("DataBaseString");
                List<Document> integer = (List<Document>) a.get("DataBaseInteger");

                for (int i = 0; i < date.size(); i++) {
                    for (int x = 0; x < date.size(); x++) {             //assign values to variables
                        enteredDate = String.valueOf(date.get(i));
                        String eneteredDetails = String.valueOf(details.get(i));
                        String enteredInteger = String.valueOf(integer.get(i));
                        Integer finalInteger = Integer.parseInt(enteredInteger);

                        previousDate = String.valueOf(date.get(x));
                        if (tripTwo.get(enteredDate) != null) {         //check the entered date was create in previous
                            passengerMap = tripTwo.get(enteredDate);
                            passengerMap.put(eneteredDetails, finalInteger);    //put values to the inner Hash map
                        }else if (tripTwo.get(enteredDate) != tripTwo.get(previousDate)) {
                            tripTwo.put(enteredDate, new HashMap() {{    //created new key
                                put(eneteredDetails, finalInteger);     //put values to the inner Hash map
                            }});
                        }
                    }
                }
                final HashMap addSeat = passengerMap;
                tripTwo.put(enteredDate, addSeat);
            }
            System.out.println("Data load successfully!!");
        } catch (Exception e) {
            System.out.println("Operation is unsuccessful...Error - " + e);
        }
    }

    private void loadData(HashMap<String, HashMap> tripOne) {                 //defined method to load tripOne data from mongodb
        try {
            MongoClient mongoClient= MongoClients.create();                     //connect with the mongodb
            MongoDatabase mongoDatabase= mongoClient.getDatabase("seatBooking");
            MongoCollection<Document>collection=mongoDatabase.getCollection("newTripOne");

            HashMap<String, Integer> passengerMap = new HashMap<String, Integer>();
            String enteredDate = null;
            String previousDate;
            List<Document> names = (List<Document>) collection.find().into(new ArrayList<Document>()); //gathered data from database
            for (Document a : names) {
                List<Document> date = (List<Document>) a.get("DataBaseDate");           //get data to list
                List<Document> details = (List<Document>) a.get("DataBaseString");
                List<Document> integer = (List<Document>) a.get("DataBaseInteger");

                for (int i = 0; i < date.size(); i++) {
                    for (int x = 0; x < date.size(); x++) {
                        enteredDate = String.valueOf(date.get(i));          //assign values to variables
                        String eneteredDetails = String.valueOf(details.get(i));
                        String enteredInteger = String.valueOf(integer.get(i));
                        Integer finalInteger = Integer.parseInt(enteredInteger);

                        previousDate = String.valueOf(date.get(x));
                        if (tripOne.get(enteredDate) != null) {         //check the entered date was create in previous
                            passengerMap = tripOne.get(enteredDate);
                            passengerMap.put(eneteredDetails, finalInteger);        //put values to the inner Hash map
                        }else if (tripOne.get(enteredDate) != tripOne.get(previousDate)) {
                            tripOne.put(enteredDate, new HashMap() {{       //created new key
                                put(eneteredDetails, finalInteger);         //put values to the inner Hash map
                            }});
                        }
                    }
                }
                final HashMap addSeat = passengerMap;
                tripOne.put(enteredDate, addSeat);
            }
        } catch (Exception e) {
            System.out.println("Operation is unsuccessful...Error - " + e);
        }
    }
    private void storeData2(HashMap<String,HashMap> tripTwo) {            //defined method to store data to mongodb
        try {
            MongoCollection seatDb1;                 //create mongoCollection database 01
            MongoClient mongoClient = MongoClients.create();                 //connect with the mongodb
            MongoDatabase mongoDatabase = mongoClient.getDatabase("seatBooking");
            seatDb1 = mongoDatabase.getCollection("newTripTwo");

            Document document = new Document();
            String store = "DataBaseString";                      //defined key value to store string
            String storeInt = "DataBaseInteger";                  //defined key value to store integer
            String storeDate = "DataBaseDate";
            ArrayList<String> output = new ArrayList<String>();        //defined arraylists
            ArrayList<Integer> outputInt = new ArrayList<Integer>();
            ArrayList<String> outputDate = new ArrayList<String>();

            for (Object date : tripTwo.keySet()) {              //put values to the lists
                for (Object details : tripTwo.get(date).keySet()) {
                    outputDate.add((String) date);
                    output.add((String) details);
                    outputInt.add((Integer) tripTwo.get(date).get(details));

                    document.append(storeDate, outputDate);     //append to the document
                    document.append(store, output);             //append to the document
                    document.append(storeInt, outputInt);       //append to the document
                }
            }
            seatDb1.insertOne(document);
            System.out.println("Data stored successfully!!");
        }catch (Exception e){
            System.out.println("Operation is unsuccessful...Error - " + e);
        }
    }

    private void storeData(HashMap<String,HashMap> tripOne) {            //defined method to store data to mongodb
        try{
            MongoCollection seatDb1;                 //create mongoCollection database 01
            MongoClient mongoClient= MongoClients.create();                 //connect with the mongodb
            MongoDatabase mongoDatabase= mongoClient.getDatabase("seatBooking");
            seatDb1=mongoDatabase.getCollection("newTripOne");

            Document document= new Document();
            String store="DataBaseString";                      //defined key value to store string
            String storeInt="DataBaseInteger";                  //defined key value to store integer
            String storeDate="DataBaseDate";
            ArrayList<String> output=new ArrayList<String>();        //defined arraylists
            ArrayList<Integer>outputInt=new ArrayList<Integer>();
            ArrayList<String> outputDate=new ArrayList<String>();

            for (Object date : tripOne.keySet()) {                  //put values to the lists
                for (Object details : tripOne.get(date).keySet()) {
                    outputDate.add((String) date);
                    output.add((String) details);
                    outputInt.add((Integer)tripOne.get(date).get(details));

                    document.append(storeDate,outputDate);      //append to the document
                    document.append(store,output);              //append to the document
                    document.append(storeInt,outputInt);        //append to the document
                }
            }
            seatDb1.insertOne(document);
        }catch (Exception e){
            System.out.println("Operation is unsuccessful...Error - " + e);
        }
    }

    private void alphabetically(HashMap<String,HashMap> passenger) {   //defined method to get names by alphabetical order from Hash Map
        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();              //take date as a input

        HashMap passengerMap = new HashMap<String, Integer>();
        if (passenger.get(selectedDate) != null) {      //check the entered date was create in previous
            passengerMap = passenger.get(selectedDate);
        }
        final HashMap alphSeat = passengerMap;

        ArrayList<String> names=new ArrayList<String>();            //defined array list
        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button btn = new Button("Seat " + i);
            btn.setId(Integer.toString(i));

            if (alphSeat.containsValue(Integer.parseInt(btn.getId()))){
                for (Object a : alphSeat.keySet()) {           //add key values to name list
                    names.add((String) a);
                }break;
            }
        }
        for (int x=0; x<names.size()-1; x++)                                  // bubble sort outer loop
        {
            for (int j=0; j < names.size()-x-1; j++) {                     //bubble sort inner loop
                if (names.get(j).compareTo(names.get(j+1))>0)
                {
                    String temporary = names.get(j);
                    names.set(j,names.get(j+1) );
                    names.set(j+1, temporary);
                }
            }
        }
        for(int n=0;n<names.size();n++){
            for (int i = 1; i <= SEATING_CAPACITY; i++) {
                Button btn = new Button("Seat " + i);
                btn.setId(Integer.toString(i));
                //display the name and seat number which have key and value
                if ((alphSeat.containsKey(names.get(n))) && (alphSeat.containsValue(Integer.parseInt(btn.getId())))) {
                    System.out.println(names.get(n) + " : seat No." + alphSeat.get(names.get(n)));
                    break;
                }
            }
        }
    }

    private void findSeats2(HashMap<String,HashMap> tripTwo) { //defined method to find seat number from tripTwo Hash map
        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();              //take date as a input

        HashMap passengerMap = new HashMap<String, Integer>();
        if (tripTwo.get(selectedDate) != null) {            //check the entered date was create in previous
            passengerMap = tripTwo.get(selectedDate);
        } final HashMap fSeat = passengerMap;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your first name : ");
        String fName=scan.next().toUpperCase();         //get string input & turn the string value to uppercase
        System.out.println("Enter your surname : ");
        String sName=scan.next().toUpperCase();         //get string input & turn the string value to uppercase
        String findCust=fName+" "+sName;

        for (int i = 1; i <= SEATING_CAPACITY; i++) {            //create buttons id using for loop
            Button btn = new Button("Seat " + i);
            btn.setId(Integer.toString(i));
            //display the name and seat number which have key and value also display date
            if ((fSeat.containsKey(findCust)) &&(fSeat.containsValue(Integer.parseInt(btn.getId())))){
                System.out.println(findCust + " reserved seat No." +fSeat.get(findCust)+" From Badulla to Colombo"+" on "+selectedDate);
                break;
            }else if(i==42&&((fSeat.containsKey(findCust)) &&(fSeat.containsValue(Integer.parseInt(btn.getId()))))==false){
                System.out.println("There's no reservation for "+findCust);
            }
        }
    }
    private void findSeats(HashMap<String,HashMap> tripOne) {      //defined method to find seat number from tripOne Hashmap
        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();              //take date as a input

        HashMap passengerMap = new HashMap<String, Integer>();
        if (tripOne.get(selectedDate) != null) {            //check the entered date was create in previous
            passengerMap = tripOne.get(selectedDate);
        }
        final HashMap fSeat = passengerMap;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your first name : ");
        String fName=scan.next().toUpperCase();          //get string input & turn the string value to uppercase
        System.out.println("Enter your surname : ");
        String sName=scan.next().toUpperCase();     //get string input & turn the string value to uppercase
        String findCust=fName+" "+sName;

        for (int i = 1; i <= SEATING_CAPACITY; i++) {            //create buttons id using for loop
            Button btn = new Button("Seat " + i);
            btn.setId(Integer.toString(i));
            //display the name and seat number which have key and value
            if ((fSeat.containsKey(findCust)) &&(fSeat.containsValue(Integer.parseInt(btn.getId())))){
                System.out.println(findCust + " reserved seat No." +fSeat.get(findCust)+" From Colombo to Badulla "+" on "+selectedDate);
                break;
            }else if(i==42&&((fSeat.containsKey(findCust)) &&(fSeat.containsValue(Integer.parseInt(btn.getId()))))==false){
                System.out.println("There's no reservation for "+findCust);
            }
        }
    }

    private void deleteSeats(HashMap<String,HashMap> passenger) {  //defined method to delete key & value from Hashmap

        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();          //take date as a input

        HashMap passengerMap = new HashMap<String, Integer>();
        if (passenger.get(selectedDate) != null) {          //check the entered date was create in previous
            passengerMap = passenger.get(selectedDate);
        }
        final HashMap dSeat = passengerMap;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your first name : ");
        String fName=scan.next().toUpperCase();        //get string input & turn the string value to uppercase
        System.out.println("Enter your surname : ");
        String sName=scan.next().toUpperCase();       //get string input & turn the string value to uppercase
        String cust=fName+" "+sName;
        if(dSeat.containsKey(cust)){         //check the input belongs to the Hash  map
            dSeat.remove(cust);             //remove the key & value
            System.out.println(cust+"'s reservation is cancelled!!");
        } else{
            System.out.println("There's no reservation for " + cust);
        }
    }

    private void emptySeats(HashMap<String,HashMap> passenger) {    //defined method to show empty seat from Hash map
        Stage primaryStage =new Stage();
        primaryStage.setTitle("Train seat booking program");
        FlowPane flowPane = fPane();                //calling method to get flow pane

        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();          //take date as a input

        HashMap passengerMap = new HashMap<String, Integer>();
        if (passenger.get(selectedDate) != null) {          //check the entered date was create in previous
            passengerMap = passenger.get(selectedDate);
        }
        final HashMap eSeat = passengerMap;

        for(int i = 1; i <= SEATING_CAPACITY; i++) {        //create buttons id using for loop
            Button btn = new Button("Seat " + i);
            btn.setId(Integer.toString(i));
            btn.setStyle("-fx-background-color:#87CEEB;-fx-text-fill:black;");
            flowPane.getChildren().add(btn);

            if (eSeat.containsValue(Integer.parseInt(btn.getId()))) {       //finding the seats which have value
                btn.setVisible(false);
            }
        }
        Scene scene = new Scene(flowPane, 340, 440);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    private void allSeats(HashMap<String,HashMap> passenger) {      //defined method to show all seat from Hash map
        Stage primaryStage =new Stage();
        primaryStage.setTitle("Train seat booking program");
        FlowPane flowPane = fPane();            //calling method to get flow pane

        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();          //take date as a input

        HashMap passengerMap = new HashMap<String, Integer>();
        if (passenger.get(selectedDate) != null) {      //check the entered date was create in previous
            passengerMap = passenger.get(selectedDate);
        }
        final HashMap aSeat = passengerMap;

        for(int i = 1; i <= SEATING_CAPACITY; i++) {            //create buttons id using for loop
            Button btn = new Button("Seat " + i);
            btn.setId(Integer.toString(i));
            btn.setStyle("-fx-background-color:#87CEEB;-fx-text-fill:black;");
            flowPane.getChildren().add(btn);

            if (aSeat.containsValue(Integer.parseInt(btn.getId()))) {       //finding the seats which have value
                btn.setDisable(true);
                btn.setText("Booked");
                btn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");
            }
        }
        Scene scene = new Scene(flowPane, 340, 440);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    private void addCust(HashMap<String, HashMap> passenger) {           //defined method to add customer from Hash map

        Scanner date = new Scanner(System.in);
        System.out.println("Please enter a date: (ex: 2020-01-01) ");
        String selectedDate = date.next();          //take date as a input

        Scanner scan=new Scanner(System.in);
        System.out.println("Enter your first name : ");
        String fName=scan.next().toUpperCase();         //get string input & turn the string value to uppercase
        System.out.println("Enter your surname : ");
        String sName=scan.next().toUpperCase();         //get string input & turn the string value to uppercase
        String output=fName+" "+sName;

        Stage primaryStage =new Stage();
        primaryStage.setTitle("Train seat booking program");
        FlowPane flowPane = fPane();                //calling method to get flow pane

        HashMap passengerMap = new HashMap<String, Integer>();
        if (passenger.get(selectedDate) != null) {      //check the entered date was create in previous
            passengerMap = passenger.get(selectedDate);
        }
        final HashMap addSeat = passengerMap;

        for(int i = 1; i <= SEATING_CAPACITY; i++) {            //create buttons id using for loop
            Button btn = new Button("Seat " + i);
            btn.setId(Integer.toString(i));
            btn.setStyle("-fx-background-color:#87CEEB;-fx-text-fill:black;");
            flowPane.getChildren().add(btn);

            if(addSeat.containsValue(Integer.parseInt(btn.getId()))) {        //finding the seats which have value
                btn.setDisable(true);
                btn.setText("Booked");
                btn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");
            }
            else {
                btn.setOnAction(new EventHandler<ActionEvent>() {       //defined event
                    @Override
                    public void handle(ActionEvent event) {
                        btn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");

                        Stage conBox = new Stage();                                 //confirmation box
                        conBox.setTitle("Confirmation");
                        Button confirm = new Button("Book");
                        VBox vbox = new VBox(new Text("Please confirm!!"), confirm);
                        vbox.setSpacing(20);
                        vbox.setStyle("-fx-background-color:#d1d1e0;-fx-font-size:18;-fx-font-weight:bolder;");
                        vbox.setAlignment(Pos.CENTER);
                        vbox.setPadding(new Insets(20));

                        confirm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                addSeat.put(output, Integer.parseInt(btn.getId()));         //put values to the Hash map
                                passenger.put(selectedDate, addSeat);
                                System.out.println(output+ " booked seat No." + btn.getId());
                                conBox.close();
                                primaryStage.close();
                            }
                        });
                        conBox.setScene(new Scene(vbox, 200, 150));
                        conBox.showAndWait();
                    }
                });
            }
        }
        Scene scene = new Scene(flowPane, 340, 440);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }
    private static FlowPane fPane() {       //defined method to flow pane
        FlowPane flowPane=new FlowPane();
        flowPane.setHgap(15);
        flowPane.setVgap(7.5);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setStyle("-fx-background-color:#d1d1e0");
        return flowPane;
    }

    public static void main(String[] args) {
        launch();
    }
}