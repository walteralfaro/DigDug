package commons;

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

		public char[][] getMapa1() {
		return mapa1;
	}

	public void setMapa1(char[][] mapa1) {
		this.mapa1 = mapa1;
	}
	
	public int[] repaint(int keyCode,int x_jugador1,  int y_jugador1){
		int[] pos = new int[3];
		pos[0]=x_jugador1;
		pos[1]=y_jugador1;
		pos[2]=0;
		switch( keyCode ) { 
    	case KeyEvent.VK_UP:
//    		nivel_img[10] = p1_arriba;
    		
        	if ( (mapa1[y_jugador1-1][x_jugador1] == 0) || (mapa1[y_jugador1-1][x_jugador1] == 1) || 
        			(mapa1[y_jugador1-1][x_jugador1] == 2) || (mapa1[y_jugador1-1][x_jugador1] == 3) || 
        			(mapa1[y_jugador1-1][x_jugador1] == 4) || (mapa1[y_jugador1-1][x_jugador1] == 6) ) {
        		
        		mapa1[y_jugador1][x_jugador1] = 0; //set en la matriz el lugar excavado
        		y_jugador1 = y_jugador1 - 1; //set posicion movimiento arriba
        		mapa1[y_jugador1][x_jugador1] = 10;
        		pos[0]=x_jugador1;
        		pos[1]=y_jugador1;
        		pos[2]=1;
        	}
            break;
            
        case KeyEvent.VK_DOWN:
//        	nivel_img[10] = p1_abajo;
        	
        	if ( (mapa1[y_jugador1+1][x_jugador1] == 0) || (mapa1[y_jugador1+1][x_jugador1] == 1) || 
        			(mapa1[y_jugador1+1][x_jugador1] == 2) || (mapa1[y_jugador1+1][x_jugador1] == 3) || 
        			(mapa1[y_jugador1+1][x_jugador1] == 4) || (mapa1[y_jugador1+1][x_jugador1] == 6) ) {
        		
        		mapa1[y_jugador1][x_jugador1] = 0;
        		y_jugador1 = y_jugador1 + 1;
        		mapa1[y_jugador1][x_jugador1] = 10;
        		pos[0]=x_jugador1;
        		pos[1]=y_jugador1;
        		pos[2]=1;
        	}
            break;
            
        case KeyEvent.VK_LEFT:
//        	nivel_img[10] = p1_izq;
        	
        	if ( (mapa1[y_jugador1][x_jugador1-1] == 0) || (mapa1[y_jugador1][x_jugador1-1] == 1) || 
        			(mapa1[y_jugador1][x_jugador1-1] == 2) || (mapa1[y_jugador1][x_jugador1-1] == 3) || 
        			(mapa1[y_jugador1][x_jugador1-1] == 4) || (mapa1[y_jugador1][x_jugador1-1] == 6) ) {

        		mapa1[y_jugador1][x_jugador1] = 0;
        		x_jugador1 = x_jugador1 - 1;
        		mapa1[y_jugador1][x_jugador1] = 10;
        		pos[0]=x_jugador1;
        		pos[1]=y_jugador1;
        		pos[2]=1;
        	}
            break;
            
        case KeyEvent.VK_RIGHT :
//        	nivel_img[10] = p1_der;
        	
        	if ( (mapa1[y_jugador1][x_jugador1+1] == 0) || (mapa1[y_jugador1][x_jugador1+1] == 1) || 
        			(mapa1[y_jugador1][x_jugador1+1] == 2) || (mapa1[y_jugador1][x_jugador1+1] == 3) || 
        			(mapa1[y_jugador1][x_jugador1+1] == 4) || (mapa1[y_jugador1][x_jugador1+1] == 6) ) {

        		mapa1[y_jugador1][x_jugador1] = 0;
        		x_jugador1 = x_jugador1 + 1;
        		mapa1[y_jugador1][x_jugador1] = 10;
        		pos[0]=x_jugador1;
        		pos[1]=y_jugador1;
        		pos[2]=1;
        	}
            break;
            
     }// switch
//
		return pos;
		
	}
}
