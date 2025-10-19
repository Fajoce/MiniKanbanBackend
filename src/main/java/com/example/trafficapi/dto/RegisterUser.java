package com.example.trafficapi.dto;

public class RegisterUser {
    private String username;
    private String password;
    private String rol;

    public RegisterUser() {}

    public RegisterUser(String username, String password, String Rol) {
        this.username = username;
        this.password = password;
        this.rol = Rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
