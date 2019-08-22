/**
 * Dish for snake
 */
class Dish {
    int x, y;
    private boolean isClose;

    Dish(Snake snake) {
        do {
            isClose = false;
            x = (int) ((Math.random() * 18) + 1) * Constant.SIZE;// Count of possible blocks on the screen
            y = (int) ((Math.random() * 17) + 1) * Constant.SIZE;//
            for (BlockPosition b : snake.getSnake()
            ) {
                if (b.getX() == this.x && b.getY() == this.y) {
                    isClose = true;
                }
            }
        } while (isClose);


    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
