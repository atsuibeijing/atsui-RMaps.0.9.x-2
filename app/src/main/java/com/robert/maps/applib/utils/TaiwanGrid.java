//modify by self 20160916
// add TaiwanGrid class
package com.robert.maps.applib.utils;

public class TaiwanGrid {
    public static int relativePositionInCenterMapTile[]={0,0};
    final static int iCellWidth =256;
    final static int iCellHeight= 256;
    final static double ileft = 118.119;
    final static double iright = 122.018;
    final static double itop = 26.3984;
    final static double ibottom = 21.8871;
    final static double default_width[]={10.48576,5.24288,2.62144,1.31072,0.65536,0.32768,0.16384,0.08192,0.04096,0.02048,0.01024};
    final static double default_height[]={10.48576,5.24288,2.62144,1.31072,0.65536,0.32768,0.16384,0.08192,0.04096,0.02048,0.01024};

    public static int[] LatLon2GRID(final double Latitude, final double Longitude, final int zoom){
        double smallMap_width = default_width[zoom-1] / 4;
        double smallMap_height = default_height[zoom-1] / 4;
        int x=(int) Math.floor((Longitude - ileft) / smallMap_width);
        int y=(int) Math.floor((itop - Latitude) / smallMap_height);

        relativePositionInCenterMapTile[0] = (int)Math.floor((itop - Latitude - y * smallMap_height) / smallMap_height * iCellHeight);
        relativePositionInCenterMapTile[1] = (int)Math.floor((Longitude - ileft - x * smallMap_width) / smallMap_width * iCellWidth);

        int[] ret = new int[2];
        ret[0]=y;
        ret[1]=x;

        //if (relativePositionInCenterMapTile[0]==0) ret[0]--;
        //if (relativePositionInCenterMapTile[1]==0) ret[1]--;
        return ret;
    }

    public static double[] GRID2LatLon(final int xGRID, final int yGRID, final int zoom){
        double smallMap_width = default_width[zoom-1] / 4;
        double smallMap_height = default_height[zoom-1] / 4;

        double[] ret = new double[2];
        ret[0] = itop-yGRID*smallMap_height;
        ret[1] = xGRID*smallMap_width+ileft;
        return ret;
    }
}

