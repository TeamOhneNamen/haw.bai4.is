package app.logic;

public class BoardElement {
    public int row;
    public int column;
    public String data;
    public BoardElement(int row, int column, String data){
        this.row = row;
        this.column = column;
        this.data = data;
    }

    @Override
    public String toString(){
        return "row: " + String.valueOf(row) + "col: " +String.valueOf(column) + " value: " + data;
    }
}
