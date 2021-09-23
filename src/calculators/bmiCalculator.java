package calculators;

public class bmiCalculator {
    public double calculateBMI(double length, double weight) {
        double bmi = (100*100*weight)/(length*length);
        return bmi;
    }
    public String bmiCategory(double bmi) {
        if(bmi < 18.5) {
            return "Underweight";
        }else if (bmi < 25) {
            return "Normal";
        }else if (bmi < 30) {
            return "Overweight";
        }else {
            return "Obese";
        }
    }
}
