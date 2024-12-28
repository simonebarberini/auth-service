package com.poc.authservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class AuthResponse {
    private String username;
    private String codiceAbi;
    private String ruolo;
    private String token;
    private boolean isAtuht;

    public AuthResponse(String username, String codiceAbi, String ruolo, String token, boolean isAtuht) {
        this.username = username;
        this.codiceAbi = codiceAbi;
        this.ruolo = ruolo;
        this.token = token;
        this.isAtuht = isAtuht;
    }

    public AuthResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAtuht() {
        return isAtuht;
    }

    public void setAtuht(boolean atuht) {
        isAtuht = atuht;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCodiceAbi() {
        return codiceAbi;
    }

    public void setCodiceAbi(String codiceAbi) {
        this.codiceAbi = codiceAbi;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
