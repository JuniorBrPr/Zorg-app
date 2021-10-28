package calculators;

import java.util.Locale;
import java.util.ResourceBundle;

public class bmiCalculator {
    public double calculateBMI(double length, double weight) {
        double bmi = (100*100*weight)/(length*length);
        return bmi;
    }
    //Calculates the BMI category , with the bmi
    public String bmiCategory(double bmi, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
        if(bmi < 18.5) {
            return resourceBundle.getString("uweight");
        }else if (bmi < 25) {
            return resourceBundle.getString("normal");
        }else if (bmi < 30) {
            return resourceBundle.getString("oweight");
        }else {
            return resourceBundle.getString("obese");
        }
    }
}
