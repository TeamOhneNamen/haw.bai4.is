package app.logic;

//Based on this idea: https://cs.stackexchange.com/a/13455
public class Heuristic {

    public static double determineScore(String playerColor){
        return determineHorizontalScore(playerColor);
    }

    protected static double determineHorizontalScore(String playerColor) {
        double score = 0;
        for (int i = 0; i < Board.rowLength(); i++) {
            int j = 0;
            int column;
            while (j < Board.columnLength()) {
                column = j;
                j += 1;
                // 1 in a row
                if (playerColor.equals(Board.get(i, column))) {
                    score++;
                    // space to the left
                    if(Board.NO_COLOR.equals(Board.get(i, column - 1))){
                        score += 0.5;
                    }
                    j += 2;
                    // 2 in a row
                    if (playerColor.equals(Board.get(i, column + 1))) {
                        score++;
                        j += 3;
                        // 3 in a row
                        if (playerColor.equals(Board.get(i, column + 2))) {
                            score++;
                            // 3 in a row with space to the right
                            if (Board.NO_COLOR.equals(Board.get(i, column + 3))) {
                                score += 0.5;
                            }
                            System.out.println("3 in a row with space to the right");
                            j += 4;
                            // 2 in a row with space to the right
                        } else if (Board.NO_COLOR.equals(Board.get(i, column+3))) {
                            score += 0.5;
                        }
                        // 1 in a row with space to the right
                    } else if (Board.NO_COLOR.equals(Board.get(i, column+2))) {
                        score += 0.5;
                    }
                }

            }
        }
        return score;
    }
}
