import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Suporte {
    private static final String ARQUIVO_SUPORTE = "suporte.txt";

    public static void abrirMenuSuporte() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu de Suporte:");
            System.out.println("1 - Denunciar Conta");
            System.out.println("2 - Relatar Fraude");
            System.out.println("3 - Fui vítima de golpe");
            System.out.println("4 - Relatar outro caso");
            System.out.println("5 - Falar com a central");
            System.out.println("6 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    registrarRelato(scanner, "Denúncia de Conta");
                    break;
                case 2:
                    registrarRelato(scanner, "Relato de Fraude");
                    break;
                case 3:
                    registrarRelato(scanner, "Vítima de Golpe");
                    break;
                case 4:
                    registrarRelato(scanner, "Outro Caso");
                    break;
                case 5:
                    falarComCentral();
                    break;
                case 6:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 6);
    }

    private static void registrarRelato(Scanner scanner, String tipo) {
        System.out.println("\nDigite os detalhes do seu relato:");
        String detalhes = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_SUPORTE, true))) {
            writer.println("Tipo de Relato: " + tipo);
            writer.println("Detalhes: " + detalhes);
            System.out.println("Seu relato foi registrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar relato: " + e.getMessage());
        }
    }

    private static void falarComCentral() {
        System.out.println("\nCentral de Atendimento:");
        System.out.println("Telefone: 0800-123-456");
        System.out.println("E-mail: suporte@banco.com");
    }
}
