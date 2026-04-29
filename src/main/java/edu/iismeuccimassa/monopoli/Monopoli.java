package edu.iismeuccimassa.monopoli;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// --- LOGICA DEL GIOCO ---

abstract class Casella {
    String nome;
    public Casella(String nome) { this.nome = nome; }
    public abstract void eseguiAzione(Giocatore g);
}

class Proprieta extends Casella {
    int prezzo;
    int affitto;
    Giocatore proprietario;

    public Proprieta(String nome, int prezzo, int affitto) {
        super(nome);
        this.prezzo = prezzo;
        this.affitto = affitto;
    }

    @Override
    public void eseguiAzione(Giocatore g) {
        if (proprietario == null) {
            if (g.soldi >= prezzo) {
                int scelta = JOptionPane.showConfirmDialog(null, 
                    g.nome + ", vuoi comprare " + nome + " per $" + prezzo + "?", "Acquisto", JOptionPane.YES_NO_OPTION);
                if (scelta == JOptionPane.YES_OPTION) {
                    g.soldi -= prezzo;
                    proprietario = g;
                }
            }
        } else if (proprietario != g) {
            g.soldi -= affitto;
            proprietario.soldi += affitto;
            JOptionPane.showMessageDialog(null, g.nome + " paga $" + affitto + " a " + proprietario.nome);
        }
    }
}

class CasellaVuota extends Casella {
    public CasellaVuota(String nome) { super(nome); }
    @Override
    public void eseguiAzione(Giocatore g) { }
}

class Giocatore {
    String nome;
    int posizione = 0;
    int soldi = 2500;
    Color colore;

    public Giocatore(String nome, Color colore) {
        this.nome = nome;
        this.colore = colore;
    }
}

// --- CLASSE PRINCIPALE ---

public class Monopoli extends JFrame {
    private List<Casella> tabellone = new ArrayList<>();
    private List<Giocatore> giocatori = new ArrayList<>();
    private int turnoCorrente = 0;
    private JPanel[] caselleGrafiche = new JPanel[12];
    private JLabel infoLabel;
    private JLabel dadoLabel; // Label per il risultato del dado
    private JButton btnLancia; // Dichiarata qui e usata correttamente sotto

    public Monopoli() {
        setupGioco();
        initGUI();
    }

    private void setupGioco() {
        tabellone.add(new CasellaVuota("VIA!"));
        tabellone.add(new Proprieta("Vicolo Corto", 60, 10));
        tabellone.add(new CasellaVuota("Probabilità"));
        tabellone.add(new Proprieta("Vicolo Stretto", 80, 20));
        tabellone.add(new CasellaVuota("Prigione"));
        tabellone.add(new Proprieta("Corso Dante", 140, 50));
        tabellone.add(new CasellaVuota("Parcheggio"));
        tabellone.add(new Proprieta("Corso Rossini", 160, 60));
        tabellone.add(new CasellaVuota("Vai in Prigione"));
        tabellone.add(new Proprieta("Piazza Verdi", 240, 100));
        tabellone.add(new Proprieta("Parco Vittoria", 400, 200));
        tabellone.add(new CasellaVuota("Tassa"));

        giocatori.add(new Giocatore("Giocatore 1", Color.RED));
        giocatori.add(new Giocatore("Giocatore 2", Color.BLUE));
    }

    private void initGUI() {
        setTitle("Monopoli Meucci - Bancarotta & Dado");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello Laterale per il Dado
        JPanel sidePanel = new JPanel(new GridLayout(2, 1));
        dadoLabel = new JLabel("-", SwingConstants.CENTER);
        dadoLabel.setFont(new Font("Serif", Font.BOLD, 80)); // Font grande per il dado
        sidePanel.add(new JLabel("ULTIMO LANCIO:", SwingConstants.CENTER));
        sidePanel.add(dadoLabel);
        add(sidePanel, BorderLayout.EAST);

        // Tabellone
        JPanel board = new JPanel(new GridLayout(4, 4));
        int[] mappa = {0, 1, 2, 3, 11, -1, -1, 4, 10, -1, -1, 5, 9, 8, 7, 6};

        for (int i : mappa) {
            if (i == -1) board.add(new JLabel(""));
            else {
                caselleGrafiche[i] = new JPanel(new BorderLayout());
                caselleGrafiche[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                caselleGrafiche[i].add(new JLabel(tabellone.get(i).nome, SwingConstants.CENTER), BorderLayout.NORTH);
                board.add(caselleGrafiche[i]);
            }
        }

        // --- CORREZIONE QUI ---
        btnLancia = new JButton("Lancia Dadi"); // Rimosso "JButton" davanti
        infoLabel = new JLabel("Turno di: " + giocatori.get(0).nome + " | Soldi: $2500", SwingConstants.CENTER);

        btnLancia.addActionListener(e -> giocaTurno());

        add(board, BorderLayout.CENTER);
        add(btnLancia, BorderLayout.SOUTH);
        add(infoLabel, BorderLayout.NORTH);
        
        aggiornaGrafica();
    }

    private void giocaTurno() {
        Giocatore g = giocatori.get(turnoCorrente);
        int dado = (int) (Math.random() * 6) + 1;
        
        // Visualizza il dado (Icone Unicode)
        String[] facce = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
        dadoLabel.setText(facce[dado - 1]);
        dadoLabel.setForeground(g.colore);
        
        g.posizione = (g.posizione + dado) % tabellone.size();
        tabellone.get(g.posizione).eseguiAzione(g);
        
        // Controllo Bancarotta
        if (g.soldi <= 0) {
            aggiornaGrafica();
            JOptionPane.showMessageDialog(this, "GAME OVER! " + g.nome + " è fallito!");
            btnLancia.setEnabled(false); // Ora non darà più errore!
            infoLabel.setText("PARTITA FINITA");
            return;
        }

        turnoCorrente = (turnoCorrente + 1) % giocatori.size();
        infoLabel.setText("Turno di: " + giocatori.get(turnoCorrente).nome + " | Ultimo giocatore ha: $" + g.soldi);
        aggiornaGrafica();
    }

    private void aggiornaGrafica() {
        for (JPanel p : caselleGrafiche) {
            if (p != null) p.setBackground(Color.WHITE);
        }
        for (Giocatore g : giocatori) {
            caselleGrafiche[g.posizione].setBackground(g.colore.brighter());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Monopoli().setVisible(true));
    }
}