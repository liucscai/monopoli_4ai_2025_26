package edu.iismeuccimassa.monopoli;

import javax.swing.*;

public class Ferrovia extends Casella {
    static final int PREZZO = 200;
    static final int AFFITTO = 25;
    Giocatore proprietario;

    Ferrovia(String nome) {
        super(nome, TipoCasella.FERROVIA, null);
    }

    @Override
    void eseguiAzione(Giocatore g) {
        if (proprietario == null) {
            if (g.soldi >= PREZZO) {
                int r = JOptionPane.showConfirmDialog(null,
                        "<html><b>" + g.nome + "</b> vuoi comprare la ferrovia<br><b>" + nome + "</b> per <b>$" + PREZZO + "</b>?</html>",
                        "Acquisto Ferrovia", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    g.soldi -= PREZZO;
                    proprietario = g;
                    g.proprieta.add(this);
                }
            }
        } else if (proprietario != g) {
            int paga = Math.min(AFFITTO, g.soldi);
            g.soldi -= paga;
            proprietario.soldi += paga;
            JOptionPane.showMessageDialog(null,
                    "<html><b>" + g.nome + "</b> paga <b>$" + paga + "</b> per il biglietto a <b>" + proprietario.nome + "</b></html>",
                    "Ferrovia", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
