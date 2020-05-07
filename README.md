# chess-system-java

REGRAS GERAIS:
- Peão é a única peça do jogo que não volta;
- Peão é a única peça que não captura da forma que anda;
- Cavalo é a única peça que pode pular peças sem esperar que o peão ou outra peça saia da frente;
- Tabuleiro: 64 CASAS;

Movimento especiais:
1 - Roque (trocar posição entre rei e torre):
  - 'Se' o rei e a torre (ambas peças) não tiverem sido movimentadas, as casas vizinhas entre elas estiverem vazias e o rei não estiver em Cheque; elas podem mudar de posição em uma mesma jogada. Podem ir para as duas casas livres ao lado do Rei. O Rei deve ser (tocado) movimentado primeiro.
  Roque pequeno: Do lado do rei/lado direito.
  Roque grande:  Do lado da rainha/lado esquerdo)

2 - En Passant (Captura de outro peão que fez o se primeiro movimento pulando 2 casas):
  - Ex: Um peão branco na casa ao lado do peão preto. Na vez de jogar do peão branco (Se respeitada a regra do peão preto) ele pode avançar pela diagonal acima do preto (sendo casa livre) e capturar o peão preto.
  - Regra geral de start: O peão preto tem que estar nesta situação ao ser sua primeira jogada e se ele se movimentou 2 casas nessa jogada.
  - O peão branco não fica na casa onde estava o peão branco (se mantém na casa da diagonal).

3 - Promoção (Trocar o peão que chegou na última linha por outra peça):
  - Se o peão chegar até a última fila, o jogador poderá trocá-lo por qualquer peça que desejar, exceto pelo rei ou por outro peão




Rei - King (1x):
	- Perdeu o Rei, perdeu o jogo;
	- Pode andar de uma-uma casa em todas as direções do tabuleiro (mesmo de outras cores);


Rainha (1x):
	- Peça mais poderosa;
	- Pode andar quantas casas quiser em qualquer direção;
	
	
Torre - Rook (2x):
	- Anda quantas casa quiser horizontal e vertical (pra frente, pra trás, pros lados);
	
	

Bisco - Bishop (2x):
	- Só pode andar na diagonal (da cor que começou o jogo);
	- Pode andar quantas casa quiser;
	- Os bispos nunca vão se chocar;


Cavalo (2x):
	- Movimento que lembra L (1 e 2 na mesma direção e 1 pro lado);
	- Só ele pula peças (que estão nas casas no caminho de qualquer jogador);
	- Não precisa esperar mover os pião primeiro. Ela pula.
	- Só captura na casa que parar;

	

Pião - Pawn (8x):
	- A peça mais frágil;
	- Anda 1 ou 2 casas quando está na linha inicial;
	- Depois que saiu da linha inicial, só pode andar de 1x1 casa;
	- Piao não volta;
	- Quando um está na frente do outro, eles estão bloqueados;
	
	CAPTURA:
	Movimento curto na diagonal (vai pra posição que estava o outro), mas ele pode optar por não capturar;
