import java.util.Scanner;

public class TicTacToeBoard {
    private char[][] board;
    private char currentPlayer;

    public TicTacToeBoard() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    // 初始化棋盤
    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // 顯示棋盤
    public void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // 嘗試放置棋子
    public boolean placeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            System.out.println("座標超出範圍，請重新輸入！");
            return false;
        }
        if (board[row][col] != ' ') {
            System.out.println("這個位置已經有棋子了！");
            return false;
        }
        board[row][col] = currentPlayer;
        return true;
    }

    // 換玩家
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // 檢查是否有玩家獲勝
    public boolean checkWin() {
        // 檢查橫排與直排
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer &&
                board[i][1] == currentPlayer &&
                board[i][2] == currentPlayer) {
                return true;
            }

            if (board[0][i] == currentPlayer &&
                board[1][i] == currentPlayer &&
                board[2][i] == currentPlayer) {
                return true;
            }
        }

        // 檢查對角線
        if (board[0][0] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][2] == currentPlayer) {
            return true;
        }

        if (board[0][2] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    // 檢查是否平手
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // 主程式：遊戲執行流程
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToeBoard game = new TicTacToeBoard();

        System.out.println("歡迎來到井字遊戲！");
        game.printBoard();

        while (true) {
            System.out.println("輪到玩家 " + game.currentPlayer + "，請輸入座標 (row col)：");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!game.placeMove(row, col)) {
                continue; // 無效下法，再讓該玩家下一次
            }

            game.printBoard();

            if (game.checkWin()) {
                System.out.println("玩家 " + game.currentPlayer + " 獲勝！");
                break;
            }

            if (game.isBoardFull()) {
                System.out.println("平手！");
                break;
            }

            game.switchPlayer();
        }

        scanner.close();
    }
}