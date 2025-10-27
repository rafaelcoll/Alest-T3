import java.util.Scanner;

public class TesteArvore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArvoreBinaria arvore = new ArvoreBinaria();
        System.out.println("Teste Arvore Binária. Digite 'quit' ou 'q' para sair.");
        System.out.println("Comandos: ins <valor>, rem <posição>, del <posição>, pos <posição>, find <valor>");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String command = input.split(" ")[0].trim();
            String argument = input.contains(" ") ? input.split(" ")[1].trim() : null;
            if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q"))
                break;
            try {
                switch (command) {
                    case "ins":
                        if (argument != null)
                            arvore.insert(argument);
                        break;
                    case "rem":
                    case "del":
                        if (argument != null)
                            arvore.remove(argument);
                        break;
                    case "find":
                        if (argument != null)
                            System.out.println("Posição = " + arvore.find(argument));
                        break;
                    default:
                        System.out.println("Comando inválido.");
                }
            } catch (Exception e) {
                System.out.println("Erro: operação inválida ou lista vazia.");
            }

            // System.out.println(arvore.toString());
            arvore.print();
        }

        scanner.close();
    }
}
