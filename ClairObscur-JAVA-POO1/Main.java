import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Combatente[] personagens = new Combatente[4];
        personagens[0] = new Gustave();
        personagens[1] = new Lune();
        personagens[2] = new Maelle();
        personagens[3] = new Monoco();

        boolean continuar = true;

        while (continuar) {

            System.out.println("\n==============================");
            System.out.println("      ESCOLHA DE PERSONAGEM  ");
            System.out.println("==============================");

            // mostra personagens com nível atual
            for (int i = 0; i < personagens.length; i++) {
                Combatente c = personagens[i];
                System.out.println(i+1 + "   " + c.descricao());
            }

            // escolhe ai paizao
            int escolha1 = -1;
            while (true) {
                System.out.print("\nJogador 1, escolha o numero do personagem: ");
                escolha1 = scanner.nextInt();

                if (escolha1 >= 1 && escolha1 <= personagens.length) {
                    break;
                } else {
                    System.out.println("Opcao invalida. Tente novamente.");
                }
            }

            int escolha2 = -1;
            while (true) {
                System.out.print("Jogador 2, escolha o numero do personagem (diferente do Jogador 1): ");
                escolha2 = scanner.nextInt();

                if (escolha2 >= 1 && escolha2 <= personagens.length && escolha2 != escolha1) {
                    break;
                } else {
                    System.out.println("Opcao invalida. Tente novamente.");
                }
            }

            Combatente jogador1 = personagens[escolha1 - 1];
            Combatente jogador2 = personagens[escolha2 - 1];

            System.out.println("\n==============================");
            System.out.println("        INICIO DA LUTA        ");
            System.out.println("==============================");
            System.out.printf("Jogador 1: %s (Nivel %d)%n", jogador1.getNome(), jogador1.getNivel());
            System.out.printf("Jogador 2: %s (Nivel %d)%n%n", jogador2.getNome(), jogador2.getNivel());

            boolean turnoJogador1 = true;

            // LOOP DE BATALHA
            while (jogador1.estaVivo() && jogador2.estaVivo()) {

                Combatente atacante = turnoJogador1 ? jogador1 : jogador2;
                Combatente defensor = turnoJogador1 ? jogador2 : jogador1;
                int numeroJogador = turnoJogador1 ? 1 : 2;

                System.out.println("--------------------------------");
                System.out.printf("Turno do Jogador %d (%s - Nivel %d)%n", numeroJogador, atacante.getNome(), atacante.getNivel());

                boolean atacanteEhLune = atacante instanceof Lune; // manipulaçao das habilidades
                boolean atacanteEhMonoco = atacante instanceof Monoco;

                System.out.println("1 - Ataque básico");

                if (atacanteEhLune) {
                    System.out.println("2 - Bola de Fogo");
                    System.out.println("3 - Nova de Gelo");
                    System.out.println("4 - Trovão");
                } else if (atacanteEhMonoco) {
                    System.out.println("2 - Julgamento");
                    System.out.println("3 - Sentença");
                } else {
                    System.out.println("2 - Habilidade especial");
                }

                System.out.print("Escolha sua ação: ");
                int acao = scanner.nextInt();
                System.out.println();

                if (atacanteEhLune) {

                    Lune lune = (Lune) atacante;
                    switch (acao) {
                        case 1 -> lune.ataqueBasico(defensor);
                        case 2 -> lune.bolaDeFogo(defensor);
                        case 3 -> lune.novaDeGelo(defensor);
                        case 4 -> lune.trovao(defensor);
                        default -> {
                            System.out.println("Opção inválida! Será usado ATAQUE BÁSICO por padrão.");
                            lune.ataqueBasico(defensor);
                        }
                    }

                } else if (atacanteEhMonoco) {
                    Monoco monoco = (Monoco) atacante;
                    switch (acao) {
                        case 1 -> monoco.ataqueBasico(defensor);
                        case 2 -> monoco.julgamento(defensor);
                        case 3 -> monoco.sentenca();
                        default -> {
                            System.out.println("Opção inválida! ATAQUE BÁSICO usado.");
                            monoco.ataqueBasico(defensor);
                        }
                    }    
                } else {
                    switch (acao) {
                        case 1 -> atacante.ataqueBasico(defensor);
                        case 2 -> atacante.habilidadeEspecial(defensor);
                        default -> {
                            System.out.println("Opção inválida! Será usado ATAQUE BÁSICO por padrão.");
                            atacante.ataqueBasico(defensor);
                        }
                    }
                }

                if (!defensor.estaVivo()) {
                    System.out.printf("%n*** %s foi derrotado! ***%n", defensor.getNome());
                    Combatente vencedor = atacante;
                    System.out.printf("Vitória de %s!%n", vencedor.getNome());

                    vencedor.ganharXp(100);

                    // resolvido problema de manter informações
                    jogador1.resetarEstadoDeCombate();
                    jogador2.resetarEstadoDeCombate();

                    break;
                }

                // alterna turno
                turnoJogador1 = !turnoJogador1;
            }

            System.out.print("\nDesejam jogar novamente? (s/n): ");
            String resposta = scanner.next().toLowerCase();

            if (!resposta.equals("s")) {
                continuar = false;
            }
        }

        System.out.println("\nEncerrando o jogo. Obrigado por jogar!");
        scanner.close();
    }
}
