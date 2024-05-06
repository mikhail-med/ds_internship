package ru.ds.edu.medvedew.internship.exceptions;

public class GitlabUserCantBeCreated extends IllegalArgumentException {
    public GitlabUserCantBeCreated(String s) {
        super(s);
    }
}
