package ru.ds.edu.medvedew.internship.exceptions;

public class UserNotInternshipParticipant extends IllegalArgumentException {
    public UserNotInternshipParticipant(String s) {
        super(s);
    }
}
