/**
 * Part 4 - one appointment booking.
 *
 * The clinic only takes bookings for the current day, so an appointment stores
 * the patient details, the health professional the patient is going to see and
 * the 30 minute time slot. The class implements Comparable so the manager can
 * sort appointments by time.
 *
 * The constructor validates every argument: an appointment can never be
 * created in an incomplete or invalid state.
 */
public class Appointment implements Comparable<Appointment> {

    private final String patientName;
    private final String patientMobile;
    private final HealthProfessional professional;
    private final String timeSlot;

    /**
     * @param patientName     name of the patient
     * @param patientMobile   mobile number of the patient
     * @param professional    health professional for this appointment
     * @param timeSlot        time slot in 24 hour format, for example "09:30"
     */
    public Appointment(String patientName, String patientMobile,
                       HealthProfessional professional, String timeSlot) {
        if (patientName == null || patientName.isBlank()) {
            throw new IllegalArgumentException("Patient name cannot be empty.");
        }
        if (patientMobile == null || patientMobile.isBlank()) {
            throw new IllegalArgumentException("Patient mobile cannot be empty.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("A health professional is required.");
        }
        if (timeSlot == null || timeSlot.isBlank()) {
            throw new IllegalArgumentException("Time slot cannot be empty.");
        }
        this.patientName = patientName.trim();
        this.patientMobile = patientMobile.trim();
        this.professional = professional;
        this.timeSlot = timeSlot;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public HealthProfessional getProfessional() {
        return professional;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Time slots are stored as zero padded 24 hour strings such as "08:00" and
     * "13:30", so comparing the strings compares the chronological order.
     *
     * @param other appointment to compare with
     * @return a negative number when this appointment is earlier
     */
    @Override
    public int compareTo(Appointment other) {
        return this.timeSlot.compareTo(other.timeSlot);
    }

    /**
     * Shows the booking and the full details of the health professional.
     */
    @Override
    public String toString() {
        return String.format("%s | Patient: %-16s | Mobile: %-12s | Professional %s",
                timeSlot, patientName, patientMobile, professional);
    }
}
