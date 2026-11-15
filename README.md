# PROG2004 Assignment 1 - Clinic Appointment System

Console based Java program that models the staff and the appointment booking
process of a small medical clinic. There is no graphical interface and no file
storage: everything is demonstrated from the `main` method of `AssignmentOne`,
as required by the assessment brief.

## Project structure

```
A1-ZiangDong/
├── src/
│   ├── AssignmentOne.java        entry point, Part 3 and Part 6 demo
│   ├── HealthProfessional.java   Part 1, abstract parent class
│   ├── GeneralPractitioner.java  Part 2, general practitioner subclass
│   ├── Specialist.java           Part 2, specialist subclass
│   ├── Nurse.java                Part 2, nurse subclass
│   ├── Dietitian.java            Part 2, dietitian subclass
│   ├── Appointment.java          Part 4, booking with Comparable time order
│   └── AppointmentManager.java   Part 5, booking rules and queries
├── .gitignore
└── README.md
```

## How to compile and run

```
javac -encoding UTF-8 -d out src/*.java
java -cp out AssignmentOne
```

The output of every demonstration step, including the error cases, is printed
to the console.

## Requirements covered

| Part | Requirement | Where it is implemented |
|------|-------------|--------------------------|
| 1 | Abstract parent class, numeric ID, name, working days, encapsulation | `HealthProfessional` |
| 2 | Four subclasses including a general practitioner, `super()` calls, `toString()` overrides | `GeneralPractitioner`, `Specialist`, `Nurse`, `Dietitian` |
| 3 | Objects of different subclasses collected in a parent type list and printed polymorphically | `AssignmentOne.showHealthProfessionals()` |
| 4 | Appointment class with validated constructor, patient name, mobile, professional and time slot, sortable by time | `Appointment` |
| 5 | Booking management: 30 minute slots from 08:00 to 16:00, double booking rejected, filter by professional ID, filter by patient mobile, sort by time, cancel by professional and time, cancel by mobile | `AppointmentManager` |
| 6 | End to end demonstration including a rejected double booking and a rejected cancellation | `AssignmentOne.runAppointmentDemo()` |

## Object oriented features demonstrated

- **Abstraction** - `HealthProfessional` is `abstract` and declares the abstract
  method `describeRole()`. A plain health professional cannot be instantiated.
- **Encapsulation** - every field is `private` and `final`; values are checked in
  the constructor and exposed only through getters. Working days are copied so
  the caller cannot change the object state.
- **Inheritance** - the four subclasses extend `HealthProfessional`, call
  `super(...)` and reuse `super.toString()` for the shared information.
- **Polymorphism** - one `List<HealthProfessional>` holds four different
  subclasses and the loop calls the correct `toString()` of each object.
- **Identity** - `equals()` and `hashCode()` are overridden on the numeric staff
  ID, which is what allows the manager to detect a double booking.
- **Ordering** - `Appointment` implements `Comparable` and appointments are
  sorted with `Collections.sort`.
