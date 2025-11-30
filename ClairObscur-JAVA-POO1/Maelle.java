import java.util.Random;

public class Maelle extends Combatente {

    private enum Pose {
        NENHUMA,
        DEFENSIVA,
        OFENSIVA,
        ILUMINADA
    }

    private Pose poseAtual;
    private Random random;

    public Maelle() {
        super("Maelle", 180, 0.10); 
        this.poseAtual = Pose.NENHUMA;
        this.random = new Random();
    }

    // ataque básico:
    // se não tiver pose, ganha uma pose aleatória
    // se já tiver pose, mantém a mesma
    // dano é modificado pela pose
    @Override
    public void ataqueBasico(Combatente alvo) {

        if (poseAtual == Pose.NENHUMA) {
            sortearPose();
        }

        int danoBase = 10;
        double multiplicadorAtaque = calcularMultiplicadorAtaque();

        int danoFinal = (int) Math.round(danoBase * multiplicadorAtaque);

        System.out.printf(
            "%s ataca em pose %s! Dano base: %d, multiplicador: x%.2f, dano final: %d%n",
            getNome(),
            poseAtual.name(),
            danoBase,
            multiplicadorAtaque,
            danoFinal
        );

        alvo.receberDano(danoFinal);
    }

    // habilidade especial é um ataque forte que CONSOME a pose
    @Override
    public void habilidadeEspecial(Combatente alvo) {

        int danoBase = 25;

        double multiplicadorAtaque = calcularMultiplicadorAtaque();
        int danoFinal = (int) Math.round(danoBase * multiplicadorAtaque);

        System.out.printf(
            "%s usa GOLPE DAS POSES em pose %s contra %s! Dano base: %d, mult: x%.2f, dano final: %d%n",
            getNome(),
            poseAtual.name(),
            alvo.getNome(),
            danoBase,
            multiplicadorAtaque,
            danoFinal
        );

        alvo.receberDano(danoFinal);

        poseAtual = Pose.NENHUMA;
        System.out.printf("%s retorna ao estado NEUTRO, sem pose.%n", getNome());
    }

    @Override
    public void receberDano(int dano) {
        double multiplicador = 1.0;

        if (poseAtual == Pose.DEFENSIVA) {
            multiplicador *= 0.7;
        } else if (poseAtual == Pose.ILUMINADA) {
            multiplicador *= 1.5;
        }

        int danoAjustado = (int) Math.round(dano * multiplicador);

        System.out.printf(
            "%s está na pose %s. Modificador de dano recebido: x%.2f (dano ajustado: %d)%n",
            getNome(),
            poseAtual.name(),
            multiplicador,
            danoAjustado
        );

        super.receberDano(danoAjustado);
    }

    @Override
    public void resetarEstadoDeCombate() {
        super.resetarEstadoDeCombate();
        poseAtual = Pose.NENHUMA;
    }

    private void sortearPose() {
        int sorteio = random.nextInt(3); 
        switch (sorteio) {
            case 0:
                poseAtual = Pose.DEFENSIVA;
                break;
            case 1:
                poseAtual = Pose.OFENSIVA;
                break;
            case 2:
            default:
                poseAtual = Pose.ILUMINADA;
                break;
        }
        System.out.printf("%s entra na pose %s!%n", getNome(), poseAtual.name());
    }

    private double calcularMultiplicadorAtaque() {
        switch (poseAtual) {
            case OFENSIVA:
                return 1.3;
            case ILUMINADA:
                return 2.3;
            case DEFENSIVA:
            case NENHUMA:
            default:
                return 1.0;
        }
    }

    @Override
    public String descricao() {
        return String.format(
            "Maelle - Mestra das Poses | Vida: %d/%d | Defesa: %.0f%% | Nível: %d ",
            getVidaAtual(),
            getVidaMaxima(),
            getDefesaPercentual() * 100,
            getNivel()
            
        );
    }
}
