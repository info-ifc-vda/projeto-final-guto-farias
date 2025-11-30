import java.util.Random;

public class Lune extends Combatente {

    private enum Elemento {
        FOGO, GELO, TROVAO
    }

    private Elemento[] rodaElemental; 
    private Random random;

    public Lune() {
        super("Lune", 160, 0.05); 
        this.rodaElemental = new Elemento[3];
        this.random = new Random();
    }

    @Override
    public void resetarEstadoDeCombate() {
        super.resetarEstadoDeCombate();
        for (int i = 0; i < rodaElemental.length; i++) {
            rodaElemental[i] = null;
        }
    }


    // ataque básico:
    // causa o dano padrão
    // preenche 1 slot vazio com um elemento aleatório (FOGO/GELO/TROVAO)
    @Override
    public void ataqueBasico(Combatente alvo) {
        super.ataqueBasico(alvo);
        adicionarElementoAleatorio();
        imprimirRoda();
    }

    @Override
    public void habilidadeEspecial(Combatente alvo) {
        bolaDeFogo(alvo);
    }

    public void bolaDeFogo(Combatente alvo) {
        usarHabilidadeComElemento(alvo, Elemento.FOGO, "BOLA DE FOGO", 24);
    }

    public void novaDeGelo(Combatente alvo) {
        usarHabilidadeComElemento(alvo, Elemento.GELO, "NOVA DE GELO", 22);
    }

    public void trovao(Combatente alvo) {
        usarHabilidadeComElemento(alvo, Elemento.TROVAO, "TROVÃO", 26);
    }

    private void usarHabilidadeComElemento(Combatente alvo, Elemento tipo, String nomeHabilidade, int danoBase) {
        int quantiaElementos = contarElementos(tipo);

        double multiplicador = 1.0 + 0.66 * quantiaElementos;
        int danoFinal = (int) Math.round(danoBase * multiplicador);

        System.out.printf(
            "%s usa %s em %s! (%d marca(s) de %s, dano final: %d)%n",
            getNome(),
            nomeHabilidade,
            alvo.getNome(),
            quantiaElementos,
            tipo.name(),
            danoFinal
        );

        alvo.receberDano(danoFinal);

        removerElementos(tipo);
        imprimirRoda();
    }

    private void adicionarElementoAleatorio() {
        int indiceVazio = primeiroIndiceVazio();
        if (indiceVazio == -1) {
            System.out.println("[Roda elemental] Todos os slots já estão preenchidos.");
            return;
        }

        Elemento novoElemento = sortearElemento();
        rodaElemental[indiceVazio] = novoElemento;

        System.out.printf(
            "[Roda elemental] %s ganhou um elemento %s no slot %d.%n",
            getNome(), novoElemento.name(), indiceVazio + 1
        );
    }

    private int primeiroIndiceVazio() {
        for (int i = 0; i < rodaElemental.length; i++) {
            if (rodaElemental[i] == null) {
                return i;
            }
        }
        return -1; // sem espaço
    }

    private Elemento sortearElemento() {
        int sorteio = random.nextInt(3); 
        return switch (sorteio) {
            case 0 -> Elemento.FOGO;
            case 1 -> Elemento.GELO;
            default -> Elemento.TROVAO; // vai cair no 2 pois é a única opção que foge e vira default
        };
    }

    private int contarElementos(Elemento tipo) {
        int cont = 0;
        for (Elemento e : rodaElemental) {
            if (e == tipo) {
                cont++;
            }
        }
        return cont;
    }

    private void removerElementos(Elemento tipo) {
        for (int i = 0; i < rodaElemental.length; i++) {
            if (rodaElemental[i] == tipo) {
                rodaElemental[i] = null;
            }
        }
        System.out.printf(
            "[Roda elemental] Marcas de %s foram consumidas.%n",
            tipo.name()
        );
    }

    private void imprimirRoda() {
        System.out.print("[Roda elemental] Slots: ");
        for (int i = 0; i < rodaElemental.length; i++) {
            String simbolo;
            if (rodaElemental[i] == null) {
                simbolo = "-";
            } else if (rodaElemental[i] == Elemento.FOGO) {
                simbolo = "F";
            } else if (rodaElemental[i] == Elemento.GELO) {
                simbolo = "G";
            } else {
                simbolo = "T";
            }
            System.out.print(simbolo);
            if (i < rodaElemental.length - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    @Override
    public String descricao() {
        return String.format(
            "Lune - Maga Elemental | Vida: %d/%d | Defesa: %.0f%% | Nível: %d",
            getVidaAtual(),
            getVidaMaxima(),
            getDefesaPercentual() * 100,
            getNivel()
        );
    }
}

