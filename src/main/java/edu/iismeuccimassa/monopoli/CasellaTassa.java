package edu.iismeuccimassa.monopoli;

import javax.swing.*;

public class CasellaTassa extends Casella {
    final int importo;

    CasellaTassa(String nome, int importo) {
        super(nome, TipoCasella.TASSA, null);
        this.importo = importo;
    }

    @Override
    void eseguiAzione(Giocatore g) {
        int paga = Math.min(importo, g.soldi);
        g.soldi -= paga;
        JOptionPane.showMessageDialog(null,
                "<html><b>" + g.nome + "</b> paga la tassa di <b>$" + paga + "</b>!</html>",
                "Tassa", JOptionPane.WARNING_MESSAGE);
    }
}
