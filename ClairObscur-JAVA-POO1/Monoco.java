import java.util.Random;

public class Monoco extends Combatente {

    private int grilhoesNoAlvo;
    private Random random;

    public Monoco() {
        super("Monoco", 170, 0.10); 
        this.grilhoesNoAlvo = 0;
        this.random = new Random();
    }

    // ataque básico:
    // causa um dano base normal
    // aplica entre 1 e 10 grilhões no oponente
    @Override
    public void ataqueBasico(Combatente alvo) {
        int danoBase = 10;

        System.out.printf("%s lança GRILHÕES DE ALMA em %s!%n", getNome(), alvo.getNome());

        alvo.receberDano(danoBase);

        int novosGrilhoes = 1 + random.nextInt(10); 
        grilhoesNoAlvo += novosGrilhoes;

        System.out.printf(
            "[Grilhões] %s aplicou +%d grilhão(ões). Total atual no alvo: %d%n",
            getNome(), novosGrilhoes, grilhoesNoAlvo
        );
    }

    @Override
    public void habilidadeEspecial(Combatente alvo) {
        julgamento(alvo);
    }

    public void julgamento(Combatente alvo) {
        int baseDano = 24;
        int qtd = grilhoesNoAlvo;

        double multiplicador = 1.0 + 0.05 * qtd; // +5% de dano para cada grilhão
        int danoFinal = (int) Math.round(baseDano * multiplicador);

        System.out.printf(
            "%s usa JULGAMENTO em %s! Grilhões: %d: dano final: %d%n",
            getNome(), alvo.getNome(), qtd, danoFinal
        );

        alvo.receberDano(danoFinal);

        grilhoesNoAlvo = 0;
        System.out.println("[Grilhões] Todos os grilhões foram consumidos pelo JULGAMENTO.");
    }

    public void sentenca() { // habilidade de cura tbm baseada nos grilhões
        int baseCura = 15;
        int qtd = grilhoesNoAlvo;

        if (qtd == 0) {
            System.out.printf("%s tentou usar SENTENÇA, mas não existem grilhões acumulados! Cura mínima usada.%n", getNome());
            this.curar(baseCura);
            return;
        }

        double multiplicador = 1.0 + 0.10 * qtd; // cura aumenta 10% por grilhão
        int curaFinal = (int) Math.round(baseCura * multiplicador);

        System.out.printf(
            "%s invoca SENTENÇA! Grilhões: %d: cura final: %d%n",
            getNome(), qtd, curaFinal
        );

        this.curar(curaFinal);

        grilhoesNoAlvo = 0;
        System.out.println("[Grilhões] Todos os grilhões foram consumidos pela SENTENÇA.");
    }

    // redução de dano recebido com base nos grilhões aplicados ao oponente, tipo uma passiva
    @Override
    public void receberDano(int dano) {
        double multiplicador = 1.0;

        if (grilhoesNoAlvo > 0) {
            double reducao = 0.005 * grilhoesNoAlvo; // 0,5% por grilhão
            double reducaoPercent = reducao * 100.0;
            multiplicador = 1.0 - reducao;
            if (multiplicador < 0) {
                multiplicador = 0;
            }

            System.out.printf(
                "[Grilhões] %s reduz o dano recebido em %.1f%% graças a %d grilhão(ões).%n",
                getNome(), reducaoPercent, grilhoesNoAlvo
            );
        }

        int danoAjustado = (int) Math.round(dano * multiplicador);
        super.receberDano(danoAjustado);
    }

    @Override
    public void resetarEstadoDeCombate() {
        super.resetarEstadoDeCombate();
        grilhoesNoAlvo = 0;
    }

    @Override
    public String descricao() {
        return String.format(
            "Monoco - Carcereiro de Almas | Vida: %d/%d | Defesa: %.0f%% | Nível: %d ",
            getVidaAtual(),
            getVidaMaxima(),
            getDefesaPercentual() * 100,
            getNivel()
        );
    }

    public int getGrilhoesNoAlvo() {
        return grilhoesNoAlvo;
    }
}
