package com.example.shahulhameedc.studentsapp;

public class Api {
    private static final String ROOT_URL = "http://192.168.28.31/studentserver/v1/Api.php?apicall=";

    public static final String URL_CREATE_REC = ROOT_URL + "createrecord";
    public static final String URL_READ_REC = ROOT_URL + "readrecord";
    public static final String URL_UPDATE_REC = ROOT_URL + "updaterecord";
    public static final String URL_DELETE_REC = ROOT_URL + "deleterecord&id=";

}
