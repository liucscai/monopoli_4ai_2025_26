package edu.iismeuccimassa.monopoli;

import javax.swing.*;
//Classe derivata da Casella
public class CasellaTassa extends Casella {
    final int importo;

    CasellaTassa(String nome, int importo) {
        super(nome, TipoCasella.TASSA, null);
        this.importo = importo;
    }
    //Questo eseguiAzione fa pagare una tassa al giocatore che è finito sulla casella
    @Override
    void eseguiAzione(Giocatore g) {
        int paga = Math.min(importo, g.soldi);
        g.soldi -= paga;
        JOptionPane.showMessageDialog(null,
                "<html><b>" + g.nome + "</b> paga la tassa di <b>$" + paga + "</b>!</html>",
                "Tassa", JOptionPane.WARNING_MESSAGE);
    }
}
