package edu.iismeuccimassa.monopoli;

import java.awt.*;

public abstract class Casella {
    final String nome;
    final TipoCasella tipo;
    final Color coloreGruppo;

    Casella(String nome, TipoCasella tipo, Color coloreGruppo) {
        this.nome = nome;
        this.tipo = tipo;
        this.coloreGruppo = coloreGruppo;
    }

    abstract void eseguiAzione(Giocatore g);
}