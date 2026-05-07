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

enum TipoCasella {
    VIA, PROPRIETA, FERROVIA, UTILITA, TASSA, PROBABILITA, IMPREVISTI,
    PRIGIONE, VAI_IN_PRIGIONE, PARCHEGGIO
}

abstract class Casella {
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

class Ferrovia extends Casella {
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

class Utilita extends Casella {
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

class CasellaTassa extends Casella {
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

class CasellaVuota extends Casella {
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


    class Giocatore {
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


