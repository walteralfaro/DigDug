package walterServerCliente;

import java.awt.event.KeyEvent;

public class Maps {
	public static Maps instance;
	public synchronized static Maps getInstance(){
		if(instance == null)
			instance= new Maps();
		return instance;
	}
	private char mapa1[][] = {

			{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
    		{ 9, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 9}, 
    		{ 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 9},
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}, 
    		{ 9, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9},
    		{ 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9},
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 9}, 
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9}, 
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9},
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9},
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9},
    		{ 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 9},
    		{ 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
    };

	// 9 pared lateral y abajo
	// 5 cielo pared
	// 6 cielo para subir y moverse
	// 7 flor, 8 piedra
	// 0 espacios para moverse en negro
	// 1 2 3 4 tierra para excavar
	// 10 11 12 13 jugadores
	public char[][] getMapa1() {
		return mapa1;
	}

	public void setMapa1(char[][] mapa1) {
		this.mapa1 = mapa1;
	}
	private int enCaidaX=0;
	private int enCaidaY=0;
	private int caida;
	public Movimiento repaint(Movimiento mov, Integer userId){
		int value = 0;
		if(userId.equals(new Integer(0))){
			value =10;
		}else if(userId.equals(new Integer(1))){
			value=12;
		}
		int posX = mov.getPosicion().getX();
		int posY = mov.getPosicion().getY();
		switch( mov.getKeyCode() ) { 
    	case KeyEvent.VK_UP:
        	if ( (mapa1[posY-1][posX] == 0) || (mapa1[posY-1][posX] == 1) || 
        			(mapa1[posY-1][posX] == 2) || (mapa1[posY-1][posX] == 3) || 
        			(mapa1[posY-1][posX] == 4) || (mapa1[posY-1][posX] == 6) ) {
        		
        		mapa1[posY][posX] = 0; //set en la matriz el lugar excavado
        		posY = posY - 1; //set posicion movimiento arriba
        		mapa1[posY][posX] = (char) value;
        		mov.getPosicion().setX(posX);
        		mov.getPosicion().setY(posY);
        		if(mapa1[posY-1][posX] == 8){
        			enCaidaX=posY-1;
        			enCaidaY=posX;
//        			enCaida[posY-1][posX] = 1;
        		}
        	}
            break;
            
        case KeyEvent.VK_DOWN:
        	if ( (mapa1[posY+1][posX] == 0) || (mapa1[posY+1][posX] == 1) || 
        			(mapa1[posY+1][posX] == 2) || (mapa1[posY+1][posX] == 3) || 
        			(mapa1[posY+1][posX] == 4) || (mapa1[posY+1][posX] == 6) ) {
        		
        		mapa1[posY][posX] = 0;
        		posY = posY + 1;
        		mapa1[posY][posX] = (char) value;
        		mov.getPosicion().setX(posX);
        		mov.getPosicion().setY(posY);
        	}
            break;
            
        case KeyEvent.VK_LEFT:
        	if ( (mapa1[posY][posX-1] == 0) || (mapa1[posY][posX-1] == 1) || 
        			(mapa1[posY][posX-1] == 2) || (mapa1[posY][posX-1] == 3) || 
        			(mapa1[posY][posX-1] == 4) || (mapa1[posY][posX-1] == 6) ) {

        		mapa1[posY][posX] = 0;
        		posX = posX - 1;
        		mapa1[posY][posX] = (char) value;
        		mov.getPosicion().setX(posX);
        		mov.getPosicion().setY(posY);
        	}
            break;
            
        case KeyEvent.VK_RIGHT :
        	if ( (mapa1[posY][posX+1] == 0) || (mapa1[posY][posX+1] == 1) || 
        			(mapa1[posY][posX+1] == 2) || (mapa1[posY][posX+1] == 3) || 
        			(mapa1[posY][posX+1] == 4) || (mapa1[posY][posX+1] == 6) ) {

        		mapa1[posY][posX] = 0;
        		posX = posX + 1;
        		mapa1[posY][posX] = (char) value;
        		mov.getPosicion().setX(posX);
        		mov.getPosicion().setY(posY);
        	}
            break;
            
     }
		if(enCaidaX != 0){
			caida++;
			if((caida%3) == 0){
				mapa1[enCaidaY-1][enCaidaX] = 0;
				enCaidaY = enCaidaY + 1;
				caida = 0;
			}
		}
			
		return mov;
		
	}
	public void agregarPiedra(Coordenada coordenada){
		mapa1[coordenada.getY()][coordenada.getX()] = 8;
	}
	
	public void agregarFlor(Coordenada coordenada){
		mapa1[coordenada.getY()][coordenada.getX()] = 7;	
	}
}
