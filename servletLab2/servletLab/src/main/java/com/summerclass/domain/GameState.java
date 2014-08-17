package com.summerclass.domain;

public class GameState
{
    private String boardSize;
    private String score;

    //comma delimited
    private String board;

    public GameState()
    {
    }

    public GameState( String boardSize, String score, String board )
    {
        this.boardSize = boardSize;
        this.score = score;
        this.board = board;
    }

    public String getBoardSize()
    {
        return boardSize;
    }

    public void setBoardSize( String boardSize )
    {
        this.boardSize = boardSize;
    }

    public String getScore()
    {
        return score;
    }

    public void setScore( String score )
    {
        this.score = score;
    }

    public String getBoard()
    {
        return board;
    }

    public void setBoard( String board )
    {
        this.board = board;
    }
}
