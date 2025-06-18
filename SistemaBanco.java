package A3_Banco;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaBanco {
    private static final String ARQUIVO_DADOS = "dados.ser";

    public static void salvarDados(List<Usuario> usuarios) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            out.writeObject(usuarios);
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Usuario> carregarDados() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {
            return (List<Usuario>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum arquivo de dados encontrado. Iniciando com lista vazia.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        List<Usuario> usuarios = carregarDados();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Criar Usuário");
            System.out.println("2 - Fazer Login");
            System.out.println("3 - Acessar Suporte");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarUsuario(scanner, usuarios);
                    break;
                case 2:
                    fazerLogin(scanner, usuarios);
                    break;
                case 3:
                    Suporte.abrirMenuSuporte();
                    break;
                case 4:
                    salvarDados(usuarios);
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void criarUsuario(Scanner scanner, List<Usuario> usuarios) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario novoUsuario = new Usuario(nome, senha);
        usuarios.add(novoUsuario);

        salvarDados(usuarios);
        System.out.println("Usuário criado com sucesso!");
    }

    private static void fazerLogin(Scanner scanner, List<Usuario> usuarios) {
        System.out.print("Nome de usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome) && usuario.getSenha().equals(senha)) {
                System.out.println("Login bem-sucedido! Bem-vindo, " + nome + "!");
                menuConta(scanner, usuario, usuarios);
                return;
            }
        }

        System.out.println("Nome de usuário ou senha incorretos.");
    }

    private static void menuConta(Scanner scanner, Usuario usuario, List<Usuario> usuarios) {
        int opcao;

        do {
            System.out.println("\nMenu da Conta:");
            System.out.println("1 - Ver Saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Ver Histórico de PIX");
            System.out.println("5 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    mostrarSaldo(usuario);
                    break;
                case 2:
                    depositar(scanner, usuario, usuarios);
                    break;
                case 3:
                    sacar(scanner, usuario, usuarios);
                    break;
                case 4:
                    mostrarHistoricoPix(usuario);
                    break;
                case 5:
                    salvarDados(usuarios);
                    System.out.println("Saindo da conta...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    private static void mostrarSaldo(Usuario usuario) {
        for (Conta conta : usuario.getContas()) {
            System.out.println("Conta: " + conta.getNumero() + " | Saldo: R$" + conta.getSaldo());
        }
    }

    private static void depositar(Scanner scanner, Usuario usuario, List<Usuario> usuarios) {
        if (usuario.getContas().isEmpty()) {
            System.out.println("Você não tem contas cadastradas.");
            return;
        }

        Conta conta = escolherConta(scanner, usuario);
        if (conta != null) {
            System.out.print("Valor para depositar: R$");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
            salvarDados(usuarios);
            System.out.println("Depósito realizado com sucesso!");
        }
    }

    private static void sacar(Scanner scanner, Usuario usuario, List<Usuario> usuarios) {
        if (usuario.getContas().isEmpty()) {
            System.out.println("Você não tem contas cadastradas.");
            return;
        }

        Conta conta = escolherConta(scanner, usuario);
        if (conta != null) {
            System.out.print("Valor para sacar: R$");
            double valor = scanner.nextDouble();
            if (conta.sacar(valor)) {
                salvarDados(usuarios);
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }
    }

    private static void mostrarHistoricoPix(Usuario usuario) {
        if (usuario.getContas().isEmpty()) {
            System.out.println("Você não tem contas cadastradas.");
            return;
        }

        for (Conta conta : usuario.getContas()) {
            System.out.println("\nHistórico da conta: " + conta.getNumero());
            if (conta.getHistoricoPix().isEmpty()) {
                System.out.println("Nenhuma transação PIX encontrada.");
            } else {
                for (TransacaoPIX pix : conta.getHistoricoPix()) {
                    System.out.println(pix);
                }
            }
        }
    }

    private static Conta escolherConta(Scanner scanner, Usuario usuario) {
        System.out.println("\nSuas contas:");
        for (int i = 0; i < usuario.getContas().size(); i++) {
            System.out.println((i + 1) + " - Conta " + usuario.getContas().get(i).getNumero());
        }
        System.out.print("Escolha o número da conta: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha >= 1 && escolha <= usuario.getContas().size()) {
            return usuario.getContas().get(escolha - 1);
        } else {
            System.out.println("Escolha inválida.");
            return null;
        }
    }
}
