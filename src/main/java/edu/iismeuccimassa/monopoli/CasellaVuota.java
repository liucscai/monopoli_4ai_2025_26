package edu.iismeuccimassa.monopoli;

import javax.swing.*;
//classe CasellaVuota che eredita da Casella
public class CasellaVuota extends Casella {
    CasellaVuota(String nome, TipoCasella tipo) {
        super(nome, tipo, null);
    }
    //le CaselleVuote sono le caselle che attualmente non fanno niente ad attivazione di effetto
    //questo metodo attualmente controlla che se il giocatore è finito sulla casella vai in prigione lo posiziona lì
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
