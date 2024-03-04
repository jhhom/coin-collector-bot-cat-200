package com.jeff.game.GameScreens;

public enum InstType {
    MoveUp, MoveDown, MoveLeft, MoveRight,
    Loop, Function, None;

    private boolean isInLoop = false;
    private boolean isInFunction = false;

    public void setIsInLoop (boolean isInLoop) { this.isInLoop = isInLoop; }
    public void setIsInFunction (boolean isInFunction) { this.isInFunction = isInFunction; }
}