package ladder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ladder.domain.request.LadderRequest;

public class Ladder {

    private final List<Line> lines;

    public static Ladder of(LadderRequest request) {
        List<Line> tempLines = new ArrayList<>();
        for (int i = 0; i <= request.getHeight(); i++) {
            tempLines.add(Line.from(request.getWidth(), request.getStrategy()));
        }
        return new Ladder(tempLines);
    }

    public Ladder(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public int resultPosition(int currentPosition) {
        int position = currentPosition;
        for (Line line : lines) {
            position = getPosition(position, line);
        }

        return position;
    }

    public List<Integer> resultPositions(List<Integer> positions) {
        return positions.stream()
                .map(this::resultPosition)
                .collect(Collectors.toList());
    }

    private int getPosition(int position, Line line) {
        List<Boolean> points = line.getPoints();

        position = getLeftHasLine(position, points) ? position - 1 : getRightHasLine(position, points) ? position + 1 : position;
        return position;
    }

    private Boolean getRightHasLine(int currentPosition, List<Boolean> points) {
        Boolean rightHasLine = false;
        if (currentPosition != points.size()) {
            rightHasLine = points.get(currentPosition);
        }

        return rightHasLine;
    }

    private Boolean getLeftHasLine(int currentPosition, List<Boolean> points) {
        Boolean leftHasLine = false;
        if (currentPosition != 0) {
            leftHasLine = points.get(currentPosition - 1);
        }

        return leftHasLine;
    }

}
