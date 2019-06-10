package com.nhs.game.Global;

public class global {

    public  static final int _width=400;
    public  static final int _height=205;
    public  static final float PPM=100 ;//pixel pẻ meter
    public static boolean handleInput=false;
    //get bound map to set camera

    public  static   float _mapWidth;
    public  static   float _mapWidthX2;
    public  static  boolean countPress=false;
    public static  final short NONCOLLISION_BIT=0;
    public  static final  short GROUND_BIT =1;
    public  static final  short MARIO_BIT=2;
    public  static final  short BRICK_BIT=4;
    public  static final  short COINS_BIT=8;
    public  static final  short DISTROYED_BIT=16 ;
    public  static final  short PIPE_BIT =32 ;
    public  static final  short ENERMY_BIT=64 ;
    public  static final  short ENERMY_HEAD_BIT=128;
    public  static final  short ITEM_BIT=256;
    public  static final  short MARIO_HEAD_BIT=512;
    public  static final  short DEADZONE_BIT=1024;
    public static final short MAPZONE_BIT=2048;
    public static final short FIREBALL_BIT=4096;
}
