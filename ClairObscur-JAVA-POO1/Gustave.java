import java.util.Random;

public class Gustave extends Combatente {
    private int cargaSobrecarga;  // 0 a 10
    private Random random;

    public Gustave() {
        super("Gustave", 200, 0.15);
        this.cargaSobrecarga = 0;
        this.random = new Random();
    }

    @Override
    public void resetarEstadoDeCombate() {
        super.resetarEstadoDeCombate();
        this.cargaSobrecarga = 0;
    }

    // ataque básico:
    // causa um dano base normal
    // aplica entre 1 e 4 cargas
    @Override
    public void ataqueBasico(Combatente alvo) {
        super.ataqueBasico(alvo);

        int ganho = 1 + random.nextInt(4); 
        cargaSobrecarga += ganho;
        if (cargaSobrecarga > 10) {
            cargaSobrecarga = 10;
        }

        System.out.printf(
            "[Sobrecarga] %s ganhou +%d de carga. Carga atual: %d/10%n",
            getNome(), ganho, cargaSobrecarga
        );
    }

    @Override
    public void habilidadeEspecial(Combatente alvo) {

        if (cargaSobrecarga <= 0) {
            System.out.printf(
                "%s tentou usar SOBRECARGA, mas não tem carga acumulada!%n",
                getNome()
            );
            System.out.println("Um ataque básico será usado no lugar.\n");
            super.ataqueBasico(alvo);
            return;
        }

        // base da Sobrecarga
        int danoBase = 10;

        // crescimento exponencial com base 1.25^carga

        double multiplicador = Math.pow(1.25, cargaSobrecarga);
        int danoFinal = (int) Math.round(danoBase * multiplicador);

        System.out.printf(
            "%s libera SOBRECARGA nível %d contra %s! Dano final: %d%n",
            getNome(), cargaSobrecarga, alvo.getNome(), danoFinal
        );

        alvo.receberDano(danoFinal);

        cargaSobrecarga = 0;
        System.out.printf(
            "[Sobrecarga] A carga de %s foi resetada para %d/10.%n",
            getNome(), cargaSobrecarga
        );
    }

    @Override
    public String descricao() {
        return String.format(
            "Gustave - Guerreiro Carregado | Vida: %d/%d  | Defesa: %.0f%% | Nivel: %d",
            getVidaAtual(),
            getVidaMaxima(),
            getDefesaPercentual() * 100,
            getNivel()
        );
    }
}
