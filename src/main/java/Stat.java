import com.esprit.Utils.Connexion;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Stat {
 //   private Connection connection;


        Connection cnx = Connexion.getInstance().getCnx();

    @FXML
    private PieChart pieChart;

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            // Utilize a Statement to execute the SQL query
            try (Statement statement = cnx.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT role, COUNT(*) FROM usr GROUP BY role")) {

                // Traverse the results and add data to the PieChart
                while (resultSet.next()) {
                    String role = resultSet.getString("role");
                    int nombreEvenements = resultSet.getInt(2); // Use column index 2 for the count column

                    PieChart.Data slice = new PieChart.Data(role, nombreEvenements);
                    pieChartData.add(slice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }

    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = contc();
        pieChart.setData(pieChartData);
    }
}
