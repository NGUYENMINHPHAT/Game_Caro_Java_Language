package NGUYENMINHPHAT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CaroFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private int width = CaroGraphics.width;
	private int height = CaroGraphics.height;

	private CaroGraphics caroGraphics;

	public static JLabel lbStatusO;
	public static JLabel lbStatusX;
	private JLabel lbNamePlayerO;
	private JLabel lbNamePlayerX;
	private JLabel lbScoreO;
	private JLabel lbScoreX;
	private ImageIcon iconPlayerO;
	private ImageIcon iconPlayerX;
	private int scoreO = 0, scoreX = 0;

	private String playerName1 = "PLAYER 1", playerName2 = "PLAYER 2";

	private MyImage myImage = new MyImage();
	private PlayerName selectPlayerFrame;

	public CaroFrame() {
		init();
	}

	private void init() {
		setTitle("PRODUCT BY: NGUYEN MINH PHAT. SCHOOL: DAI HOC CAN THO. INDUSTRY: INFORMATION SYSTEMS.KEY: 45.EMAIL:pn333393@gmail.com");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		initGraphics();
		setJMenuBar(createJMenuBar());
		add(createMainPainl());
		// JOptionPane.show

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		selectPlayer();
	}

	private void initGraphics() {
		caroGraphics = new CaroGraphics();
		caroGraphics.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				caroGraphics.actionClick(e.getPoint());
				if (caroGraphics.getWiner() > 0) {
					win(caroGraphics.getWiner());
				}
			}
		});
	}

	private void selectPlayer() {
		if (selectPlayerFrame == null) {
			selectPlayerFrame = new PlayerName(this);
		}
		selectPlayerFrame.setVisible(true);
	}

	public void updateStatus() {
		playerName1 = selectPlayerFrame.getPlayerName1();
		playerName2 = selectPlayerFrame.getPlayerName2();
		caroGraphics.player = caroGraphics.playerRoot;
		lbNamePlayerX.setText(playerName1);
		lbNamePlayerO.setText(playerName2);
		if (selectPlayerFrame.getStart() == 1) {
			caroGraphics.playerRoot = true;
		} else {
			caroGraphics.playerRoot = false;
		}
		caroGraphics.player = caroGraphics.playerRoot;
		caroGraphics.setStatus();
		System.out.println("updated");
	}

	private JMenuBar createJMenuBar() {
		JMenuBar mb = new JMenuBar();
		String[] game = { "NEW GAME", "NEW ROUND", "", "EXIT" };
		mb.add(createJMenu("GAME", game, KeyEvent.VK_T));
		String[] help = { "TOTURIAL", "", "INTRODUCE" };
		mb.add(createJMenu("TOTURIAL", help, KeyEvent.VK_H));
		return mb;
	}

	private JMenu createJMenu(String menuName, String itemName[], int key) {
		JMenu m = new JMenu(menuName);
		m.addActionListener(this);
		m.setMnemonic(key);

		for (int i = 0; i < itemName.length; i++) {
			if (itemName[i].equals("")) {
				m.add(new JSeparator());
			} else {
				m.add(createJMenuItem(itemName[i]));
			}
		}

		return m;
	}

	private JMenuItem createJMenuItem(String itName) {
		JMenuItem mi = new JMenuItem(itName);
		mi.addActionListener(this);
		return mi;
	}

	private JPanel createMainPainl() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createPanelGraphics(), BorderLayout.CENTER);
		panel.add(createSidebarPanel(true), BorderLayout.WEST);
		panel.add(createSidebarPanel(false), BorderLayout.EAST);
		return panel;
	}

	private JPanel createPanelGraphics() {
		JPanel panelGraphics = new JPanel(null);
		panelGraphics.add(caroGraphics, BorderLayout.CENTER);
		int bound = 10;
		caroGraphics.setBounds(bound, bound, caroGraphics.width,
				caroGraphics.height);
		panelGraphics.setPreferredSize(new Dimension(caroGraphics.width + bound
				* 2, caroGraphics.height + bound * 2));

		panelGraphics.setBorder(new LineBorder(Color.black));
		panelGraphics.setBackground(Color.red);
		return panelGraphics;
	}

	private JPanel createSidebarPanel(boolean player) {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(createPanelStatus(player), BorderLayout.PAGE_START);

		panel.add(createPlayerPanel(player), BorderLayout.CENTER);

		panel.add(createPanelBottom(player), BorderLayout.PAGE_END);
		return panel;
	}

	private JPanel createPanelStatus(boolean player) {
		JPanel panelStatus = new JPanel(new GridLayout(2, 1, 2, 2));
		JPanel panel1 = new JPanel();

		if (player) {
			lbStatusX = new JLabel();
			lbStatusX.setHorizontalAlignment(JLabel.CENTER);
			lbNamePlayerX = new JLabel("PLAYER 1");
			lbNamePlayerX.setHorizontalAlignment(JLabel.CENTER);

			lbScoreX = new JLabel("PLAYER SCORE 1: 0");
			lbScoreX.setFont(lbScoreX.getFont().deriveFont(Font.PLAIN, 17f));
			lbScoreX.setForeground(Color.blue);
			lbScoreX.setHorizontalAlignment(JLabel.CENTER);

			panel1.add(lbStatusX);
			panel1.add(lbNamePlayerX);
			panelStatus.add(panel1);
			panelStatus.add(lbScoreX);

		} else {
			lbStatusO = new JLabel();
			lbStatusO.setHorizontalAlignment(JLabel.CENTER);
			lbNamePlayerO = new JLabel("PLAYER 2");
			lbNamePlayerO.setHorizontalAlignment(JLabel.CENTER);

			lbScoreO = new JLabel("PLAYER SCORE 2: 0");
			lbScoreO.setFont(lbScoreO.getFont().deriveFont(Font.PLAIN, 17f));
			lbScoreO.setForeground(Color.red);
			lbScoreO.setHorizontalAlignment(JLabel.CENTER);

			panel1.add(lbStatusO);
			panel1.add(lbNamePlayerO);
			panelStatus.add(panel1);
			panelStatus.add(lbScoreO);
		}
		int bound = 1;
		panelStatus.setBorder(new LineBorder(Color.green));
		panelStatus.setPreferredSize(new Dimension(width / 3, height / 6 - 25));
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(bound, bound, bound, bound));
		panel.add(panelStatus);
		return panel;
	}

	private JPanel createPlayerPanel(boolean player) {
		int boundw = 10;
		int boundh = 10;
		int h = height * 2 / 3 + boundh;
		int w = width / 3;
		String imgPlayerO = "playerO.gif";
		String imgPlayerX = "playerX.gif";
		iconPlayerO = new ImageIcon(myImage.reSizeImage(
				myImage.getMyImageIcon(imgPlayerO), w - boundw, h - boundh));
		iconPlayerX = new ImageIcon(myImage.reSizeImage(
				myImage.getMyImageIcon(imgPlayerX), w - boundw, h - boundh));

		ImageIcon icon = player ? iconPlayerX : iconPlayerO;
		JLabel lbPlayer = new JLabel(icon);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(w, h));

		panel.add(lbPlayer, BorderLayout.CENTER);

		int bound = 1;
		panel.setBorder(new LineBorder(Color.green));
		JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(bound, bound, bound, bound));
		panel1.add(panel);
		return panel1;
	}

	private JPanel createPanelBottom(boolean player) {
		String[] str1 = { "GO", "I GIVE UP" };
		String[] str2 = { "NEW GAME", "NEW ROUND" };
		String[] str;
		if (player) {
			str = str1;
		} else {
			str = str2;
		}
		int size = str.length;
		JPanel panel = new JPanel(new GridLayout(size, 1, 5, 5));
		for (int i = 0; i < size; i++) {
			panel.add(createJButton(str[i]));
		}
		int bound = 1;
		panel.setBorder(new LineBorder(Color.green));
		panel.setPreferredSize(new Dimension(width / 3, height / 6));
		JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(bound, bound, bound, bound));
		panel1.add(panel);
		return panel1;
	}

	private JButton createJButton(String btnName) {
		JButton btn = new JButton(btnName);
		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == "NEW GAME") {
			actionNewGame();
		}
		if (command == "NEW ROUND") {
			actionNewUnit();
		}
		if (command == "EXIT") {
			actionExit();
		}

		if (command == "TOTURIAL") {
			actionHelp();
		}

		if (command == "INTRODUCE") {
			actionAbout();
		}

		if (command == "GO") {
			actionUndo();
		}
		if (command == "I GIVE UP") {
			actionGiveIn();
		}
	}

	private void actionNewGame() {
		int select = showDialog("YOU GUYS REALLY WANT TO CREATE A NEW GAME?",
				"NEW GAME");
		if (select == 0) {
			scoreO = 0;
			scoreX = 0;
			clear();
		}
	}

	private void actionNewUnit() {
		int select = showDialog("YOU GUYS REALLY WANT TO MAKE A NEW SET?",
				"NEW ROUND");
		if (select == 0) {
			clear();
		}
	}

	private void actionExit() {
		int select = showDialog("YOU GUYS REALLY WANT TO ESCAPE?", "EXIT");
		if (select == 0) {
			System.exit(0);
		}
	}

	private void actionHelp() {
		new CaroInfo(0);
	}

	private void actionAbout() {
		new CaroInfo(1);
	}

	private void actionUndo() {
		caroGraphics.undo();
	}

	private void actionGiveIn() {
		int sO = 0, sX = 0;
		String playerName = "";
		if (caroGraphics.player) {
			sO = 1;
			playerName = playerName1;
		} else {
			sX = 1;
			playerName = playerName2;
		}

		int select = showDialog(playerName + " REALLY WANT TO ASK TO LOSE?",
				"NEW GAME");
		if (select == 0) {
			scoreO += sO;
			scoreX += sX;
			clear();
		}
	}

	private int showDialog(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		return select;
	}

	private void clear() {
		caroGraphics.init();
		updateScore();
		selectPlayer();
		caroGraphics.setStatus();
	}

	private void updateScore() {
		lbScoreO.setText(scoreO + "");
		lbScoreX.setText(scoreX + "");
	}

	private void win(int winer) {
		String playerName = "";
		if (winer == 1) {
			scoreX++;
			playerName = playerName1;
		} else {
			scoreO++;
			playerName = playerName2;
		}
		Object[] options = { "NEW GAME", "NEW ROUND", "EXIT" };
		int select = JOptionPane.showOptionDialog(this, "CONGRATULATIONS "
				+ playerName + " WON THE MATCH "
				+ (scoreO + scoreX), "A Silly Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[options.length - 1]);
		if (select == 2) {
			actionExit();
		} else if (select == 0) {
			scoreO = 0;
			scoreX = 0;
		}
		clear();
	}
}
