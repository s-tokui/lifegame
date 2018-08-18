package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main{


	public static void main(String[] args){
		BoardModel model = new BoardModel();
		BoardView view = new BoardView();
		model.addListener(view);

		JFrame frame = new JFrame("Lifegame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(800,600));
		frame.setMinimumSize(new Dimension(300,200));
		base.setLayout(new BorderLayout());

		base.add(view, BorderLayout.CENTER);
		JPanel btnPanel = new JPanel();
		btnPanel = view.getPanel();
		base.add(btnPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}
}
