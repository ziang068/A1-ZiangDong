import java.util.List;

/**
 * Part 2 - general practitioner (GP), the role required by the brief.
 *
 * A GP is a health professional, so it inherits every common field from
 * HealthProfessional and adds one own attribute: the GP's clinic room.
 */
public class GeneralPractitioner extends HealthProfessional {

    private final String clinicRoom;

    /**
     * @param professionalId numeric staff ID
     * @param fullName       full name of the GP
     * @param workingDays    days of the week the GP works
     * @param clinicRoom     room where the GP sees patients
     */
    public GeneralPractitioner(int professionalId, String fullName,
                               List<String> workingDays, String clinicRoom) {
        super(professionalId, fullName, workingDays);
        if (clinicRoom == null || clinicRoom.isBlank()) {
            throw new IllegalArgumentException("Clinic room cannot be empty.");
        }
        this.clinicRoom = clinicRoom;
    }

    public String getClinicRoom() {
        return clinicRoom;
    }

    /**
     * A GP can issue a repeat prescription, which is behaviour specific to
     * this subclass.
     *
     * @param medicationName name of the medication
     * @return confirmation message
     */
    public String issueRepeatPrescription(String medicationName) {
        return "GP " + getFullName() + " issued a repeat prescription for " + medicationName;
    }

    @Override
    public String describeRole() {
        return "General Practitioner";
    }

    /**
     * Reuses the common part from the parent class and appends the GP specific
     * attribute.
     */
    @Override
    public String toString() {
        return "[" + describeRole() + "] " + super.toString()
                + " | Clinic room: " + clinicRoom;
    }
}
