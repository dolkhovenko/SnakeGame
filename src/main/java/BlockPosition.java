/**
 * Almost everything in game created by blocks
 */

class BlockPosition {
    private int x, y;
    private PartOfBody partOfBody;

    enum PartOfBody {
        HEAD,
        BODY,
        TAIL
    }

    BlockPosition(int x, int y, PartOfBody p) {
        this.x = x;
        this.y = y;
        this.partOfBody = p;
    }

    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean equals(BlockPosition obj) {
        return this.x == obj.x && this.y == obj.y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    PartOfBody getPartOfBody() {
        return partOfBody;
    }
}
