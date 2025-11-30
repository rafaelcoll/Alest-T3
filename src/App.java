import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("== Salvando as árvores ==");
        interfacePrincipal();
    }

    private static void interfacePrincipal() {
        exibeHelp();
        
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            File[] arquivos = listaArquivosTxt();
            int indices = arquivos.length;
            System.out.print("Digite a opção desejada: ");
            input = scanner.nextLine().trim();
            // System.out.println("Você digitou: " + input); // Debug

            if (input.equalsIgnoreCase("h")) {
                exibeHelp();
                continue;
            }

            Integer option;
            try {
                option = Integer.parseInt(input);
                if (option > 0 && option <= indices) {
                    input = arquivos[option-1].getName();
                    System.out.println("Arquivo selecionado: " + input);
                } else {
                    System.out.println("Opção inválida.");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Comando inválido.");
                continue;
            }
            
            processaArquivo(input);
        } while (!input.equalsIgnoreCase("q"));

        scanner.close();
    }

    private static void exibeHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("  <número> - Processa o arquivo correspondente ao número listado");
        System.out.println("  q -        Encerra o programa");
        System.out.println("  h -        Exibe esta mensagem de ajuda");
    }

    private static File[] listaArquivosTxt() {
        System.out.println("\nLista de arquivos:");
        File dir = new File(".");
        File[] arquivos = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
        if (arquivos != null && arquivos.length > 0) {
            Arrays.sort(arquivos);
            for (int i = 1; i <= arquivos.length; i++) {
                System.out.println(i + ". " + arquivos[i-1].getName());
            }
        } else {
            System.out.println("Nenhum arquivo .txt encontrado.");
        }

        return arquivos;
    }

    private static void processaArquivo(String arquivo) {
        System.out.println("Processando arquivo: " + arquivo);
        ArvoreBinaria arvore;
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

        System.out.println("\n=== Resultado ===");
        System.out.println("Arquivo: " + arquivo);
        System.out.println("Tempo de processamento: "
                + String.format(Locale.forLanguageTag("pt-BR"), "%.6f", tempoExecucao) + " ms");
        System.out.println("Altura da árvore: " + arvore.altura());
        System.out.println("Quantidade de nodos: " + arvore.size());
        System.out.println("Validação caminhamento pré-fixado: " + Objects.equals(arvore.toStringPreFixado(), entrada));
        System.out.println("Validação caminhamento central: " + Objects.equals(arvore.toStringCentral(), ordenacao));
        System.out.println("==================");

        return;
    }
}
