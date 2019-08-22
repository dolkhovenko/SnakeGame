import java.util.ArrayList;

class Snake {
    ArrayList<BlockPosition> snake = new ArrayList();

    Snake() {
        snake.add(new BlockPosition(100, 20, BlockPosition.PartOfBody.HEAD));
        snake.add(new BlockPosition(80, 20, BlockPosition.PartOfBody.BODY));
        snake.add(new BlockPosition(60, 20, BlockPosition.PartOfBody.BODY));
        snake.add(new BlockPosition(40, 20, BlockPosition.PartOfBody.BODY));
        snake.add(new BlockPosition(20, 20, BlockPosition.PartOfBody.TAIL));
    }

    public ArrayList<BlockPosition> getSnake() {
        return this.snake;
    }
}
