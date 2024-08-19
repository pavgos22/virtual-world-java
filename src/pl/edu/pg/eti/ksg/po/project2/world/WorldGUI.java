package pl.edu.pg.eti.ksg.po.project2.world;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import pl.edu.pg.eti.ksg.po.project2.enums.Direction;
import pl.edu.pg.eti.ksg.po.project2.game.*;

public class WorldGUI implements ActionListener, KeyListener {
    private final JFrame jFrame;
    private final JMenuItem newGame, load, save, exit;
    private BoardPanel boardPanel = null;
    private NarratorPanel narratorPanel = null;
    private Legend legend = null;
    private final JPanel mainPanel;
    private final int SPACE;
    private final int ADDITIONAL_WIDTH;
    private final int BIG_SPACE;
    private World world;

    public WorldGUI(String title) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        SPACE = dimension.height / 100;
        BIG_SPACE = 10;
        ADDITIONAL_WIDTH = 260;

        jFrame = new JFrame(title);
        jFrame.setBounds((dimension.width - 1000) / 2, (dimension.height - 700) / 2, 1000, 700);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(newGame);
        menu.add(load);
        menu.add(save);
        menu.add(exit);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(40, 40, 40));
        mainPanel.setLayout(null);

        jFrame.addKeyListener(this);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == newGame) {
            startNewGame();
        }
        if (event.getSource() == load) {
            loadGame();
        }
        if (event.getSource() == save) {
            saveGame();
        }
        if (event.getSource() == exit) {
            jFrame.dispose();
        }
    }

    private void startNewGame() {
        double difficultyLevel = 0;
        Narrator.clearComments();
        int sizeX = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                "Enter world width", "20"));
        int sizeY = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                "Enter world height", "20"));
        String[] options = {"Peaceful", "Easy", "Medium", "Hard"};
        ImageIcon icon = new ImageIcon("src/images/smile.png");
        String n = (String) JOptionPane.showInputDialog(null, "Select difficulty level",
                "Level of difficulty", JOptionPane.QUESTION_MESSAGE, icon, options, options[2]);
        if (Objects.equals(n, options[0]))
            difficultyLevel = 0;
        if (Objects.equals(n, options[1]))
            difficultyLevel = 0.2;
        if (Objects.equals(n, options[2]))
            difficultyLevel = 0.4;
        if (Objects.equals(n, options[3]))
            difficultyLevel = 0.6;

        if (sizeX <= 100 && sizeY <= 100) {
            world = new World(sizeX, sizeY, this);
            world.generateWorld(difficultyLevel);
            resetGUIComponents();
            startGame();
        } else {
            String message = "Maximum board size is 100x100";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGame() {
        Narrator.clearComments();
        String nameOfFile = JOptionPane.showInputDialog(jFrame, "Enter file name", "save");

        if (world != null) {
            world.resetWorld();
            resetGUIComponents();
        }

        world = World.loadWorld(nameOfFile, this);
        if (world == null)
            throw new RuntimeException("World cannot be null");
        world.setWorldGUI(this);

        resetGUIComponents();
        startGame();
    }

    private void saveGame() {
        String nameOfFile = JOptionPane.showInputDialog(jFrame, "Enter file name", "save");
        world.saveWorld(nameOfFile);
        Narrator.addComment("Saved successfully!");
        narratorPanel.refreshComments();
    }

    private void resetGUIComponents() {
        if (boardPanel != null)
            mainPanel.remove(boardPanel);
        if (narratorPanel != null)
            mainPanel.remove(narratorPanel);
        if (legend != null)
            mainPanel.remove(legend);
    }

    private void startGame() {
        narratorPanel = new NarratorPanel(mainPanel, SPACE);
        mainPanel.add(narratorPanel);
        boardPanel = new BoardPanel(world, this, mainPanel, SPACE, BIG_SPACE, ADDITIONAL_WIDTH);
        mainPanel.add(boardPanel);
        legend = new Legend(boardPanel, mainPanel, SPACE);
        mainPanel.add(legend);

        refreshWorld();
    }

    public void refreshWorld() {
        boardPanel.refreshBoard();
        narratorPanel.refreshComments();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public World getWorld() {
        return world;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public NarratorPanel getNarratorPanel() {
        return narratorPanel;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (world != null && world.isPause()) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                // No-op
            } else if (world.getHumanAlive()) {
                if (keyCode == KeyEvent.VK_UP) {
                    world.getHuman().setMoveDirection(Direction.UP);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    world.getHuman().setMoveDirection(Direction.DOWN);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    world.getHuman().setMoveDirection(Direction.LEFT);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    world.getHuman().setMoveDirection(Direction.RIGHT);
                } else if (keyCode == KeyEvent.VK_P) {
                    Power tmpPower = world.getHuman().getPower();
                    if (tmpPower.getCanActivate()) {
                        tmpPower.activate();
                        Narrator.addComment("Ability 'Alzur's Shield' active (for " + tmpPower.getDuration() + " rounds)");
                    } else if (tmpPower.getActive()) {
                        Narrator.addComment("Ability has already been activated " + "(Remaining time: " + tmpPower.getDuration() + " turns)");
                        narratorPanel.refreshComments();
                        return;
                    } else {
                        Narrator.addComment("Ability can be activated after " + tmpPower.getCooldown() + " rounds");
                        narratorPanel.refreshComments();
                        return;
                    }
                } else {
                    Narrator.addComment("\nUnknown key pressed");
                    narratorPanel.refreshComments();
                    return;
                }
            } else if (!world.getHumanAlive() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P)) {
                Narrator.addComment("Human is dead");
                narratorPanel.refreshComments();
                return;
            } else {
                Narrator.addComment("\nUnknown key pressed");
                narratorPanel.refreshComments();
                return;
            }
            Narrator.clearComments();
            world.setPause(false);
            world.executeTurn();
            refreshWorld();
            world.setPause(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }
}
