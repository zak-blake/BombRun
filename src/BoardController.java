import java.util.TimerTask;
import java.util.Timer;

/*  no message if an action is not possible*/
public class BoardController{

    public enum movement{
        UP, DOWN, LEFT, RIGHT, BOMB
    }
    private Board board;
    private int rowSize;

    public BoardController(Board b){
        this.board = b;
        this.rowSize = board.getRowSize();
    }

    public void playerAction(int playerID, movement m ){

        int currentIndex = board.getPlayerIndex(playerID);
        switch (m){
            case UP:    if (board.movePlayer(currentIndex, 1)) {
                            board.setPlayerIndex(currentIndex - rowSize, playerID);
                        }
                        break;

            case DOWN:  if (board.movePlayer(currentIndex, 2)) {
                            board.setPlayerIndex(currentIndex + rowSize, playerID);
                        }
                        break;

            case LEFT:  if (board.movePlayer(currentIndex, 4)) {
                            board.setPlayerIndex(currentIndex - 1, playerID);
                        }
                        break;

            case RIGHT: if (board.movePlayer(currentIndex, 3)) {
                            board.setPlayerIndex(currentIndex + 1, playerID);
                        }
                        break;

            case BOMB:  Player player = Main.gameBoard.getPlayer(currentIndex);
                        if (player.getBombCount() > 0
                            && !player.isDead()
                            && !Main.gameBoard.getCell(currentIndex).hasBomb()
                            )
                        {
                            board.setBomb(currentIndex, 3);
                            player.subBomb();
                            Timer timer = new Timer();
                            timer.schedule(new replenishBombTask(playerID),3000);
                        }
                        break;

            default:    System.out.println("Error in BoardController");
                        return;
        }
    }

    public class replenishBombTask extends TimerTask{
        private int ID;
        public replenishBombTask(int ID){
            this.ID = ID;
        }
        public void run(){
            Player P = Main.gameBoard.getPlayer(Main.gameBoard.getPlayerIndex(ID));
            P.addBomb();
        }
    }


}
