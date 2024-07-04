import java.awt.*;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {
	private double cx, cy, width, height, speed, Xspeed, Yspeed;
	double directionX, directionY;
	Color color;

 public double upSideWall, downSideWall;
	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/
	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.color = color;

		double direction = Math.random()*360; // ângulo aleatório
		double directionInRad = direction * Math.PI/180; //transformado em radiano

		directionX = Math.cos(directionInRad); 
		directionY = Math.sin(directionInRad);

		Xspeed = this.speed;
		Yspeed = this.speed;

	}


	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){

		GameLib.setColor(color);
		GameLib.fillRect(cx, cy, width, height);
	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.

	 *Implementação: a posição atual da bola calculada com a fórmula S = S0 + velocidade*tempo.

	 *@param delta quantidade de millisegundos que se passou entre o ciclo anterior de
		atualização do jogo e o atual.
	 *@param cx posição atual da bola no eixo x
	 *@param cy posição atual da bola no eixo y
	 * **/
	public void update(long delta){
		cx = cx + Xspeed*delta;
		cy = cy + Yspeed*delta;

	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/
	public void onPlayerCollision(String playerId){
		this.Xspeed = - this.Xspeed;
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){
		switch (wallId) {
			case "Left":
			   Xspeed = Math.abs(Xspeed);
					break;
			case "Right":
					Xspeed = -Math.abs(Xspeed);
					break;
			case "Top":
					Yspeed = Math.abs(Yspeed);
					break;
			case "Bottom":
					Yspeed = -Math.abs(Yspeed);
					break;
	}
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	public boolean checkCollision(Wall wall){
	//coordenada do lado esquerdo da bola
	double leftSideBall = cx - width/2;
	//coordenada do lado direito da bola
	double rightSideBall = cx + width/2;
	//coordenada do lado de cima da bola
	double downSideBall = cy - height/2;
	//coordenada do lado de baixo da bola
	double upSideBall = cx + height/2;

		
		switch (wall.getId()){
			case "Left":
				if (leftSideBall < wall.getCx() + wall.getWidth()/2)
						return true;
				break;

			case "Right":
				if (rightSideBall > wall.getCx() - wall.getWidth()/2)
						return true;
					break;
			case "Top":
				if (downSideBall < wall.getCy() + wall.getHeight()/2)
						return true;
					break;
			case "Bottom":
					if (cy + height/2 > wall.getCy() - wall.getHeight()/2)
							return true;
					break;
	}
	return false;
	}
	Player playerColisao, playerHit;
	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	
	public boolean checkCollision(Player player){
//coordenadas da bola abaixo

		//coordenada do lado esquerdo da bola
		double leftSideBall = cx - width/2;
		//coordenada do lado direito da bola
		double rightSideBall = cx + width/2;
		//coordenada do lado de cima da bola
		double downSideBall = cy - height/2;
		//coordenada do lado de baixo da bola
		double upSideBall = cx + height/2;

 // coordenadas player

		double downSidePlayer = player.getCy() + player.getHeight()/2;

		double upSidePlayer = player.getCy() - player.getHeight()/2;

		double rightSidePlayer = player.getCx() + player.getWidth()/2;

		double leftSidePlayer = player.getCx() - player.getWidth()/2;
		
		if (!(downSideBall < downSidePlayer && cy + height/2 > player.getCy() - player.getHeight()/2))
			return false;

switch (player.getId()) {
	case "Player 1":
		if (leftSideBall < rightSidePlayer && leftSidePlayer < rightSideBall){

			if(upSideBall < upSidePlayer && downSideBall > downSidePlayer)
				return true;

			if(Xspeed > 0 )
				return false;

			return true;
		}
					
		case "Player 2":
			if (rightSideBall > leftSidePlayer && rightSidePlayer > leftSideBall){

				if(upSideBall < upSidePlayer && downSideBall > downSidePlayer)
					return true;
				if(Xspeed < 0 ) 
					return false;

				return true;
			}
						
	}
	return false;

	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	
	public double getCx(){

		return this.cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){

		return this.cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){

		return speed;
	}

}