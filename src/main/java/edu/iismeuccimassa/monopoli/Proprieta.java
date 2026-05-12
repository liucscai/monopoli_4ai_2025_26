/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iismeuccimassa.monopoli;

/**
 *
 * @author 4ainfo20
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Proprieta extends Casella {
    final int prezzo;
    final int affitto;
    Giocatore proprietario;

    Proprieta(String nome, Color gruppo, int prezzo, int affitto) {
        super(nome, TipoCasella.PROPRIETA, gruppo);
        this.prezzo = prezzo;
        this.affitto = affitto;
    }

    @Override
    void eseguiAzione(Giocatore g) {
        if (proprietario == null) {
            if (g.soldi >= prezzo) {
                int r = JOptionPane.showConfirmDialog(null,
                        "<html><b>" + g.nome + "</b> vuoi comprare<br><b>" + nome + "</b> per <b>$" + prezzo + "</b>?</html>",
                        "Acquisto Proprietà", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    g.soldi -= prezzo;
                    proprietario = g;
                    g.proprieta.add(this);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "<html><b>" + g.nome + "</b> non può permettersi <b>" + nome + "</b> ($" + prezzo + ")</html>",
                        "Fondi Insufficienti", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (proprietario != g) {
            int paga = Math.min(affitto, g.soldi);
            g.soldi -= paga;
            proprietario.soldi += paga;
            JOptionPane.showMessageDialog(null,
                    "<html><b>" + g.nome + "</b> paga <b>$" + paga + "</b> di affitto a <b>" + proprietario.nome + "</b></html>",
                    "Affitto", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}


