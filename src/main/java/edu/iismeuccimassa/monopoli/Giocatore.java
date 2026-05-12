package edu.iismeuccimassa.monopoli;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    // ... i tuoi campi esistenti (nome, colore, soldi, etc.) ...
    boolean inPrigione = false;
    int turniInPrigione = 0;
    final String nome;
    int posizione = 0;
    int soldi = 1500;
    final Color colore;
    final List<Casella> proprieta = new ArrayList<>();

    public Giocatore(String nome, Color colore) {
        this.nome = nome;
        this.colore = colore;
        this.soldi = 1500; // Esempio di budget iniziale
    }

}
