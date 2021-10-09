package pojo;

public class Medication {
    private int medId;
    private String medName;
    private String dosage;
    private String manufacturer;
    private int patientId;
    private String medType;


    public Medication(int medId, String medName, String dosage, String manufacturer, int patientId, String medType) {
        this.medId = medId;
        this.medName = medName;
        this.dosage = dosage;
        this.manufacturer = manufacturer;
        this.patientId = patientId;
        this.medType = medType;;
    }


    public int getMedId(){
        return medId;
    }
    public int setMedId(int medId){
        return medId;
    }

    public String getMedName(){
        return medName;
    }
    public String setMedName(String medName){
        return medName;
    }
    public String getDosage(){
        return dosage;
    }
    public String setDosage(String dosage){
        return dosage;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public String setManufacturer(String manufacturer){
        return manufacturer;
    }
    public int getPatientId(){
        return patientId;
    }
    public int setPatientId(int patientId){
        return patientId;
    }
    public String getMedType(){
        return medType;
    }
    public String setMedType(String medType){
        return medType;
    }
}


