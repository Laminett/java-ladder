package laddergameplay.domain.ladder;

import laddergameplay.strategy.LineStrategy;

import java.util.*;

public class Ladder {

    public static final int FIRST_PILLAR_OF_LADDER = 0;

    private final List<Line> ladder = new ArrayList<>();
    private final int widthOfLadder;

    public Ladder(int widthOfLadder, int heightOfLadder, LineStrategy lineStrategy) {
        this.widthOfLadder = widthOfLadder;

        for (int i = 0; i < heightOfLadder; i++) {
            ladder.add(new Line(widthOfLadder, lineStrategy));
        }
    }

    public List<Integer> result(){
        List<Integer> resultList = new ArrayList<>();

        for (int startPosition = FIRST_PILLAR_OF_LADDER; startPosition <= widthOfLadder; startPosition++) {
            int resultPosition = playGame(startPosition);
            resultList.add(resultPosition);
        }

        return resultList;
    }

    private int playGame(int startPosition) {
        int currentPosition = startPosition;

        for (Line line : ladder) {
            currentPosition = new MovePosition(currentPosition).in(line);
        }

        return currentPosition;
    }

    private class MovePosition {

        private final int[] checkPointArr;
        private final int[] movedPositionArr;

        private MovePosition(int currentPosition) {
            checkPointArr = new int[]{leftPointOf(currentPosition), rightPointOf(currentPosition)};
            movedPositionArr = new int[]{moveLeft(currentPosition), moveRight(currentPosition), currentPosition};
        }

        private int moveRight(int currentPosition) {
            return currentPosition + 1;
        }

        private int moveLeft(int currentPosition) {
            return currentPosition - 1;
        }

        private int rightPointOf(int currentPosition) {
            return currentPosition;
        }

        private int leftPointOf(int currentPosition) {
            return currentPosition - 1;
        }

        private int in(Line line) {
            int currentPosition = movedPositionArr[2];

            for (int i = 0; i < checkPointArr.length; i++) {
                currentPosition = movePositionInLine(line, currentPosition, i);
            }

            return currentPosition;
        }

        private int movePositionInLine(Line line, int currentPosition, int i) {
            if (checkPointArr[i] < FIRST_PILLAR_OF_LADDER
                    || checkPointArr[i] >= widthOfLadder
                    || !line.getPoints().get(checkPointArr[i])) {
                return currentPosition;
            }

            return movedPositionArr[i];
        }
    }

    public void addResultTo(StringBuilder stringBuilder) {
        for (Line line : ladder) {
            line.addResultTo(stringBuilder);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ladder)) return false;
        Ladder ladder1 = (Ladder) o;
        return widthOfLadder == ladder1.widthOfLadder && Objects.equals(ladder, ladder1.ladder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ladder, widthOfLadder);
    }
}