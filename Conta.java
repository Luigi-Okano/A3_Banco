package A3_Banco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String numero;
    protected String titular;
    protected double saldo;
    protected List<TransacaoPIX> historicoPix = new ArrayList<>();

    public Conta(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public List<TransacaoPIX> getHistoricoPix() {
        return historicoPix;
    }

    public void adicionarTransacao(TransacaoPIX transacao) {
        historicoPix.add(transacao);
    }
}
