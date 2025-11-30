interface IArvoreString {
    void insert(String[] lista);
    boolean remove(String value);
    NodoBinString find(String value);
    int altura();
    int size();
}

public class ArvoreBinaria implements IArvoreString {
    private NodoBinString raiz = null;
    private String[] ordenacao = null;

    public ArvoreBinaria(String[] ordenacao) {
        this.ordenacao = ordenacao;
    }

    public String[] getOrdenacao() {
        return ordenacao;
    }

    public void insert(String[] lista) {
        for (String valor : lista) {
            raiz = subInsert(raiz, valor.trim());
        }
    }

    private NodoBinString subInsert(NodoBinString p, String value) {
        if (p == null)
            return new NodoBinString(value, null, null);

        int compareResult = compare(value, p.value);
        if (compareResult < 0)
            p.left = subInsert(p.left, value);
        else if (compareResult > 0)
            p.right = subInsert(p.right, value);
        else
            return p;
        return p;
    }

    public NodoBinString find(String value) {
        return subFind(raiz, value);
    }

    private NodoBinString subFind(NodoBinString p, String value) {
        if (p == null)
            return null;

        int compareResult = compare(value, p.value);
        if (compareResult < 0)
            return subFind(p.left, value);
        else if (compareResult > 0)
            return subFind(p.right, value);
        else
            return p;
    }

    public boolean remove(String value) {
        if (find(value) == null)
            return false;
        raiz = subRemove(raiz, value);
        return true;
    }

    private NodoBinString subRemove(NodoBinString p, String value) {
        if (p == null)
            return p;

        int compareResult = compare(value, p.value);
        if (compareResult < 0) {
            p.left = subRemove(p.left, value);
            return p;
        } else if (compareResult > 0) {
            p.right = subRemove(p.right, value);
            return p;
        } else {
            if (p.left == null) {
                return p.right;
            } else if (p.right == null) {
                return p.left;
            } else {
                NodoBinString minNode = p.left;
                while (minNode.right != null) {
                    minNode = minNode.right;
                }
                p.value = minNode.value;
                p.left = subRemove(p.left, minNode.value);
            }
        }
        return p;
    }

    public int size() {
        return subSize(raiz);
    }

    private int subSize(NodoBinString node) {
        if (node == null)
            return 0;
        return 1 + subSize(node.left) + subSize(node.right);
    }

    public int altura() {
        return subAltura(raiz);
    }

    private int subAltura(NodoBinString p) {
        if (p == null)
            return 0;
        if (p.left == null && p.right == null)
            return 0;

        return 1 + Math.max(subAltura(p.left), subAltura(p.right));
    }

    private int compare(String string1, String string2) {
        // Compara string1 com string2

        // Strings são iguais
        if (string1.equals(string2))
            return 0;

        for (String s : ordenacao) {
            if (s.equals(string1))
                return -1;
            if (s.equals(string2))
                return 1;
        }

        return 0;
    }

    public void print() {
        System.out.println("Altura: " + altura());
        System.out.println("Quantidade: " + size());
        System.out.println("Árvore com profundidade:");
        subPrintCentralComProfundidade(raiz, 0);
        System.out.println("Caminhamento pré-fixado:");
        System.out.println(toStringPreFixado());
        System.out.println("Caminhamento central:");
        System.out.println(toStringCentral());
    }

    private void subPrintCentralComProfundidade(NodoBinString node, int nivel) {
        if (node == null) return;
        subPrintCentralComProfundidade(node.right, nivel + 1);
        System.out.println("   ".repeat(nivel) + node.value);
        subPrintCentralComProfundidade(node.left, nivel + 1);
    }

    public String toStringCentral() {
        StringBuilder sb = new StringBuilder();
        toStringCentral(raiz, sb);
        return sb.toString();
    }

    private void toStringCentral(NodoBinString node, StringBuilder sb) {
        if (node == null)
            return;
        toStringCentral(node.left, sb);
        sb.append(node.value).append(" ");
        toStringCentral(node.right, sb);
    }

    public String toStringPreFixado() {
        StringBuilder sb = new StringBuilder();
        toStringPreFixado(raiz, sb);
        return sb.toString();
    }

    private void toStringPreFixado(NodoBinString node, StringBuilder sb) {
        if (node == null)
            return;
        sb.append(node.value).append(" ");
        toStringPreFixado(node.left, sb);
        toStringPreFixado(node.right, sb);
    }

    // private void subPrintPorNivel(NodoBinString node) {
    //     if (node == null)
    //         return;
    //     System.out.print("\t".repeat(subSize(node.left)));
    //     if (node.left != null) {
    //         System.out.print(node.left.value);
    //     }
    //     System.out.print("\t".repeat(subAltura(node.right)));
    //     System.out.println();

    //     subPrintPorNivel(node.left);
    //     subPrintPorNivel(node.right);
    // }
}
