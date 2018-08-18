package lifegame;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {
	public static final int NumOfArray = 32;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public int n=0;
	private boolean[][] cells;
	private boolean[][] oldcells;
	List<boolean[][]>  list  = new ArrayList<boolean[][]>();
    private ArrayList<BoardListener> listeners;

    public BoardModel(){
    	cells = new boolean[HEIGHT][WIDTH];
    	oldcells = new boolean[HEIGHT][WIDTH];
    	listeners = new ArrayList<BoardListener>();
    }
    public void addListener(BoardListener listener){
    	listeners.add(listener);
    }
    void fireUpdate(){
    	for(BoardListener listener: listeners){
    		listener.updated(this);
    	}
    }
    public void changeCellState(int x, int y){
    	if(cells[y][x] == false){
    		cells[y][x] = true;
    	}else{
    		cells[y][x] = false;
    	}
    }

    public int CountLiving(int x, int y, int count){
    	if(oldcells[x][y] == true){
    		count++;
    	}
    	return count;
    }

    public void next(){
    	this.addlist();
		int count  = 0;
    	for(int i = 0; i < HEIGHT; i++){
    		for(int j = 0; j < WIDTH; j++){
    			count = CircleLiving(i,j);
    			if(cells[i][j] == true){
        			if(count < 2 | count > 3 ){
        				changeCellState(j,i);
        			}
        		}else{
        			if(count == 3){
        				changeCellState(j,i);
        			}
    			}
    			count = 0;
    		}
    	}
    	this.fireUpdate();
    }
    public void addlist(){
    	for(int i = 0; i < HEIGHT; i++){
    		for(int j = 0; j < WIDTH; j++){
    			oldcells[i][j] = cells[i][j];
    		}
    	}
    	if(n == NumOfArray){
			for(int k = 0; k < NumOfArray-1; k++){
				for(int i = 0; i < HEIGHT; i++){
					for(int j = 0; j < WIDTH; j++){
        				list.get(k)[i][j] = list.get(k+1)[i][j];
        			}
        		}
        	}
			for(int i = 0; i < HEIGHT; i++){
				for(int j = 0; j < WIDTH; j++){
		    		list.get(NumOfArray-1)[i][j] = cells[i][j];
    			}
    		}
    	}else{
			list.add(new boolean[WIDTH][HEIGHT]);
			for(int i = 0; i < HEIGHT; i++){
				for(int j = 0; j < WIDTH; j++){
					list.get(list.size()-1)[i][j] = cells[i][j];
				}
			}
    		n = n + 1;
    	}
    }
	public void undo(){
		if(n == 0){
			System.out.println("error:you cannot undo");
		}else{
			for(int i = 0; i < HEIGHT; i++){
				for(int j = 0; j < WIDTH; j++){
					cells[i][j] = list.get(list.size()-1)[i][j];
				}
			}
			n = n-1;
			for(int k = list.size()-1; k >0 ; k--){
				for(int i = 0; i < HEIGHT; i++){
					for(int j = 0; j < WIDTH; j++){
						list.get(k)[i][j] = list.get(k-1)[i][j];
					}
				}
			}
			this.fireUpdate();
		}
	}
    public int CircleLiving(int x, int y){
	int count = 0;
		if(y > 0){
			count = CountLiving(x,y-1,count);
			if(x > 0){
				count = CountLiving(x-1,y-1,count);
			}
			if(x < HEIGHT-1){
				count = CountLiving(x+1,y-1,count);
			}
		}
		if(x > 0){
			count = CountLiving(x-1,y,count);
		}
		if(x < HEIGHT-1){
			count = CountLiving(x+1,y,count);
		}
		if(y < WIDTH-1){
			if(x > 0){
				count = CountLiving(x-1,y+1,count);
			}
			count = CountLiving(x,y+1,count);
			if(x < HEIGHT-1){
				count = CountLiving(x+1,y+1,count);
			}
		}
		return count;
    }
	public boolean isUndoable(){
		if(n == 0){
			return false;
		}else{
			return true;
		}
	}
	public int getwidth(){
		return WIDTH;
	}
	public int getheight(){
		return HEIGHT;
	}
	public boolean isAlive(int x, int y){
		return cells[y][x];
	}
}