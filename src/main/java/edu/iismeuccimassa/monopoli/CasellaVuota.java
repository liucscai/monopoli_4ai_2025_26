package edu.iismeuccimassa.monopoli;

import javax.swing.*;

public class CasellaVuota extends Casella {
    CasellaVuota(String nome, TipoCasella tipo) {
        super(nome, tipo, null);
    }

    @Override
    void eseguiAzione(Giocatore g) {
        if (tipo == TipoCasella.VAI_IN_PRIGIONE) {
            g.posizione = 10;
            JOptionPane.showMessageDialog(null,
                    "<html><b>" + g.nome + "</b> va in Prigione!</html>",
                    "Vai in Prigione!", JOptionPane.WARNING_MESSAGE);
        }
    }
}