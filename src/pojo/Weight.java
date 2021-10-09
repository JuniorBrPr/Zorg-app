package pojo;

import java.time.LocalDate;

public class Weight {
    private int weightId;
    private double weight;
    private LocalDate weightDate;
    private int patientId;

    public Weight(int weightId, double weight, LocalDate weightDate, int patientId) {
        this.weightId = weightId;
        this.weight = weight;
        this.weightDate = weightDate;
        this.patientId = patientId;
    }

    public int getWeightId() {
        return weightId;
    }
    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public LocalDate getWeightDate() {
        return weightDate;
    }
    public void setWeightDate(LocalDate weightDate) {
        this.weightDate = weightDate;
    }
    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
