package edu.iismeuccimassa.monopoli;

import javax.swing.*;

public class Utilita extends Casella {
    static final int PREZZO = 150;
    static final int AFFITTO = 30;
    Giocatore proprietario;

    Utilita(String nome) {
        super(nome, TipoCasella.UTILITA, null);
    }

    @Override
    void eseguiAzione(Giocatore g) {
        if (proprietario == null) {
            if (g.soldi >= PREZZO) {
                int r = JOptionPane.showConfirmDialog(null,
                        "<html><b>" + g.nome + "</b> vuoi comprare<br><b>" + nome + "</b> per <b>$" + PREZZO + "</b>?</html>",
                        "Acquisto Società", JOptionPane.YES_NO_OPTION);
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
                    "<html><b>" + g.nome + "</b> paga <b>$" + paga + "</b> alla società di <b>" + proprietario.nome + "</b></html>",
                    "Utenza", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
