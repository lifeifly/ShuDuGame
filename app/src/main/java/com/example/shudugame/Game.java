package com.example.shudugame;

public class Game {
    //数据初始化数据的基础
    private final String str = "360000000004230800000004200"
            + "070460003820000014500013020"
            + "001900000007048300000000045";
    private int[] shuduku = new int[81];
    //用于存储每个单元格已经不可用的数据
    private int[][][] used = new int[9][9][];

    public Game() {
        this.shuduku = fromPuzzieString(str);
        //计算每个单元格用过的数据
        calculateUsedTiles();
    }

    //根据横向第几格和纵向第几格返回单元格应该填写的数字
    private int getTile(int x, int y) {
        return shuduku[y * 9 + x];
    }

    public String getTileString(int x, int y) {
        int w = getTile(x, y);
        if (w == 0) {
            return "";
        } else {
            return String.valueOf(w);
        }
    }

    protected int[] fromPuzzieString(String str) {
        int[] shudu = new int[str.length()];
        for (int i = 0; i < shudu.length; i++) {
            //字符数字"0"-"9"对应int47-58，只需减去‘0’对应的int就是实际大小
            shudu[i] = str.charAt(i) - '0';
        }
        return shudu;
    }

    //用于计算所有单元格对应的不可用数据
    public void calculateUsedTiles() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x, y);
            }
        }
    }

    //用于计算某一格已经用过的数据
    public int[] getUsedTile(int x, int y) {
        return used[x][y];
    }

    //计算某个单元格已经不可用的数据
    public int[] calculateUsedTiles(int x, int y) {
        int[] c = new int[9];
        //一列上已经用过的数据
        for (int i = 0; i < 9; i++) {
            if (i == y) {
                continue;
            }
            int t = getTile(x, i);
            if (t != 0) {
                c[t - 1] = t;
            }
        }
        //一横上用过的数据
        for (int i = 0; i < 9; i++) {
            if (i == x) {
                continue;
            }
            int t = getTile(i, y);
            if (t != 0) {
                c[t - 1] = t;
            }
        }
        //一个大九宫格中用过的数据
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        //int除于int还是int，会舍去小数
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y) {
                    continue;
                }
                int t = getTile(i, j);
                if (t != 0) {
                    c[t - 1] = t;
                }
            }
        }
        //compress
        int use = 0;
        for (int i : c) {
            if (i != 0) {
                use++;
            }
        }
        int c1[] = new int[use];
        use = 0;
        for (int i : c) {
            if (i != 0) {
                c1[use++] = i;
            }
        }
        return c1;
    }
    protected boolean setTileValid(int x,int y,int value){
        int[] tiles=getUsedTile(x,y);
        if (value!=0){
            for (int tile:tiles){
                if (tile==value){
                    return false;
                }
            }
        }
        setTile(x,y,value);
        calculateUsedTiles();
        return true;
    }

    private void setTile(int x, int y, int value) {
        shuduku[y*9+x]=value;
    }


}

