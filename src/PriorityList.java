import com.github.bhlangonijr.chesslib.move.Move;

public class PriorityList{
    Move item; // item
    double weight; //priority, we will sort high to low
    PriorityList next;

    PriorityList() {
        this.item = null;
        this.weight = 0;
        this.next = null;
    }

    PriorityList(Move m, double w) {
        this.item = m;
        this.weight = w;
        this.next = null;
    }

    public void push(Move m, double w) {
        if (this.next == null) {
            this.next = new PriorityList(m, w);
        } else {
            if (this.weight > w) {

            }
        }
    }

}
