import java.util.HashMap;

public class GameHistory {
    public HashMap<Integer, Integer> positions;

    public GameHistory() {
        this.positions = new HashMap<>();
    }

    //returns true unless there is a repitition
    public boolean addKey (int k) {
        if (this.positions.containsKey(k)) {
            int kVal = this.positions.get(k);
            if (kVal >= 2) {
                return false;
            } else {
                this.positions.put(k, kVal + 1);
            }
        } else {
            this.positions.put(k, 1);
        }
        return true;
    }

    public boolean willBoardBeDraw(int k) {
        return this.positions.containsKey(k) && this.positions.get(k) >= 2;
    }

}
