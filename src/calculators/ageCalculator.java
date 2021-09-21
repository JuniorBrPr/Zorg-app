package calculators;

import java.time.LocalDate;
import java.time.Period;

public class ageCalculator {
    public static int calculateAge(LocalDate birthDate) {
        if ((birthDate != null)) {
            LocalDate ld = LocalDate.now();
            int age = Period.between(birthDate, ld).getYears();
            return age;
        } else {
            return 0;
        }
    }
}
