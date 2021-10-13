import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private LineChart<?, ?> chart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data("1",5));
        series.getData().add(new XYChart.Data("2",4));
        series.getData().add(new XYChart.Data("3",3));
        series.getData().add(new XYChart.Data("2",2));

        XYChart.Series series2 = new XYChart.Series();

        series.getData().add(new XYChart.Data("1",3));
        series.getData().add(new XYChart.Data("3",2));
        series.getData().add(new XYChart.Data("5",6));
        series.getData().add(new XYChart.Data("6",3));

        chart.getData().addAll(series, series2);
    }
}
