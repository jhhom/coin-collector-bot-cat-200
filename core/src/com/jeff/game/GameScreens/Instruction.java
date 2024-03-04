package com.jeff.game.GameScreens;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class Instruction {
    public static final int MAIN_INST_COUNT = 16;
    public static final int LOOP_FUNC_INST_COUNT = 4;
    public static final int MAIN = 0, LOOP = 1, FUNCTION = 2;

    ArrayList<InstType> mainInstList;
    ArrayList<InstType> loopInstList;
    ArrayList<InstType> funcInstList;

    int nextEmptyMainInstIndex = 0;
    int nextEmptyLoopInstIndex = 0;
    int nextEmptyFunctionInstIndex = 0;

    public Instruction() {
        mainInstList = new ArrayList<>(MAIN_INST_COUNT);
        loopInstList = new ArrayList<>(LOOP_FUNC_INST_COUNT);
        funcInstList = new ArrayList<>(LOOP_FUNC_INST_COUNT);

        for (int i = 0; i < MAIN_INST_COUNT; i++) {
            mainInstList.add(InstType.None);
        }
        for (int i = 0; i < LOOP_FUNC_INST_COUNT; i++) {
            loopInstList.add(InstType.None);
            funcInstList.add(InstType.None);
        }
    }

    public ArrayList<InstType> getMainInstList() { return mainInstList; }
    public ArrayList<InstType> getLoopInstList() { return loopInstList; }
    public ArrayList<InstType> getFuncInstList() { return funcInstList; }

    public void setNextInst(InstType inst, int codeBlock) {
        switch (codeBlock) {
            case MAIN:
                if (nextEmptyMainInstIndex != MAIN_INST_COUNT) {
                    mainInstList.set(nextEmptyMainInstIndex, inst);
                    nextEmptyMainInstIndex++;
                }
                break;
            case LOOP:
                if (nextEmptyLoopInstIndex != LOOP_FUNC_INST_COUNT) {
                    loopInstList.set(nextEmptyLoopInstIndex, inst);
                    nextEmptyLoopInstIndex++;
                }
                break;
            case FUNCTION:
                if (nextEmptyFunctionInstIndex != LOOP_FUNC_INST_COUNT) {
                    funcInstList.set(nextEmptyFunctionInstIndex, inst);
                    nextEmptyFunctionInstIndex++;
                }
                break;
            default:
                assert false;
        }
    }
    public void resetInstList() {
        assert(mainInstList.size() == 16);
        for (int i = 0; i < mainInstList.size(); i++) {
            mainInstList.set(i, InstType.None);
        }
        for (int i = 0; i < funcInstList.size(); i++) {
            funcInstList.set(i, InstType.None);
            loopInstList.set(i, InstType.None);
        }
        nextEmptyFunctionInstIndex = 0;
        nextEmptyLoopInstIndex = 0;
        nextEmptyMainInstIndex = 0;
    }

    public void deletePrevMainInst() {
        if (nextEmptyMainInstIndex != 0) {
            mainInstList.set(nextEmptyMainInstIndex - 1, InstType.None);
            nextEmptyMainInstIndex--;
        }
    }

    public ArrayList<InstType> getExpandedMainInst() {
        ArrayList<InstType> newInstList = new ArrayList<InstType>();
        for (int i = 0; i < mainInstList.size(); i++) {
            if (mainInstList.get(i) == InstType.Function) {
                for (int x = 0; x < LOOP_FUNC_INST_COUNT; x++) {
                    if (funcInstList.get(x) == InstType.None)
                        break;
                    else
                        newInstList.add(funcInstList.get(x));
                }
            } else if (mainInstList.get(i) == InstType.Loop) {
                int loopCount = 0;
                while (loopCount < 3) {
                    for (int x = 0; x < LOOP_FUNC_INST_COUNT; x++) {
                        if (loopInstList.get(x) == InstType.None)
                            break;
                        else
                            newInstList.add(loopInstList.get(x));
                    }
                    loopCount++;
                }
            } else if (mainInstList.get(i) == InstType.None) {
                break;
            } else {
                newInstList.add(mainInstList.get(i));
            }
        }
        return newInstList;
    }



}
