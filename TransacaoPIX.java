import java.io.Serializable;
import java.time.LocalDateTime;

public class TransacaoPIX implements Serializable {
    private static final long serialVersionUID = 1L;

    private String remetente;
    private String destinatario;
    private double valor;
    private LocalDateTime dataHora;

    public TransacaoPIX(String remetente, String destinatario, double valor) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return "PIX de R$" + valor + " de " + remetente + " para " + destinatario + " em " + dataHora;
    }
}
