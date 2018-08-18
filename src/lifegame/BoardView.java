package lifegame;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardView extends JPanel
implements ActionListener, BoardListener, MouseListener, MouseMotionListener{


	BoardModel model = new BoardModel();
	private int oldX;
	private int oldY;
	private int HEIGHT;
	private int WIDTH;
	private int width;
	private int height;
	private int length;
	private int startwidth;
	private int startheight;
	JButton btnNext = new JButton("NEXT");
	JButton btnUndo = new JButton("UNDO");
	JButton btnNewGame = new JButton("New Game");

	public BoardView(){
	this.addMouseMotionListener(this);
	this.addMouseListener(this);
	model.addListener(this);
	}

	public JPanel getPanel(){
	JPanel btnPanel = new JPanel();
	btnPanel.setLayout(new FlowLayout());

	btnNext.addActionListener(this);
	btnNext.setActionCommand("btnNext");
	btnUndo.addActionListener(this);
	btnUndo.setActionCommand("btnUndo");
	btnNewGame.addActionListener(new NewGameAct());

	btnPanel.add(btnNext);
	btnPanel.add(btnUndo);
	btnPanel.add(btnNewGame);

	return btnPanel;
	}

	public void paint(Graphics g){
		HEIGHT = model.getheight();
		WIDTH = model.getwidth();
		width = this.getWidth();
		height = this.getHeight();
		length = Math.min(width/WIDTH, height/HEIGHT);
		startwidth = width/2 - length * WIDTH / 2;
		startheight = height/2 - length * HEIGHT / 2;
		super.paint(g);

		for(int i = 0; i < WIDTH + 1; i++){
			g.drawLine(startwidth + i * length, startheight, startwidth + i * length, startheight + HEIGHT * length);
		}
		for(int i = 0; i < HEIGHT + 1; i++){
			g.drawLine(startwidth, startheight + i * length, startwidth + WIDTH * length, startheight + i * length);
		}
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				if(model.isAlive(j,i)){
					g.fillRect(startwidth + j * length + 1, startheight + i * length + 1, length -1, length - 1);
				}
			}
		}
		if(model.isUndoable()){
			btnUndo.setEnabled(true);
		}else{
			btnUndo.setEnabled(false);
		}
	}

	@Override
	public void updated(BoardModel m) {
		model = m;
		Graphics g = getGraphics();
		g.dispose();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("btnNext")){
			model.next();
		}else if(cmd.equals("btnUndo")){
			model.undo();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x;
		x = (int) Math.floor(( e.getX() - startwidth - 1) / length);
		int y;
		y = (int) Math.floor(( e.getY() - startheight - 1) / length);
		if(0 <= x & x < WIDTH & 0 <= y & y < HEIGHT){
			if(x != oldX | y != oldY){
				model.changeCellState(x, y);
				model.fireUpdate();
			}
		}
		oldX = x;
		oldY = y;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x;
		x = (int) Math.floor(( e.getX() - startwidth - 1) / length);
		int y;
		y = (int) Math.floor(( e.getY() - startheight - 1) / length);
		model.changeCellState(x, y);
		model.fireUpdate();
		oldX = x;
		oldY = y;
	}

	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}


}