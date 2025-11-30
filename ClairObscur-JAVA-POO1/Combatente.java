public abstract class Combatente {

    private String nome;
    private int vidaMaxima;
    private int vidaAtual;
    private double defesaPercentual;
    private int nivel;
    private int xp;

    public Combatente(String nome, int vidaMaxima, double defesaPercentual) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesaPercentual = defesaPercentual;
        this.nivel = 1;
        this.xp = 0;
    }

    // ataque básico IGUAL para todos os personagens
    public void ataqueBasico(Combatente alvo) {
        int danoBase = 10;
        System.out.printf("%s usa ATAQUE BASICO em %s!%n", nome, alvo.getNome());
        alvo.receberDano(danoBase);
    }

    public void receberDano(int dano) {
        double multiplicador = 1.0 - defesaPercentual;
        if (multiplicador < 0) {
            multiplicador = 0;
        }

        int danoFinal = (int) Math.round(dano * multiplicador);
        if (danoFinal < 0) {
            danoFinal = 0;
        }

        vidaAtual -= danoFinal;
        if (vidaAtual < 0) {
            vidaAtual = 0;
        }

        System.out.printf(
            "%s recebeu %d de dano (defesa: %.0f%%). Vida atual: %d/%d%n",
            nome, danoFinal, defesaPercentual * 100, vidaAtual, vidaMaxima
        );
    }

    // habilidade especial: cada classe concreta implementa mesmo que possua mais de uma
    public abstract void habilidadeEspecial(Combatente alvo);

    public abstract String descricao();

    public void ganharXp(int quantidade) { //apenas visual, não afetam elementos do jogo, estilo mortal kombat, cada personagem é único e sem upgrades
        xp += quantidade;
        System.out.printf("%s ganhou %d de XP. (XP atual: %d)%n", nome, quantidade, xp);

        int limiteXp = 100; 
        while (xp >= limiteXp) {
            xp -= limiteXp;
            nivel++;
            System.out.printf("%s subiu para o NIVEL %d!%n", nome, nivel);
        }
    }

    public void restaurarVidaTotal() {
        vidaAtual = vidaMaxima;
    }

    public void curar(int quantidade) {
        this.vidaAtual += quantidade;

        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }

        System.out.printf(
            "%s recuperou %d de vida. Vida atual: %d/%d%n",
            nome, quantidade, vidaAtual, vidaMaxima
        );
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public double getDefesaPercentual() {
        return defesaPercentual;
    }

    public int getNivel() {
        return nivel;
    }

    public int getXp() {
        return xp;
    }

    public void resetarEstadoDeCombate() {
        // por padrão, só restaura a vida mas subclasses podem sobrescrever para limpar estados próprios (sobrecarga, elementos, etc) 
        restaurarVidaTotal();
    }


    public boolean estaVivo() {
        return vidaAtual > 0;
    }
}
