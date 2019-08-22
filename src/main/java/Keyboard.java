import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard settings and Listener
 */
public class Keyboard implements KeyListener, Runnable {
    private MainWindow mainWindow;

    Keyboard(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        Thread worker = new Thread(this);
        worker.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                mainWindow.lastDirection = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                mainWindow.lastDirection = Direction.DOWN;
                break;
            case KeyEvent.VK_RIGHT:
                mainWindow.lastDirection = Direction.RIGHT;
                break;
            case KeyEvent.VK_LEFT:
                mainWindow.lastDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_S:
                mainWindow.rePlayGame();
                mainWindow.startGame();
                break;
            case KeyEvent.VK_P:
                mainWindow.pauseGame();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(mainWindow.gameSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mainWindow.isGameStarted && !mainWindow.isGamePaused) {
                mainWindow.move(mainWindow.lastDirection);
            }
        }
    }
}
