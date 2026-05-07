/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.monopoli;

/**
 *
 * @author 4ainfo20
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class Monopoli extends JFrame {

    // Layout constants
    private static final int BOARD  = 800;
    private static final int CORNER = 100;
    private static final double SQ  = (BOARD - 2.0 * CORNER) / 9.0; // ~66.67
    private static final int BAND   = 20;
    private static final int SIDE   = 270;

    // Palette
    private static final Color BG        = new Color(8,  12, 28);
    private static final Color CARD_BG   = new Color(18, 26, 52);
    private static final Color BOARD_GRN = new Color(20, 90, 45);
    private static final Color SQ_BG     = new Color(255, 252, 235);
    private static final Color SQ_BORDER = new Color(180, 180, 165);
    private static final Color GOLD      = new Color(255, 210,   0);
    private static final Color MONEY_GRN = new Color(74,  222, 128);

    // Game state
    private final List<Casella>   tabellone = new ArrayList<>();
    private final List<Giocatore> giocatori = new ArrayList<>();
    private int     turnoCorrente = 0;
    private boolean partitaFinita = false;
    private Rectangle2D[] squareRects;

    // UI references
    private BoardPanel boardPanel;
    private JLabel[]   moneyLabels, propLabels;
    private JLabel     turnoLabel, dice1Label, dice2Label;
    private JButton    btnLancia;

    public Monopoli() {
        setupGioco();
        computeRects();
        initGUI();
    }

    // ── SETUP ────────────────────────────────────────────────────────────────

    private void setupGioco() {
        Color PURPLE = new Color(88,  28, 135);
        Color CYAN   = new Color(2,  175, 224);
        Color PINK   = new Color(210,  50, 130);
        Color ORANGE = new Color(228, 110,   0);
        Color RED    = new Color(210,  30,  30);
        Color YELLOW = new Color(220, 195,   0);
        Color GREEN  = new Color(0,  155,  65);
        Color DKBLUE = new Color(0,   60, 175);

        tabellone.add(new CasellaVuota("VIA!",           TipoCasella.VIA));              // 0
        tabellone.add(new Proprieta("Vicolo Corto",      PURPLE,  60,  2));              // 1
        tabellone.add(new CasellaVuota("Probabilità",    TipoCasella.PROBABILITA));      // 2
        tabellone.add(new Proprieta("Vicolo Stretto",    PURPLE,  60,  4));              // 3
        tabellone.add(new CasellaTassa("Tassa Reddito",  200));                          // 4
        tabellone.add(new Ferrovia("Stazione Sud"));                                      // 5
        tabellone.add(new Proprieta("Via Nettuno",       CYAN,   100,  6));              // 6
        tabellone.add(new CasellaVuota("Imprevisti",     TipoCasella.IMPREVISTI));       // 7
        tabellone.add(new Proprieta("Via Matteucci",     CYAN,   100,  6));              // 8
        tabellone.add(new Proprieta("Viale Monterosa",   CYAN,   120,  8));              // 9
        tabellone.add(new CasellaVuota("Prigione",       TipoCasella.PRIGIONE));         // 10
        tabellone.add(new Proprieta("Viale Vesuvio",     PINK,   140, 10));              // 11
        tabellone.add(new Utilita("Soc. Elettrica"));                                    // 12
        tabellone.add(new Proprieta("Via Accademia",     PINK,   140, 10));              // 13
        tabellone.add(new Proprieta("Corso Ateneo",      PINK,   160, 12));              // 14
        tabellone.add(new Ferrovia("Stazione Ovest"));                                    // 15
        tabellone.add(new Proprieta("Via Verdi",         ORANGE, 180, 14));              // 16
        tabellone.add(new CasellaVuota("Probabilità",    TipoCasella.PROBABILITA));      // 17
        tabellone.add(new Proprieta("Corso Raffaello",   ORANGE, 180, 14));              // 18
        tabellone.add(new Proprieta("Piazza Dante",      ORANGE, 200, 16));              // 19
        tabellone.add(new CasellaVuota("Parcheggio",     TipoCasella.PARCHEGGIO));       // 20
        tabellone.add(new Proprieta("Viale Traiano",     RED,    220, 18));              // 21
        tabellone.add(new CasellaVuota("Imprevisti",     TipoCasella.IMPREVISTI));       // 22
        tabellone.add(new Proprieta("Corso Vannucci",    RED,    220, 18));              // 23
        tabellone.add(new Proprieta("Piazza Tiziano",    RED,    240, 20));              // 24
        tabellone.add(new Ferrovia("Stazione Nord"));                                     // 25
        tabellone.add(new Proprieta("Viale Costantino",  YELLOW, 260, 22));              // 26
        tabellone.add(new Proprieta("V. G. Cesare",      YELLOW, 260, 22));              // 27
        tabellone.add(new Utilita("Soc. Acqua Pot."));                                   // 28
        tabellone.add(new Proprieta("Piazza Navona",     YELLOW, 280, 24));              // 29
        tabellone.add(new CasellaVuota("Vai in Prigione", TipoCasella.VAI_IN_PRIGIONE)); // 30
        tabellone.add(new Proprieta("Viale Giardini",    GREEN,  300, 26));              // 31
        tabellone.add(new Proprieta("Via Roma",          GREEN,  300, 26));              // 32
        tabellone.add(new CasellaVuota("Probabilità",    TipoCasella.PROBABILITA));      // 33
        tabellone.add(new Proprieta("Corso Imperia",     GREEN,  320, 28));              // 34
        tabellone.add(new Ferrovia("Stazione Est"));                                      // 35
        tabellone.add(new CasellaVuota("Imprevisti",     TipoCasella.IMPREVISTI));       // 36
        tabellone.add(new Proprieta("Parco Vittoria",    DKBLUE, 350, 35));              // 37
        tabellone.add(new CasellaTassa("Tassa Lusso",    100));                          // 38
        tabellone.add(new Proprieta("Via delle Rive",    DKBLUE, 400, 50));              // 39

        giocatori.add(new Giocatore("Rosso", new Color(239, 68,  68)));
        giocatori.add(new Giocatore("Blu",   new Color(59,  130, 246)));
    }

    private void computeRects() {
        squareRects = new Rectangle2D[40];
        // Square 0 (GO) — bottom-right corner
        squareRects[0] = rect(BOARD - CORNER, BOARD - CORNER, CORNER, CORNER);
        // Squares 1-9 — bottom row, right to left
        for (int i = 1; i <= 9; i++)
            squareRects[i] = rect(BOARD - CORNER - i * SQ, BOARD - CORNER, SQ, CORNER);
        // Square 10 (Jail) — bottom-left corner
        squareRects[10] = rect(0, BOARD - CORNER, CORNER, CORNER);
        // Squares 11-19 — left column, bottom to top
        for (int i = 11; i <= 19; i++)
            squareRects[i] = rect(0, BOARD - CORNER - (i - 10) * SQ, CORNER, SQ);
        // Square 20 (Parking) — top-left corner
        squareRects[20] = rect(0, 0, CORNER, CORNER);
        // Squares 21-29 — top row, left to right
        for (int i = 21; i <= 29; i++)
            squareRects[i] = rect(CORNER + (i - 21) * SQ, 0, SQ, CORNER);
        // Square 30 (Go To Jail) — top-right corner
        squareRects[30] = rect(BOARD - CORNER, 0, CORNER, CORNER);
        // Squares 31-39 — right column, top to bottom
        for (int i = 31; i <= 39; i++)
            squareRects[i] = rect(BOARD - CORNER, CORNER + (i - 31) * SQ, CORNER, SQ);
    }

    private static Rectangle2D rect(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }

    // ── GUI ──────────────────────────────────────────────────────────────────

    private void initGUI() {
        setTitle("Monopoli");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize(new Dimension(BOARD, BOARD));

        add(boardPanel, BorderLayout.CENTER);
        add(buildSidePanel(), BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel buildSidePanel() {
        JPanel side = darkPanel();
        side.setPreferredSize(new Dimension(SIDE, BOARD));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));

        JLabel title = styledLabel("MONOPOLI", 26, Font.BOLD, GOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(title);
        side.add(Box.createVerticalStrut(5));

        JLabel sub = styledLabel("Il Gioco delle Proprietà", 11, Font.PLAIN, new Color(148, 163, 184));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(sub);
        side.add(Box.createVerticalStrut(22));

        moneyLabels = new JLabel[2];
        propLabels  = new JLabel[2];
        for (int i = 0; i < 2; i++) {
            side.add(buildPlayerCard(i));
            side.add(Box.createVerticalStrut(10));
        }

        side.add(Box.createVerticalStrut(16));

        turnoLabel = styledLabel("Turno di: " + giocatori.get(0).nome, 13, Font.BOLD, Color.WHITE);
        turnoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        side.add(turnoLabel);
        side.add(Box.createVerticalStrut(14));

        JPanel diceRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        diceRow.setOpaque(false);
        dice1Label = makeDieLabel();
        dice2Label = makeDieLabel();
        diceRow.add(dice1Label);
        diceRow.add(dice2Label);
        side.add(diceRow);
        side.add(Box.createVerticalStrut(20));

        btnLancia = new JButton("LANCIA I DADI") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = antialias(g);
                Color top = isEnabled() ? new Color(245, 158, 11) : new Color(60, 60, 70);
                Color bot = isEnabled() ? new Color(180, 100,  0) : new Color(45, 45, 55);
                g2.setPaint(new GradientPaint(0, 0, top, 0, getHeight(), bot));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(isEnabled() ? new Color(50, 25, 0) : new Color(110, 110, 120));
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String t = getText();
                g2.drawString(t, (getWidth() - fm.stringWidth(t)) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        btnLancia.setPreferredSize(new Dimension(230, 52));
        btnLancia.setMaximumSize(new Dimension(230, 52));
        btnLancia.setContentAreaFilled(false);
        btnLancia.setBorderPainted(false);
        btnLancia.setFocusPainted(false);
        btnLancia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLancia.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLancia.addActionListener(e -> giocaTurno());
        side.add(btnLancia);

        return side;
    }

    private JPanel buildPlayerCard(int idx) {
        Giocatore p = giocatori.get(idx);
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = antialias(g);
                g2.setColor(CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.setColor(p.colore);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 13, 13);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(238, 78));
        card.setPreferredSize(new Dimension(238, 78));
        card.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        card.setOpaque(false);

        card.add(styledLabel(p.nome, 14, Font.BOLD, p.colore));
        moneyLabels[idx] = styledLabel("$" + p.soldi, 13, Font.BOLD, MONEY_GRN);
        propLabels[idx]  = styledLabel("Proprietà: 0", 11, Font.PLAIN, new Color(148, 163, 184));
        card.add(moneyLabels[idx]);
        card.add(propLabels[idx]);
        return card;
    }

    private JLabel makeDieLabel() {
        JLabel l = new JLabel("?", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = antialias(g);
                g2.setColor(new Color(245, 235, 200));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.setColor(new Color(60, 60, 60));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                super.paintComponent(g);
            }
        };
        l.setFont(new Font("Serif", Font.BOLD, 34));
        l.setForeground(new Color(20, 20, 20));
        l.setPreferredSize(new Dimension(62, 62));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setVerticalAlignment(SwingConstants.CENTER);
        l.setOpaque(false);
        return l;
    }

    // ── GAME LOGIC ───────────────────────────────────────────────────────────
 private void giocaTurno() {
    Giocatore g = giocatori.get(turnoCorrente);
    int d1 = (int)(Math.random() * 6) + 1;
    int d2 = (int)(Math.random() * 6) + 1;
    
    String[] facce = {"⚀","⚁","⚂","⚃","⚄","⚅"};
    dice1Label.setText(facce[d1 - 1]);
    dice2Label.setText(facce[d2 - 1]);

    boolean doppietto = (d1 == d2);

    // --- GESTIONE PRIGIONE ---
    if (g.inPrigione) {
        if (doppietto) {
            g.inPrigione = false;
            g.turniInPrigione = 0;
            JOptionPane.showMessageDialog(this, g.nome + " ha fatto un doppietto ed esce di prigione!");
            // Dopo il doppietto il giocatore si muove normalmente
        } else {
            g.turniInPrigione++;
            if (g.turniInPrigione >= 3) {
                g.soldi -= 200;
                g.inPrigione = false;
                g.turniInPrigione = 0;
                JOptionPane.showMessageDialog(this, g.nome + " ha scontato 3 turni. Paga $200 ed esce.");
            } else {
                JOptionPane.showMessageDialog(this, g.nome + " resta in prigione (Tentativo " + g.turniInPrigione + "/3)");
                passaTurno();
                return; // Il turno finisce qui
            }
        }
    }

    // --- MOVIMENTO NORMALE ---
    int oldPos = g.posizione;
    g.posizione = (oldPos + d1 + d2) % 40;

    // Controllo transito dal VIA
    if (g.posizione < oldPos) {
        g.soldi += 200;
        // ... (tuo codice messaggio VIA)
    }

    // Controllo se è finito sulla casella "VAI IN PRIGIONE" (ID 30)
    if (g.posizione == 30) {
        g.posizione = 10; // Casella Prigione
        g.inPrigione = true;
        g.turniInPrigione = 0;
        JOptionPane.showMessageDialog(this, g.nome + " va in Prigione!");
        passaTurno();
        return;
    }

    tabellone.get(g.posizione).eseguiAzione(g);
    aggiornaUI();

    // Controllo Bancarotta
    if (g.soldi <= 0) {
        // ... (tuo codice fine partita)
        return;
    }

    passaTurno();
}

private void passaTurno() {
    turnoCorrente = (turnoCorrente + 1) % giocatori.size();
    turnoLabel.setText("Turno di: " + giocatori.get(turnoCorrente).nome);
    boardPanel.repaint();
}

    private void aggiornaUI() {
        for (int i = 0; i < giocatori.size(); i++) {
            Giocatore p = giocatori.get(i);
            moneyLabels[i].setText("$" + p.soldi);
            propLabels[i].setText("Proprietà: " + p.proprieta.size());
        }
        boardPanel.repaint();
    }

    // ── STATIC HELPERS ───────────────────────────────────────────────────────

    private static JPanel darkPanel() {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                antialias(g).setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private static JLabel styledLabel(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(color);
        return l;
    }

    private static Graphics2D antialias(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    // ── BOARD PANEL ──────────────────────────────────────────────────────────

    private class BoardPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = antialias(g);

            g2.setColor(BOARD_GRN);
            g2.fillRect(0, 0, BOARD, BOARD);

            drawCenter(g2);

            for (int i = 0; i < 40; i++) drawSquare(g2, i);

            for (int i = 0; i < giocatori.size(); i++) drawToken(g2, i);
        }

        private void drawCenter(Graphics2D g2) {
            int cx = CORNER, cy = CORNER, cw = BOARD - 2 * CORNER, ch = BOARD - 2 * CORNER;
            g2.setColor(new Color(15, 75, 35));
            g2.fillRect(cx, cy, cw, ch);

            g2.setFont(new Font("Arial", Font.BOLD, 58));
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(new Color(255, 210, 0, 55));
            String title = "MONOPOLI";
            g2.drawString(title, cx + (cw - fm.stringWidth(title)) / 2, cy + ch / 2 - 10);

            g2.setFont(new Font("Arial", Font.PLAIN, 15));
            fm = g2.getFontMetrics();
            g2.setColor(new Color(255, 255, 255, 40));
            String sub = "Il Gioco delle Proprietà";
            g2.drawString(sub, cx + (cw - fm.stringWidth(sub)) / 2, cy + ch / 2 + 28);

            g2.setColor(new Color(255, 210, 0, 30));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(cx + 8, cy + 8, cw - 16, ch - 16);
        }

        private void drawSquare(Graphics2D g2, int i) {
            Rectangle2D r = squareRects[i];
            Casella c = tabellone.get(i);

            Color bg = SQ_BG;
            if      (c.tipo == TipoCasella.VIA)            bg = new Color(198, 246, 213);
            else if (c.tipo == TipoCasella.PARCHEGGIO)     bg = new Color(198, 240, 198);
            else if (c.tipo == TipoCasella.PRIGIONE)       bg = new Color(255, 252, 200);
            else if (c.tipo == TipoCasella.VAI_IN_PRIGIONE) bg = new Color(255, 205, 205);
            else if (c.tipo == TipoCasella.TASSA)          bg = new Color(255, 215, 210);

            g2.setColor(bg);
            g2.fill(r);

            if (c.coloreGruppo != null)            drawBand(g2, r, i, c.coloreGruppo);
            if (c.tipo == TipoCasella.FERROVIA)    drawBand(g2, r, i, new Color(40,  40,  40));
            if (c.tipo == TipoCasella.UTILITA)     drawBand(g2, r, i, new Color(160, 160, 160));

            g2.setColor(SQ_BORDER);
            g2.setStroke(new BasicStroke(0.7f));
            g2.draw(r);

            drawOwnerDot(g2, r, i, c);
            drawContent(g2, r, i, c);
        }

        private void drawBand(Graphics2D g2, Rectangle2D r, int i, Color color) {
            g2.setColor(color);
            if      (i >= 1  && i <= 9)  g2.fill(new Rectangle2D.Double(r.getX(), r.getY(), r.getWidth(), BAND));
            else if (i >= 11 && i <= 19) g2.fill(new Rectangle2D.Double(r.getX() + r.getWidth() - BAND, r.getY(), BAND, r.getHeight()));
            else if (i >= 21 && i <= 29) g2.fill(new Rectangle2D.Double(r.getX(), r.getY() + r.getHeight() - BAND, r.getWidth(), BAND));
            else if (i >= 31 && i <= 39) g2.fill(new Rectangle2D.Double(r.getX(), r.getY(), BAND, r.getHeight()));
        }

        private void drawOwnerDot(Graphics2D g2, Rectangle2D r, int i, Casella c) {
            Giocatore owner = null;
            if (c instanceof Proprieta)  owner = ((Proprieta)c).proprietario;
            else if (c instanceof Ferrovia) owner = ((Ferrovia)c).proprietario;
            else if (c instanceof Utilita)  owner = ((Utilita)c).proprietario;
            if (owner == null) return;

            double dx, dy;
            if      (i >= 1  && i <= 9)  { dx = r.getX() + 2;                    dy = r.getY() + 2; }
            else if (i >= 11 && i <= 19) { dx = r.getX() + r.getWidth() - BAND + 2; dy = r.getY() + 2; }
            else if (i >= 21 && i <= 29) { dx = r.getX() + 2;  dy = r.getY() + r.getHeight() - BAND + 2; }
            else if (i >= 31 && i <= 39) { dx = r.getX() + 2;                    dy = r.getY() + 2; }
            else                         { dx = r.getX() + 4;                    dy = r.getY() + 4; }

            g2.setColor(owner.colore.brighter());
            g2.fill(new Ellipse2D.Double(dx, dy, 10, 10));
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(new Ellipse2D.Double(dx, dy, 10, 10));
        }

        private void drawContent(Graphics2D g2, Rectangle2D r, int i, Casella c) {
            double tx = r.getX(), ty = r.getY(), tw = r.getWidth(), th = r.getHeight();
            if      (i >= 1  && i <= 9)  { ty += BAND; th -= BAND; }
            else if (i >= 11 && i <= 19) { tw -= BAND; }
            else if (i >= 21 && i <= 29) { th -= BAND; }
            else if (i >= 31 && i <= 39) { tx += BAND; tw -= BAND; }

            // Corners get special treatment
            if (i == 0)  { drawCorner(g2, r, "VIA!",         "GO →",      new Color(0,  120,  0)); return; }
            if (i == 10) { drawCorner(g2, r, "PRIGIONE",     "In visita", new Color(100, 70,  0)); return; }
            if (i == 20) { drawCorner(g2, r, "PARCHEGGIO",   "Gratuito",  new Color(0,  100,  0)); return; }
            if (i == 30) { drawCorner(g2, r, "VAI IN",       "PRIGIONE!", new Color(160,  0,  0)); return; }

            g2.setColor(new Color(25, 25, 25));

            // Name with icon
            String name = squareIcon(c.tipo) + c.nome;
            drawWrapped(g2, name, tx, ty, tw, th * 0.72, 6.5f);

            // Price near edge
            String price = priceOf(c);
            if (price != null) {
                g2.setFont(new Font("Arial", Font.PLAIN, 6));
                g2.setColor(new Color(80, 80, 80));
                FontMetrics fm = g2.getFontMetrics();
                int pw = fm.stringWidth(price);
                int px = (int)(tx + (tw - pw) / 2);
                int py = (i >= 21 && i <= 29)
                        ? (int)(ty + fm.getAscent() + 2)
                        : (int)(ty + th - 3);
                g2.drawString(price, px, py);
            }
        }

        private void drawCorner(Graphics2D g2, Rectangle2D r, String l1, String l2, Color c) {
            int cx = (int)(r.getX() + r.getWidth()  / 2);
            int cy = (int)(r.getY() + r.getHeight() / 2);
            g2.setColor(c);
            g2.setFont(new Font("Arial", Font.BOLD, 9));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(l1, cx - fm.stringWidth(l1) / 2, cy - 4);
            g2.setFont(new Font("Arial", Font.PLAIN, 8));
            fm = g2.getFontMetrics();
            g2.drawString(l2, cx - fm.stringWidth(l2) / 2, cy + 10);
        }

        private void drawWrapped(Graphics2D g2, String text, double tx, double ty, double tw, double th, float size) {
            g2.setFont(new Font("Arial", Font.BOLD, (int)size));
            FontMetrics fm = g2.getFontMetrics();
            List<String> lines = wrapWords(text, fm, (int)tw - 4);
            int lh = fm.getHeight();
            int sy = (int)(ty + (th - lines.size() * lh) / 2 + fm.getAscent());
            for (String line : lines) {
                g2.drawString(line, (int)(tx + (tw - fm.stringWidth(line)) / 2), sy);
                sy += lh;
            }
        }

        private void drawToken(Graphics2D g2, int idx) {
            Giocatore p = giocatori.get(idx);
            Rectangle2D r = squareRects[p.posizione];

            boolean shared = giocatori.stream()
                    .anyMatch(x -> x != p && x.posizione == p.posizione);
            double cx = r.getCenterX() + (shared ? (idx == 0 ? -9 : 9) : 0);
            double cy = r.getCenterY();
            double rad = 11;

            g2.setColor(new Color(0, 0, 0, 70));
            g2.fill(new Ellipse2D.Double(cx - rad + 1, cy - rad + 2, rad * 2, rad * 2));

            g2.setColor(p.colore);
            g2.fill(new Ellipse2D.Double(cx - rad, cy - rad, rad * 2, rad * 2));

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new Ellipse2D.Double(cx - rad, cy - rad, rad * 2, rad * 2));

            g2.setFont(new Font("Arial", Font.BOLD, 9));
            FontMetrics fm = g2.getFontMetrics();
            String init = p.nome.substring(0, 1);
            g2.drawString(init, (int)(cx - fm.stringWidth(init) / 2.0), (int)(cy + fm.getAscent() / 2.0 - 1));
        }

        private List<String> wrapWords(String text, FontMetrics fm, int maxW) {
            List<String> lines = new ArrayList<>();
            StringBuilder cur = new StringBuilder();
            for (String w : text.split(" ")) {
                String test = cur.length() == 0 ? w : cur + " " + w;
                if (fm.stringWidth(test) <= maxW) { cur = new StringBuilder(test); }
                else { if (cur.length() > 0) lines.add(cur.toString()); cur = new StringBuilder(w); }
            }
            if (cur.length() > 0) lines.add(cur.toString());
            if (lines.isEmpty())  lines.add(text);
            return lines;
        }

        private String squareIcon(TipoCasella t) {
            return switch (t) {
                case FERROVIA    -> "🚂 ";
                case UTILITA     -> "⚡ ";
                case TASSA       -> "💸 ";
                case PROBABILITA -> "? ";
                case IMPREVISTI  -> "! ";
                default          -> "";
            };
        }

        private String priceOf(Casella c) {
            if (c instanceof Proprieta p)    return "$" + p.prezzo;
            if (c instanceof Ferrovia)       return "$200";
            if (c instanceof Utilita)        return "$150";
            if (c instanceof CasellaTassa t) return "$" + t.importo;
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Monopoli().setVisible(true));
    }
}
