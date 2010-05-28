package oop.ex4.gui;

// -------------------------------------------------------
//
// This is modification of original code
//
// Source Written by Carl Haynes
// Submitted to Sun Microsystems for their
// java.applet.Applet Contest 8/31/95
//
// Source changed by Dmitry(dmitry@alice.nc.huji.ac.il) for
// oop course homework definition
// 
// Note that THIS FILE is NOT an example of perfectly designed GUI code
// However it is "REAL" code "from outside" you should be able to
// handle, use and understand relevant connection parts
//
// You should not change this code
// Please pay attention on lines commented with:
// /** CODE CONNECTION **/
// These are lines where this viewer
// --------------------------------------------------------

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;

import oop.ex4.crosswords.Crossword;
import oop.ex4.crosswords.CrosswordDictionary;
import oop.ex4.crosswords.CrosswordEntry;
import oop.ex4.crosswords.CrosswordPosition;
import oop.ex4.crosswords.CrosswordShape;
import oop.ex4.crosswords.MyCrossword;
import oop.ex4.crosswords.MyCrosswordDictionary;
import oop.ex4.crosswords.MyCrosswordShape;
import oop.ex4.crosswords.CrosswordShape.SlotType;
import oop.ex4.search.*;

public class CrosswordViewer extends JFrame implements ActionListener,
		CaretListener {
	private static final long serialVersionUID = 1L;
	protected JScrollPane _paneh, _panev;
	protected boolean _isHighlight = false;
	protected JTextArea _downClues, _acrossClues;
	protected CrosswordShape _shape;
	protected Crossword _board;
	boolean _noGUIFlag=false;

	protected int _maxEntryIndex = 0;

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            - The program arguments
	 */
	public static void main(String[] args) {
		CrosswordViewer hw = new CrosswordViewer();
		if ((args.length != 3 && args.length != 4) ||
				(args.length ==4 && args[3].compareToIgnoreCase("NOGUI")!=0)) {
			System.out.println("Invalid arguments!");
			System.out
					.println("Correct usage: CrosswordViewer gridfile dictfile timeout [NOGUI]");
			return;
		}
		try {
			long to = Long.parseLong(args[2]);
			hw.setupCrossword(args[0], args[1], to);

		} catch (IOException e) {
			System.err.println("I/O Exception during crossword creation!");
			System.err.println(e.getMessage());
			return;
		} catch (NumberFormatException e) {
			System.err.println("Invalid numbers in arguments!");
			return;
		} catch (Exception e) {
			System.err.println("Exception during crossword creation!");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		if (args.length !=4) { 
			hw.makeGUI();
			hw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			hw.validate();
			hw.repaint();
			hw.setVisible(true);
		}
	}

	Collection<CrosswordEntry> _entries;

	public void setupCrossword(String shapeFileName, String dictFileName,
			long timeout) throws IOException {
		/** CODE CONNECTION **/
		_shape = new MyCrosswordShape();
		_shape.load(shapeFileName);
		CrosswordDictionary dictionary = new MyCrosswordDictionary();
		dictionary.load(dictFileName);
		// Construct new CrosswordBoard from shape and dictionary file
		Crossword emptyBoard = new MyCrossword();
		emptyBoard.attachDictionary(dictionary);
		emptyBoard.attachShape(_shape);
		DepthFirstSearch<Crossword, CrosswordEntry> search = new MyDepthFirstSearch<Crossword, CrosswordEntry>();
		long time = new Date().getTime();
		_board = // _isLoops?
		search.search(emptyBoard, dictionary.getTerms().size() * 2, timeout);
		// search.searchTree(emptyBoard, dictionary.getTerms().size(), timeout);
		System.out.println("Done in " + (new Date().getTime() - time) + " ms.");

		if (_board == null) {
			System.out.println("Search returned NULL board!");
			System.exit(1);
		}
		_entries = _board.getCrosswordEntries();
		if (!verifyEntries() || _entries.isEmpty()) {
			if (_entries.isEmpty()) System.out.println("No entries!");
			System.out.println("BAD OUTPUT! - fix your code!");
		} else {
			System.out.println("Returned good crossword");
			System.out.println("With "+_entries.size()+ " entries.");
			System.out.println("Quality:"+_board.getQuality());
			System.out.println("This is "+(_board.isBestSolution() ?"definitely " : "(possibly) not ")
								+"the best solution for the given data");
		}
		/** END OF CODE CONNECTION **/

		kBlocksHigh = _shape.getHeight();
		kBlocksWide = _shape.getWidth();
	}

	class HashedPosition  {
		private CrosswordPosition _p;
		HashedPosition(CrosswordPosition p) {
			_p=p;
		}
		public int hashCode() {
			return _p.getX()*11+_p.getY()*13+25*(_p.isVertical()?1:0);			
		}
		public boolean equals(Object o) {
			if (!(o instanceof HashedPosition)) return false;
			HashedPosition p1=(HashedPosition)o;			
			return _p.getX()==p1._p.getX() && _p.getY()==p1._p.getY() && _p.isVertical()==p1._p.isVertical();
		}
		public boolean isInside() {
			return _p.getX()>=0 && _p.getX()<_shape.getWidth() && _p.getY()>=0 && _p.getY()<_shape.getHeight();
		}
		public String toString() {
			return "X:"+_p.getX()+",Y:"+_p.getY()+" IsVertical:"+_p.isVertical();
		}
	}
	private boolean verifyEntries() {
		Set<HashedPosition> positions=new HashSet<HashedPosition>();
		Set<String> words=new HashSet<String>();
		for (CrosswordEntry entry:_entries) {
			HashedPosition p=new HashedPosition(entry.getPosition());
			String w= entry.getTerm();
			if (positions.contains(p)) {
				System.out.println("Error: position "+p+" appears more than once");
				return false;
			} else if (words.contains(w)) {
				System.out.println("Error: word "+w+" appears more than once");
				return false;
			} else if (!p.isInside()) {
				// Note that this is a partial verification
				// You should test that all your entries and not only the first letters
				// are inside the area
				// and that your entry does not overlaps the shape blocking squares
					System.out.println("Error: position "+p+" is not inside the shape");
					return false;
			}
			positions.add(p);
			words.add(w);		
			//Among the other thigs you should check that your program supplies corrent
			//entry definitions inside entries
		}
		return true;
	}

	// /////////////////////////////////////// GUI IMPLEMENTATION //////////////
	/**
	 * Highlights text currently selected on crossword;
	 * 
	 */
	public boolean selectText() {
		CrosswordEntry e = _currentEntry;
		if (e != null) {
			String s;
			JTextArea area;
			if (e.getPosition().isVertical())
				area = _downClues;
			else
				area = _acrossClues;
			s = area.getText();
			int pos1 = s.indexOf("." + e.getDefinition()) - 1;
			int pos2 = s.substring(pos1).indexOf('\n');
			if (pos2 < 0)
				pos2 = s.length();
			else
				pos2 += pos1;
			while (pos1 >= 0 && s.charAt(pos1) >= '0' && s.charAt(pos1) <= '9')
				pos1--;
			pos1++;
			Highlighter h;
			_downClues.getHighlighter().removeAllHighlights();
			_acrossClues.getHighlighter().removeAllHighlights();
			h = area.getHighlighter();
			try {
				area.scrollRectToVisible(area.modelToView(pos1));
				area.scrollRectToVisible(area.modelToView(pos2));
				h.addHighlight(pos1, pos2, DefaultHighlighter.DefaultPainter);
				return true;
			} catch (BadLocationException ex) {
				assert (false);
			}
		}
		return false;
	}

	// ////// GUI Constants for graphic implementation
	int kBlockWidth = 30; // Horis Size of square
	int kBlockHeight = 30; // Vert Size of square

	int kBlocksWide; // Number of blocks
	int kBlocksHigh;

	int kPadding = 20; // Padding between components

	// Current highlight position
	CrosswordPosition _curPosition = new InternalCrosswordPosition(0, 0);

	/* --------------------------------------------------------------- */
	/* --------------------------------------------------------------- */

	int layout[][];

	/**
	 * Fills crossword with proposed solution
	 */
	public void fillTextAreas() {
		_acrossClues.setLineWrap(true);
		_downClues.setLineWrap(true);
		for (int k = 1; k < _maxEntryIndex; k++) {
			for (int j = 0; j < _shape.getHeight(); j++) {
				for (int i = 0; i < _shape.getWidth(); i++) {
					if (layout[j][i] == k) {
						CrosswordEntry e = getHighlightEntry(new InternalCrosswordPosition(i, j,
								false));
						if (e != null && e.getPosition().getX() == i
								&& e.getPosition().getY() == j) {
							_acrossClues.insert(layout[j][i] + "."
									+ e.getDefinition() + "\n", _acrossClues
									.getText().length());
						}
						e = getHighlightEntry(new InternalCrosswordPosition(i, j, true));
						if (e != null && e.getPosition().getX() == i
								&& e.getPosition().getY() == j) {
							_downClues.insert(layout[j][i] + "."
									+ e.getDefinition() + "\n", _downClues
									.getText().length());
						}
					}
				}
			}
		}
	}

	/**
	 * Makes crossword layout (as 2-d array) from grid
	 */
	public void makeLayout() {
		int entryIndex = 1;
		layout = new int[_shape.getHeight()][_shape.getWidth()];
		_guesses = new char[_shape.getWidth()][_shape.getHeight()];
		_correctLetters = new char[_shape.getWidth()][_shape.getHeight()];
		for (int j = 0; j < _shape.getHeight(); j++) {
			for (int i = 0; i < _shape.getWidth(); i++) {
				if (_shape.getSlotType(new InternalCrosswordPosition(i, j)) == SlotType.FRAME_SLOT) {
					layout[j][i] = -2;
				} else {
					layout[j][i] = -1;
				}
			}
		}
		for (int j = 0; j < _shape.getHeight(); j++) {
			for (int i = 0; i < _shape.getWidth(); i++) {
				CrosswordPosition pos2;
				for (CrosswordEntry entry : _entries) {
					CrosswordPosition pos1 = entry.getPosition();
					if (pos1.getX() == i && pos1.getY() == j) {
						if (pos1.isVertical())
							pos2 = new InternalCrosswordPosition(i, j + entry.getLength() - 1,
									true);
						else
							pos2 = new InternalCrosswordPosition(i + entry.getLength() - 1, j,
									false);
						if (layout[j][i] <= 0) {
							layout[j][i] = entryIndex;
							entryIndex++;
						}
						for (int i1 = i; i1 <= pos2.getX(); i1++) {
							for (int j1 = j; j1 <= pos2.getY(); j1++) {
								if (layout[j1][i1] < 0)
									layout[j1][i1] = 0;
								_correctLetters[i1][j1] = Character.toUpperCase(entry.getTerm()
										.charAt(j1 + i1 - j - i));
							}
						}
					}
				}
			}
		}
		_maxEntryIndex = entryIndex;
	}

	// Flags for GUI
	char _guesses[][];
	char _correctLetters[][];
	boolean gUpdateActiveAreaFlag = false;
	boolean gChangedActiveAreaFlag = false;

	/**
	 * Set gui items
	 */
	public void makeGUI() {
		makeLayout();
		setLayout(null);
		setResizable(false);
		_downClues = new JTextArea("");
		_acrossClues = new JTextArea("");
		// setMenuBar(mBar);
		JLabel label1, label2;
		label1 = new JLabel("Accross");
		add(label1);
		label2 = new JLabel("Down");
		add(label2);
		label2.setBounds(kBlockWidth * kBlocksWide + kPadding * 2, kPadding
				- 10
				+ Math.max((kBlocksHigh * kBlockHeight + kPadding) / 2, 150)
				+ kPadding, 150, 15);
		label2.setFont(new Font("Courier", Font.BOLD, 15));
		label1.setFont(new Font("Courier", Font.BOLD, 15));
		label1.setBounds(kBlockWidth * kBlocksWide + kPadding * 2,
				kPadding - 15, 125, 20);
		_paneh = new JScrollPane(_acrossClues);
		_panev = new JScrollPane(_downClues);

		_paneh.setBounds(kBlockWidth * kBlocksWide + kPadding * 2,
				kPadding + 5, 250, Math.max(
						(kBlocksHigh * kBlockHeight + kPadding) / 2, 150));
		_panev.setBounds(kBlockWidth * kBlocksWide + kPadding * 2, kPadding + 5
				+ Math.max((kBlocksHigh * kBlockHeight + kPadding) / 2, 150)
				+ kPadding, 250, Math.max(kBlocksHigh * kBlocksHigh + kPadding
				/ 2, 150));
		_acrossClues.setEditable(false);
		_downClues.setEditable(false);
		_acrossClues.addCaretListener(this);
		_downClues.addCaretListener(this);
		JButton solveButton = new JButton("Solve");
		solveButton.addActionListener(this);
		JCheckBox checkButton = new JCheckBox("Show correct letters");
		checkButton.addActionListener(this);
		add(_paneh);
		add(_panev);

		if (kBlocksWide < 14) {
			// Vertical button orientation
			solveButton.setBounds(kPadding, kBlocksHigh * kBlockHeight
					+ kPadding * 3, (kBlockWidth * kBlocksWide) +kPadding-5, 40);
			checkButton.setBounds(kPadding, kBlocksHigh * kBlockHeight
					+ kPadding * 4 + 40, (kBlockWidth * kBlocksWide) + kPadding -5, 40);
		} else {
			// Horisontal button orientation
			solveButton.setBounds(
					(kBlockWidth * kBlocksWide + kPadding * 2) / 2, kBlocksHigh
							* kBlockHeight + kPadding * 3,
					(kBlockWidth * kBlocksWide) / 2, 40);
			checkButton.setBounds(kPadding, kBlocksHigh * kBlockHeight
					+ kPadding * 3,
					(kBlockWidth * kBlocksWide + kPadding * 2) / 2, 40);
		}
		add(solveButton);
		add(checkButton);
		fillTextAreas();
		pack();
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK
				| AWTEvent.WINDOW_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK
				| AWTEvent.WINDOW_FOCUS_EVENT_MASK);
		setSize(kBlockWidth * kBlocksWide + kPadding * 4 + 250, Math.max(
				kBlocksHigh * kBlockHeight + kPadding * 3 + 150, 400));
		requestFocus();
		NewGame();
	}

	/**
	 * Initializes constants at the begining of game
	 */
	public void NewGame() {
		for (int j = 0; j < kBlocksHigh; j++) {
			for (int i = 0; i < kBlocksWide; i++) {
				_guesses[i][j] = ' ';
			}
		}
		_oldEntry = null;
		_currentEntry = _entries.iterator().next();
		_curPosition = _currentEntry.getPosition();
		setActiveBlock(_currentEntry.getPosition());
	}

	public CrosswordEntry findEntry(int x, int y, boolean isVertical) {
		for (CrosswordEntry entry : _entries) {
			if ((entry.getPosition().getX() == x && entry.getPosition().getY() == y)
					&& ((isVertical == entry.getPosition().isVertical())))
				return entry;
		}
		return null;
	}

	/**
	 * Highlights entries in cue lists
	 * 
	 * @return the highlighted entry
	 */
	public CrosswordEntry getHighlightEntry(CrosswordPosition pos) {
		int x = pos.getX(), y = pos.getY();
		boolean isVertical = pos.isVertical();
		if (!isVertical) {
			while (x > 0 && x<kBlocksWide && layout[y][x] >= 0 && (findEntry(x, y, false) == null || findEntry(x, y, false).getLength()<=pos.getX()-x))
				x--;
		} else {
			while (y > 0 && y<kBlockHeight && layout[y][x] >= 0 && (findEntry(x, y, true) == null || findEntry(x, y, true).getLength()<=pos.getY()-y))
				y--;
		}
		CrosswordEntry entry = findEntry(x, y, isVertical);
		return entry;
	}

	/**
	 * Redraw function
	 */
	public void paint(Graphics g) {
		paintComponents(g);
		paintWord(g, null);
	}

	/**
	 * Draws the board and words on it
	 * 
	 * @param g
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 */
	void paintWord(Graphics g, CrosswordEntry entry) {
		int left = kPadding;// (getSize().width / 2) - (viewWidth / 2);
		int top = kPadding * 3;// (kPadding * 2) + kQuestionAreaHeight;
		int tempLeft = 0;
		int tempTop = 0;
		int minX = entry == null ? 0 : entry.getPosition().getX();
		int maxX = minX;
		int minY = entry == null ? 0 : entry.getPosition().getY();
		int maxY = minY;
		if (entry != null) {
			if (entry.getPosition().isVertical())
				maxY += entry.getTerm().length();
			else
				maxX += entry.getTerm().length();
		} else {
			maxX = kBlocksWide - 1;
			maxY = kBlocksHigh - 1;
		}

		Font f = new java.awt.Font("Helvetica", 0, 12);
		g.setFont(f);

		Font numFont = new java.awt.Font("Helvetica", 0, 10);

		Font answerFont = new java.awt.Font("Helvetica", 0, 18);
		Font answerGuessFont = new java.awt.Font("Helvetica", Font.BOLD, 18);
		FontMetrics answerFontMetrics = g.getFontMetrics(answerFont);

		for (int j = minY; j <= maxY; j++) {
			for (int i = minX; i <= maxX; i++) {
				tempLeft = left + (i * kBlockWidth);
				tempTop = top + (j * kBlockHeight);

				if (InEntry(i, j, _currentEntry)) {
					if (i == _curPosition.getX() && j == _curPosition.getY())
						g.setColor(Color.cyan);
					else
						g.setColor(Color.yellow);
					g.fillRect(tempLeft, tempTop, kBlockWidth, kBlockHeight);
				} else {
					g.setColor(Color.white);
					g.fillRect(tempLeft, tempTop, kBlockWidth, kBlockHeight);
				}

				g.setColor(Color.black);
				g.drawRect(tempLeft, tempTop, kBlockWidth, kBlockHeight);

				if (layout[j][i] == -1) {
					g.setColor(Color.black);
					g.fillRect(tempLeft, tempTop, kBlockWidth, kBlockHeight);
				} else if (layout[j][i] == -2) {
					g.setColor(Color.BLUE);
					g.fillRect(tempLeft, tempTop, kBlockWidth, kBlockHeight);
				} else if (layout[j][i] != 0) {
					String numStr = String.valueOf(layout[j][i]);

					g.setFont(numFont);
					g.drawString(numStr, tempLeft + 4, tempTop + 10);
					if (findEntry(i, j, false)!=null) {
						int shift=-6;
						if (layout[j][i]>=10) shift+=6;
						if (layout[j][i]>=100) shift+=6;
						g.drawLine(tempLeft+16+shift, tempTop+5, tempLeft+19+shift, tempTop+5);
						g.drawLine(tempLeft+19+shift, tempTop+5, tempLeft+17+shift, tempTop+3);
						g.drawLine(tempLeft+19+shift, tempTop+5, tempLeft+17+shift, tempTop+7);				
					} 
					if (findEntry(i,j,true)!=null) {
						g.drawLine(tempLeft+6, tempTop+15, tempLeft+6, tempTop+11);
						g.drawLine(tempLeft+6, tempTop+15, tempLeft+4, tempTop+13);
						g.drawLine(tempLeft+6, tempTop+15, tempLeft+8, tempTop+13);				}						
					}
				char guess = _guesses[i][j];
				if (layout[j][i] >= 0) {
					if (guess != ' ') {
						int sWidth = 0;
						if (!_isHighlight || _correctLetters[i][j] != guess) {
							g.setFont(answerFont);
							g.setColor(Color.red);
						} else {
							g.setColor(Color.black);
							g.setFont(answerGuessFont);
						}

						sWidth = answerFontMetrics.stringWidth(guess + "");
						g.drawString(guess + "", tempLeft
								+ ((kBlockWidth / 2) - (sWidth / 2)),
								(tempTop + kBlockHeight) - 6);
					}

				}

			}
		}
	}

	/**
	 * Returns true if (x,y) is inside active block
	 * 
	 * @param x
	 * @param y
	 * @return true if selection is in active block
	 */
	private boolean InEntry(int x, int y, CrosswordEntry entry) {
		CrosswordPosition p = entry.getPosition();
		if (x < p.getX())
			return (false);
		if (p.isVertical() && x > p.getX())
			return (false);
		if (y < p.getY())
			return (false);
		if (!p.isVertical() && y > p.getY())
			return (false);
		if (p.isVertical() && y > p.getY() + entry.getTerm().length()-1)
			return (false);
		if (!p.isVertical() && x > p.getX() + entry.getTerm().length()-1)
			return (false);
		return (true);
	}

	/**
	 * Extends x,y coordinates into active block
	 * 
	 * @param x
	 * @param y
	 * @param isVert
	 */
	private void setActiveBlock(CrosswordPosition pos) {
		CrosswordEntry entry = getHighlightEntry(pos);
		if (entry != null) {
			_oldEntry = _currentEntry;
			_currentEntry = entry;
		} else {
			changeDirection();
		}
	}

	CrosswordEntry _currentEntry, _oldEntry;

	/**
	 * Updates requested area with new highlights
	 */
	public void update(Graphics g) {
		super.update(g);

		if (gChangedActiveAreaFlag == false && gUpdateActiveAreaFlag == false) {
			paint(g);
			return;
		}

		if (gChangedActiveAreaFlag == true) {
			paintWord(g, _oldEntry);
			paintWord(g, _currentEntry);
			gChangedActiveAreaFlag = false;
			return;
		}

		// -----------------------------------------------
		if (gUpdateActiveAreaFlag == true) {
			gUpdateActiveAreaFlag = false;
			paintWord(g, _currentEntry);
			return;
		}
	}

	/**
	 * Handles mouse selection of entries
	 */
	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);
		if (e.getID() != MouseEvent.MOUSE_CLICKED)
			return;
		int x = e.getX();
		int y = e.getY();
		int left = kPadding; // (getSize().width / 2) - (viewWidth / 2);
		int top = kPadding * 3;// (kPadding * 2) + kQuestionAreaHeight;

		if (x < left || x > left + kBlocksWide * kBlockWidth)
			return;
		if (y < top || y > top + kBlockHeight * kBlocksHigh)
			return;
		requestFocus();
		if (!(e.getID() == MouseEvent.MOUSE_CLICKED))
			return;

		int j = y - top;
		j /= kBlockHeight;

		int i = x - left;
		i /= kBlockWidth;

		if (i >= 0 && i < kBlocksWide && j >= 0 && j < kBlocksHigh) {
			if (layout[j][i] >= 0) {
				if (_curPosition.getX() == i && _curPosition.getY() == j) {
					changeDirection();
					selectText();
					gChangedActiveAreaFlag = true;
					repaint();
					return;
				} else {
					_curPosition = new InternalCrosswordPosition(i, j, _curPosition.isVertical());
				}
				if (InEntry(i, j, _currentEntry)) {
					gUpdateActiveAreaFlag = true;
					repaint();
				} else {
					setActiveBlock(_curPosition);
					gChangedActiveAreaFlag = true;
					repaint();
				}
				if (!selectText())
					changeDirection();
				return;
			}
		}
		return;
	}

	/**
	 * Redraws window on refocus
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_ACTIVATED)
			repaint();
	}

	protected void processFocusEvent(FocusEvent e) {
		super.processFocusEvent(e);
		if (e.getID() == FocusEvent.FOCUS_GAINED)
			repaint();
	}

	/**
	 * Handles keys letters- fill letter space - change direction
	 */
	protected void processKeyEvent(KeyEvent e) {
		if (!(e.getID() == KeyEvent.KEY_TYPED))
			return;
		char key = e.getKeyChar();
		int x = _curPosition.getX();
		int y = _curPosition.getY();
		boolean isVertical = _curPosition.isVertical();
		if ((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z')
				|| key == ' ' || key == '-') {

			_guesses[x][y] = Character.toUpperCase(key);
			if (!isVertical) {
				if (x < kBlocksWide - 1 && layout[y][x + 1] >= 0)
					_curPosition = new InternalCrosswordPosition(x + 1, y, isVertical);
			} else {
				if (y < kBlocksHigh - 1 && layout[y + 1][x] >= 0)
					_curPosition = new InternalCrosswordPosition(x, y + 1, isVertical);
			}
			gUpdateActiveAreaFlag = true;
			repaint();
			return;
		}

		switch ((char) key) {
		case '\n':
			boolean olddir = _curPosition.isVertical();
			changeDirection();
			if (olddir == _curPosition.isVertical())
				return;
			gChangedActiveAreaFlag = true;
			repaint();
			break;
		case 0x08:
			if (_guesses[x][y] != ' ') {
				_guesses[x][y] = ' ';
				gUpdateActiveAreaFlag = true;
				repaint();
			} else {
				if (!isVertical) {
					if (x != 0 && layout[y][x - 1] >= 0) {
						_curPosition = new InternalCrosswordPosition(x - 1, y, isVertical);
						_guesses[x][y] = ' ';
						gUpdateActiveAreaFlag = true;
						repaint();
					}
				} else {
					if (y != 0 && layout[y - 1][x] >= 0) {
						_curPosition = new InternalCrosswordPosition(x, y - 1, isVertical);
						_guesses[x][y] = ' ';
						gUpdateActiveAreaFlag = true;
						repaint();
					}
				}
			}
			break;
		default:
			java.awt.Toolkit.getDefaultToolkit().beep();
			break;
		}
		return;
	}

	/* ---------------------------------------------- */

	boolean ptInRect(int x, int y, int left, int top, int right, int bottom) {
		if (x < left)
			return (false);

		if (x > right)
			return (false);

		if (y < top)
			return (false);

		if (y > bottom)
			return (false);

		return (true);
	}

	/**
	 * Changes direction of selection
	 */
	void changeDirection() {
		CrosswordPosition tmpPosition = new InternalCrosswordPosition(_curPosition.getX(), _curPosition
				.getY(), !_curPosition.isVertical());
		CrosswordEntry newEntry = getHighlightEntry(tmpPosition);
		if (newEntry != null) {
			setActiveBlock(newEntry.getPosition());
			_currentEntry = newEntry;
			_curPosition = tmpPosition;
			selectText();
		}
	}

	/**
	 * Handles solve button and checkbox
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Solve")) {
			for (int i = 0; i < kBlocksWide; i++) {
				for (int j = 0; j < kBlocksHigh; j++) {
					_guesses[i][j] = _correctLetters[i][j];
				}
			}
			repaint();
		} else if (e.getActionCommand().equals("Show correct letters")) {
			_isHighlight = !_isHighlight;
			repaint();
		} else
			repaint();
	}

	/**
	 * Handles selection of cues
	 */
	public void caretUpdate(CaretEvent e) {
		String text = ((JTextArea) e.getSource()).getText();
		int end = text.indexOf('\n', e.getDot());
		if (end == -1)
			end = text.length();
		int start;
		for (start = e.getDot() - 1; start >= 0 && text.charAt(start) != '\n'; start--)
			;
		start = text.indexOf('.', start) + 1;
		String glos = text.substring(start, end);
		for (CrosswordEntry entry : _board.getCrosswordEntries()) {
			if (entry.getDefinition().compareToIgnoreCase(glos) == 0) {
				_curPosition = entry.getPosition();
				_currentEntry = entry;

				gUpdateActiveAreaFlag = true;
				selectText();
				repaint();
				break;
			}
		}
	}
	private class InternalCrosswordPosition implements CrosswordPosition {
		public InternalCrosswordPosition(int x,int y) {
			this(x,y,true);
		}

		public InternalCrosswordPosition(int x,int y,boolean isVertical) {
			_x=x;
			_y=y;
			_isVertical=isVertical;
		}
		
		
		/* (non-Javadoc)
		 * @see oop.ex4.crosswords.CrosswordPosition#getX()
		 */
		public int getX() {
			return _x;
		}
		
		/* (non-Javadoc)
		 * @see oop.ex4.crosswords.CrosswordPosition#getY()
		 */
		public int getY() {
			return _y;
		}
		/* (non-Javadoc)
		 * @see oop.ex4.crosswords.CrosswordPosition#isVertical()
		 */
		public boolean isVertical() {
			return _isVertical;
		}
		int _x,_y;
		boolean _isVertical;
	}
}
