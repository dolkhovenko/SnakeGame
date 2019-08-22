import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JPanel {
    Snake snake = new Snake();
    Direction lastDirection = Direction.RIGHT;
    static Keyboard key;
    Boolean isGameStarted = false;
    Boolean isGamePaused = false;
    Boolean isGameOver = false;
    Boolean isStartText = true;
    String startText = "Press S for start game";
    String gameOver = "GAME OVER!\n Your score: ";
    static JFrame f;
    Dish dish = new Dish(snake);
    int score = 0;

    private MainWindow() {
        key = new Keyboard(this);
        f = new JFrame("Snake");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(Constant.MAIN_WINDOW_SIZE_WIDTH, Constant.MAIN_WINDOW_SIZE_HEIGHT);
        f.addKeyListener(key);
        f.setFocusable(true);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        showGameWindow(mainWindow);
    }

    private static void showGameWindow(MainWindow mainWindow) {
        f.add(mainWindow);

        f.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);

        g.drawRect(Constant.BORDER_WINDOW_SIZE_START_X, Constant.BORDER_WINDOW_SIZE_START_Y, Constant.BORDER_WINDOW_SIZE_WIDTH, Constant.BORDER_WINDOW_SIZE_HEIGHT);

        if (isStartText) {
            setText(g, startText);
        } else if (isGameOver) {
            setText(g, gameOver + score);
        } else {

            g.setColor(Color.RED);
            this.snake.getSnake().forEach(z ->
                    g.fillOval(z.getX(), z.getY(), Constant.SIZE, Constant.SIZE)
            );
            g.setColor(Color.BLUE);
            if (dish == null) {
                dish = new Dish(snake);
            }
            g.fillOval(dish.getX(), dish.getY(), Constant.SIZE, Constant.SIZE);

            showScore(g);
        }
    }

    //Showing of the game score and speed
    private void showScore(Graphics g) {
        g.drawString("SCORE: " + score + "\nSPEED: " + (1000 - gameSpeed()), 150, 400);
    }

    private void setText(Graphics g, String text) {
        Color lastColor = g.getColor();
        g.setColor(new Color(16, 112, 54));//Dark Green color
        g.drawString(text, 120, 220);
        g.setColor(lastColor);
    }

    private void increaseScore() {
        score += 1;
    }

    private void resetScore() {
        score = 0;
    }

    void move(Direction direction) {
        int x, y;
        switch (direction) {
            case UP:
                x = 0;
                y = Constant.UP;
                break;
            case DOWN:
                x = 0;
                y = Constant.DOWN;
                break;
            case RIGHT:
                x = Constant.RIGHT;
                y = 0;
                break;
            case LEFT:
                x = Constant.LEFT;
                y = 0;
                break;
            default:
                x = 0;
                y = 0;
        }

        ArrayList<BlockPosition> snake = this.snake.getSnake();
        BlockPosition lastPosition = moveAllSnake(snake, x, y);
        BlockPosition headOfSnake = snake.get(0);

        eatDish(headOfSnake, lastPosition);
        borderOut(headOfSnake);
        selfEat(snake, headOfSnake);

        repaint();
    }

    //Move all snake by set direction
    //Return last placed block
    private BlockPosition moveAllSnake(ArrayList<BlockPosition> snake, int x, int y) {
        BlockPosition lastPosition = null;
        BlockPosition newPosition;
        for (int i = 0; i < snake.size(); i++) {
            BlockPosition currentPosition = snake.get(i);
            if (i == 0) {
                lastPosition = new BlockPosition(currentPosition.getX(), currentPosition.getY(), currentPosition.getPartOfBody());
                if ((new BlockPosition(currentPosition.getX() + x, currentPosition.getY() + y, BlockPosition.PartOfBody.BODY)).equals(snake.get(1))) {
                    currentPosition.move(currentPosition.getX() - x, currentPosition.getY() - y);
                } else {
                    currentPosition.move(currentPosition.getX() + x, currentPosition.getY() + y);
                }
            } else {
                newPosition = new BlockPosition(currentPosition.getX(), currentPosition.getY(), currentPosition.getPartOfBody());
                currentPosition.move(lastPosition.getX(), lastPosition.getY());
                lastPosition = newPosition;
            }
        }
        return lastPosition;
    }

    //Check that snake has no opportunity to go through own body
    private void selfEat(ArrayList<BlockPosition> snake, BlockPosition head) {
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver();
            }
        }
    }

    //Check if snake eat dish. If yes - raise snake
    private void eatDish(BlockPosition head, BlockPosition lastBlock) {
        if (head.getX() == dish.getX() && head.getY() == dish.getY()) {
            dish = null;
            this.snake.getSnake().add(lastBlock);
            increaseScore();
        }
    }

    //Check that snake haven't gone out
    private void borderOut(BlockPosition head) {
        if (head.getX() < Constant.BORDER_WINDOW_SIZE_START_X || head.getX() > Constant.BORDER_WINDOW_SIZE_WIDTH || head.getY() < Constant.BORDER_WINDOW_SIZE_START_Y || head.getY() > Constant.BORDER_WINDOW_SIZE_HEIGHT) {
            gameOver();
        }
    }

    void startGame() {
        isStartText = false;
        isGameOver = false;
        resetScore();
        if (!isGameStarted) {
            isGameStarted = true;
            showGameWindow(this);

            repaint();
        }
    }

    void pauseGame() {
        isGamePaused = !isGamePaused;
    }

    private void gameOver() {
        isGameStarted = false;
        isGameOver = true;
        repaint();
    }

    void rePlayGame() {
        if (!isGameStarted) {
            refreshSnake();
            repaint();
        }
    }

    //Reset snake to start condition
    private void refreshSnake() {
        this.snake = new Snake();
        this.lastDirection = Direction.RIGHT;
    }

    //Game speed depend on size of snake
    public int gameSpeed() {
        int length = this.snake.getSnake().size();
        if (length <= 5) {
            return Constant.DEFAULT_SPEED;
        } else if (length < 10) {
            return Constant.DEFAULT_SPEED - 100;
        } else if (length < 20) {
            return Constant.DEFAULT_SPEED - 200;
        } else if (length < 30) {
            return Constant.DEFAULT_SPEED - 250;
        } else if (length < 40) {
            return Constant.DEFAULT_SPEED - 300;
        } else if (length < 50) {
            return Constant.DEFAULT_SPEED - 350;
        } else {
            return Constant.DEFAULT_SPEED - 400;
        }
    }


}


