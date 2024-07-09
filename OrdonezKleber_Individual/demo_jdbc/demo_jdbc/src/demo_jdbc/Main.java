package demo_jdbc;




import java.util.List;

import demo_jdbc.models.DriverResult;
import demo_jdbc.respositories.DriverResultRepository;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import demo_jdbc.models.Circuit;
import demo_jdbc.models.DriverResult;
import demo_jdbc.respositories.CircuitRepository;
import demo_jdbc.respositories.DriverResultRepository; 


public class Main {

	public static void main(String[] args) {
		
		DriverResultRepository driverResultRepository = new DriverResultRepository();
		var results = driverResultRepository.getResultByYear(2000);
		
		for(DriverResult rs: results) {		
			System.out.println(rs.getDriverName()  + rs.getWins() + rs.getTotalPoints() + rs.getRank());
			
			
		}
		
		CircuitRepository circuitRepository = new CircuitRepository();
				
		var circuits = circuitRepository.getCircuits();
		
		
		System.out.println("Total circuitos: " + circuits.size());
		
		String country = "Russia";
		
		var circuitsByCountry = circuitRepository.getCircuitsByCountry(country);
		
		System.out.println("Circuitos de " + country);
		
		for(Circuit circuit:circuitsByCountry) {
			System.out.println(circuit.getName() + "\t" + circuit.getLocation() + "\t"+ circuit.getCountry());		}
		
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/formula_01";
		String user = "root";
		String password = "0505";
		
		List<Circuit> circuits1 = new ArrayList();
		
		try {
			// Establecer la conexion
			Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
			System.out.println("Se conecto a la base de datos");
			
			// Crear una sentencia
			Statement statement = conn.createStatement();
			
			// Ejecutar la consulta
			String sql = "SELECT * FROM circuits";
			ResultSet rs = statement.executeQuery(sql);
			
			// Procesar los resultados
			while(rs.next()) {
				int circuitid = rs.getInt("circuitid");
				String circuitref = rs.getString("circuitref");
				String name = rs.getString("name");
				String location = rs.getString("location");
				String country1 = rs.getString("country");
				
				Circuit circuit = new Circuit(circuitid, circuitref, name, location, country1);
				circuits1.add(circuit);
				
				// System.out.println(circuitid + "\t" + circuitref + "\t"+ name + "\t" + location + "\t" + country);
				
			}
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		*/
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    
	private TableView<DriverResult> tableView;
    private DriverResultRepository driverResultRepository;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Results");
        driverResultRepository = new DriverResultRepository();

        // Crear el ComboBox para seleccionar el año
        ComboBox<Integer> yearComboBox = new ComboBox<>();
        yearComboBox.getItems().addAll(getYearsFromDatabase());
        yearComboBox.setOnAction(e -> {
            Integer selectedYear = yearComboBox.getValue();
            if (selectedYear != null) {
                updateTable(selectedYear);
            }
        });

        // Crear el TableView
        tableView = new TableView<>();
                TableColumn<DriverResult, String> driverNamec = new TableColumn<>("Driver Name");
        driverNamec.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        
        TableColumn<DriverResult, Integer> winsc = new TableColumn<>("Wins");
        winsc.setCellValueFactory(new PropertyValueFactory<>("wins"));
        
        TableColumn<DriverResult, Integer> totalPointsc = new TableColumn<>("Total Points");
        totalPointsc.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));
        
        TableColumn<DriverResult, Integer> rankc = new TableColumn<>("Rank");
        rankc.setCellValueFactory(new PropertyValueFactory<>("rank"));
        
        tableView.getColumns().addAll(driverNamec, winsc, totalPointsc, rankc);
        

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        vbox.getChildren().addAll(yearComboBox, tableView);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private ObservableList<Integer> getYearsFromDatabase() {
        List<Integer> years = driverResultRepository.getAvailableYears();
        return FXCollections.observableArrayList(years);
    }

    private void updateTable(int year) {
        List<DriverResult> results = driverResultRepository.getResultByYear(year);
        System.out.println("Results size: " + results.size()); // Añadido para depuración
        for (DriverResult result : results) {
            System.out.println(result); // Añadido para depuración
        }
        ObservableList<DriverResult> driverResults = FXCollections.observableArrayList(results);
        tableView.setItems(driverResults);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
	



