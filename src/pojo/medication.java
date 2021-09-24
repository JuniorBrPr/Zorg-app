package pojo;

public class medication {
    int medId;
    String medName;
    String dosage;
    String manufacturer;



    public medication(int medId, String medName, String dosage, String manufacturer) {
        this.medId = medId;
        this.medName = medName;
        this.dosage = dosage;
        this.manufacturer = manufacturer;
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
}


