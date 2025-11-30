# ClairObscur – Sistema de Batalha em Java (LPG1)

Projeto desenvolvido para a disciplina **Linguagem de Programação 1**, utilizando conceitos fundamentais de **Programação Orientada a Objetos (POO)** em Java pelos alunos DIEGO ANTONIO ROSTIROLLA, MATHEUS TROMBETTA DEGARAES e VITOR AUGUSTO FARIAS.

O jogo implementa **batalhas 1 vs 1** entre personagens com mecânicas únicas.

---

## Índice
* [Descrição Geral](#descrição-geral)
* [Funcionalidades](#funcionalidades)
* [Como Executar](#como-executar)
* [Regras Gerais](#regras-gerais)
* [Personagens](#personagens)
    * [Gustave](#gustave)
    * [Lune](#lune)
    * [Maelle](#maelle)
    * [Monoco](#monoco)
* [Arquitetura](#arquitetura)
* [Conceitos de POO Aplicados](#conceitos-de-poo-aplicados)

---

## Descrição Geral

O projeto se trata de jogo de batalha em **linha de comando**, onde dois jogadores escolhem personagens e se enfrentam utilizando ataques básicos e habilidades especiais. O título e nomes de personagens são inspirados no Clair Obscur: Expedition 33 que é um jogo eletrônico de RPG de 2025 desenvolvido pela Sandfall Interactive e publicado pela Kepler Interactive.

O projeto procura demonstrar os **pilares da Programação Orientada a Objetos** de forma clara e prática.

---

## Funcionalidades

* Seleção de personagens para dois jogadores.
* Sistema de **nível e XP persistente** enquanto o programa está rodando.
* **Mecânicas exclusivas** para cada personagem.
* Menu dinâmico baseado na classe selecionada.
* Alternância automática de turnos.
* Reset automático de estados ao fim da batalha.
* Múltiplas partidas no mesmo programa.

---

## Como Executar

No terminal, dentro da pasta do projeto:

```bash
javac *.java
java Main
```
---

## Regras Gerais

No ClairObscur, as partidas seguem o formato 1 contra 1, onde dois jogadores escolhem personagens distintos — não é permitido selecionar o mesmo personagem para ambos os lados. Todos os combatentes utilizam um ataque básico padronizado, que serve como base para a ativação ou progressão de cada uma das habilidades específicas de suas classes, como carregamento, marcas elementais, poses ou grilhões. Ao final de cada combate, todos os estados temporários e mecânicas especiais são resetados, garantindo que cada nova partida comece em condições equilibradas e independentes da anterior. O vencedor recebe pontos de experiência, que permitem ao personagem subir de nível, porém esse nível tem função apenas visual, não altera atributos, danos ou defesas e existe apenas para registrar o progresso do jogador ao longo das batalhas.

---

## Personagens

### Gustave 
Guerreiro da Sobrecarga ⚡

| Mecânica Principal | Sobrecarga |
| :--- | :--- |

Cada ataque básico:
1.  Causa dano normal;
2.  Gera **1 a 3 pontos de carga**, esse valor sendo somado a cada ataque básico utilizado até 10.

* **Cargas** acumulam até **10** como citado acima, tornando cada vez mais benéfico usar a **Habilidade** citada abaixo:
* Habilidade **“Sobrecarga”**:
    $$dano = 12 \times (1.25^{\text{carga}})$$
    * Após usar, a carga zera. Com o jogador podendo voltar a usar ataques básicos para acumular cargas;
    * Reset ao fim da batalha.

---

### Lune
Maga Elemental 🔥❄⚡

| Mecânica Principal | Roda Elemental (3 slots) |
| :--- | :--- |

Cada ataque básico preenche 1 slot vazio com: **fogo (F)**, **gelo (G)** ou **trovão (T)**.
* Slots cheios → não adiciona mais elementos.

| Habilidade | Bônus de Dano |
| :--- | :--- |
| Bola de Fogo | +66% por slot de fogo |
| Nova de Gelo | +66% por slot de gelo |
| Trovão | +66% por slot de trovão |

* A habilidade consome apenas os slots do elemento correspondente.



---

### Maelle
Mestra das Poses 🌙

| Pose | Efeito no Ataque | Efeito na Defesa |
| :--- | :--- | :--- |
| **Defensiva** | normal | -30% dano recebido |
| **Ofensiva** | +30% dano | normal |
| **Iluminada** | +130% dano | +50% dano recebido |

**Regras:**
* Começa sem pose.
* Ataque básico:
    * Sem pose → **ganha pose aleatória**;
    * Com pose → **mantém a pose que estava e aproveita os benefícios**;
* Habilidade especial **consome pose** e retorna ao estado neutro.

---

### Monoco
Carcereiro de Almas 🔗

| Mecânica Principal | Grilhões de Alma |
| :--- | :--- |

Ataque básico aplica **1 a 10 grilhões** ao inimigo.
* Cada grilhão reduz **0,5% do dano recebido** por Monoco. Ou seja, há vantagens em apenas ficar acumulando grilhões.

| Habilidade | Efeito |
| :--- | :--- |
| **Julgamento** | consome todos os grilhões, **+5% de dano por grilhão** |
| **Sentença** | cura, cura base aumentada **+10% por grilhão**, consome todos os grilhões |



---

## Arquitetura



A estrutura de arquivos do projeto é organizada da seguinte forma:

```bash
ClairObscur/
├── Combatente.java  # Classe abstrata base
├── Gustave.java     # Subclasse de Combatente
├── Lune.java        # Subclasse de Combatente
├── Maelle.java      # Subclasse de Combatente
├── Monoco.java      # Subclasse de Combatente
└── Main.java        # Ponto de entrada e lógica principal do jogo
```
---

## Conceitos de POO Aplicados

A tabela a seguir detalha como os pilares da Programação Orientada a Objetos (POO) foram aplicados no projeto:

| Conceito | Status | Aplicação |
| :--- | :--- | :--- |
| **Abstração** | ✔️ | `Combatente` abstrai atributos e comportamentos genéricos. Cada personagem implementa habilidades específicas. |
| **Encapsulamento** | ✔️ | Todos os atributos são **privados**. Métodos de acesso (getters/setters) e lógica interna controlam o acesso aos dados. |
| **Herança** | ✔️ | Todos os personagens (`Gustave`, `Lune`, `Maelle`, `Monoco`) estendem a classe base `Combatente`. |
| **Polimorfismo** | ✔️ | Ataques e habilidades são **sobrescritos** nas subclasses. A lógica do combate trata todos os personagens de forma genérica como `Combatente`. |
| **Composição** | ✔️ | A classe `Main` compõe e coordena dois objetos `Combatente`.  |
| **Coesão e Baixo Acoplamento** | ✔️ | Cada classe possui responsabilidade clara. O acoplamento é baixo, exceto por verificações de menus especiais via `instanceof`. |

---
