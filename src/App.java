import java.io.FileReader;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class App {
    static ArvoreBinaria arvore = new ArvoreBinaria();

    public static void main(String[] args) throws Exception {
        System.out.println("== Salvando as árvores ==");

        if (args.length == 1) {
            processaArquivo(args[0]);
            return;
        }

        interfacePrompt();
    }

    private static void processaArquivo(String arquivo) {
        System.out.println("Processando arquivo: " + arquivo);
        String entrada;
        String ordenacao;

        try (FileReader reader = new FileReader(arquivo);
                Scanner fileScanner = new Scanner(reader)) {
            entrada = fileScanner.nextLine();
            ordenacao = fileScanner.nextLine();
            arvore = new ArvoreBinaria(ordenacao.split(" "));

            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Erro ao processar o arquivo " + arquivo + ": " + e.getMessage());
            return;
        }

        long inicioExecucao = System.nanoTime();
        arvore.insert(entrada.split(" "));
        long fimExecucao = System.nanoTime();
        double tempoExecucao = (fimExecucao - inicioExecucao) / 1_000_000.0;

        System.out.println("Fim do processamento do arquivo.");
        // arvore.print();

        System.out.println("\n=== Resultados ===");
        System.out.println("Caso de teste: " + arquivo);
        System.out.println("Tempo de processamento: "
                + String.format(Locale.forLanguageTag("pt-BR"), "%.6f", tempoExecucao) + " ms");
        System.out.println("Altura: " + arvore.altura());
        System.out.println("Quantidade de nodos: " + arvore.size());
        System.out.println("Validação caminhamento pré-fixado: " + Objects.equals(arvore.toStringPreFixado(), entrada));
        System.out.println("Validação caminhamento central: " + Objects.equals(arvore.toStringCentral(), ordenacao));

        return;
    }

    private static void interfacePrompt() {
        exibeHelp();

        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.print("> ");
            input = scanner.nextLine().trim();
            // System.out.println("Você digitou: " + input);

            if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("h")) {
                exibeHelp();
                continue;
            }

            processaEntrada(input);
            // arvore.print();
        } while (!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("q"));

        exibeEstadoFinal();
        scanner.close();
    }

    private static void processaEntrada(String input) {
        // Ignora entradas vazias
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String[] comandos = input.toLowerCase().split(" ");

        switch (comandos[0]) {
            case "find":
                String valorParaBuscar = comandos.length == 2 ? comandos[1].trim() : "";
                if (valorParaBuscar.isEmpty()) {
                    System.out.println("Comando 'find' requer um valor para buscar.");
                    return;
                }
                if (arvore.find(valorParaBuscar) == null) {
                    System.out.println("Valor '" + valorParaBuscar + "' não encontrado na árvore.");
                } else {
                    System.out.println("Valor '" + valorParaBuscar + "' encontrado na árvore.");
                }
                break;
            case "del":
                String valorParaDeletar = comandos.length == 2 ? comandos[1].trim() : "";
                if (valorParaDeletar.isEmpty()) {
                    System.out.println("Comando 'del' requer um valor para deletar.");
                    return;
                }
                if (arvore.remove(valorParaDeletar)) {
                    System.out.println("Valor '" + valorParaDeletar + "' removido da árvore.");
                } else {
                    System.out.println("Valor '" + valorParaDeletar + "' não encontrado para remoção.");
                    return;
                }
                break;
            case "alt":
                System.out.println("Altura da árvore: " + arvore.altura());
                break;
            case "print":
                break;
            case "size":
            case "cont":
                System.out.println("Contagem de nós na árvore: " + arvore.size());
                break;
            case "ins":
                String valorParaInserir = comandos.length == 2 ? comandos[1].trim() : "";
                if (valorParaInserir.isEmpty()) {
                    System.out.println("Comando 'ins' requer um valor para inserir.");
                    return;
                } else {
                    // System.out.println("Inserindo o valor: " + valorParaInserir);
                    arvore.insert(valorParaInserir);
                }
                break;
            default:
                String[] valoresParaInserir = comandos;
                for (String valor : valoresParaInserir) {
                    if (valor.trim().isEmpty())
                        continue;
                    System.out.println("Inserindo o valor: " + valor);
                    arvore.insert(valor);
                }
                break;
        }

        arvore.print();
    }

    private static void exibeHelp() {
        System.out.println("Digite um valor de entrada ou um dos comandos abaixo.");
        System.out.println("'ins', 'find', 'del' <número> - insere, busca ou deleta o número na árvore");
        System.out.println("'alt' exibe a altura da árvore");
        System.out.println("'print' exibe a árvore");
        System.out.println("Digite 'q' ou 'quit' para sair do programa.");
        System.out.println("Digite 'h' ou 'help' para exibir essa mensagem novamente.");
    }

    private static void exibeEstadoFinal() {
        System.out.println("Estado final da árvore: " + arvore.toString());
        System.out.println("Altura final da árvore: " + arvore.altura());
        System.out.println("Encerrando a árvore.");
    }
}
