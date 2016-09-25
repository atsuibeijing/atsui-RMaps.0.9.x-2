//modify by self 20160916
//add HK1980GRID class
//測量採用“國際海福德”（ 1910） 為參考橢球， 在與以前相同的基準原點(零號三角網測站)訂定了新
//的“香港大地基準1980”(HK80)。同時又改用“橫墨卡托投影”，在相同的投影原點，
//發展新的平面方格網坐標系統， 命名為“香港1980 方格網”(HK1980 方格網)。

package com.robert.maps.applib.utils;

import com.robert.maps.applib.utils.Transform;

public class HK1980GRID {
    public static int relativePositionInCenterMapTile[]={0,0} ;
    public final static int TOTAL_TILES[] = {5, 15, 30, 120, 240, 600, 1200};
    final static int IMAGE_WIDTH =257;
    final static int IMAGE_HEIGHT= 257;
    final static double a[]={783800.0, 772900.0};
    final static double i[]={885800.0, 874900.0};

    public static int[] LatLon2GRID(final double Latitude, final double Longitude, final int zoom) {
        Transform transform = new Transform();
        GridData griddata1 = transform.GEOHK(2D, Latitude, Longitude);

		/*
		GridData griddata1 = transform.GEOHK(
				2D,
				(new BigDecimal(Latitude).setScale(4, BigDecimal.ROUND_HALF_EVEN)).doubleValue(),
				(new BigDecimal(Longitude).setScale(5, BigDecimal.ROUND_HALF_EVEN)).doubleValue()
				);
		*/
        double[] l=new double[2];
        l[0]=griddata1.getE();
        l[1]=griddata1.getN();

        double[] k=new double[2];
        k[0]=(i[0]-a[0])/TOTAL_TILES[zoom-1]/IMAGE_WIDTH;
        k[1]=(i[1]-a[1])/TOTAL_TILES[zoom-1]/IMAGE_HEIGHT;

        int x=(int)((l[0]-a[0])/k[0]);
        int y=(int)(TOTAL_TILES[zoom-1]*IMAGE_HEIGHT-(l[1]-a[1])/k[1]);

        relativePositionInCenterMapTile[0]=y % IMAGE_HEIGHT;
        relativePositionInCenterMapTile[1]= x % IMAGE_WIDTH;

        int[] ret = new int[2];
        ret[0]=(int)Math.floor((TOTAL_TILES[zoom-1]*IMAGE_HEIGHT-y)/IMAGE_HEIGHT);
        ret[1]=(int)Math.floor(x /IMAGE_WIDTH);
        if (relativePositionInCenterMapTile[0]==0) ret[0]--;
        return ret;
    }

    public static double[] GRID2LatLon(final int xGRID, final int yGRID, final int zoom){
        double[] k=new double[2];
        k[0]=(i[0]-a[0])/TOTAL_TILES[zoom-1]/IMAGE_WIDTH;
        k[1]=(i[1]-a[1])/TOTAL_TILES[zoom-1]/IMAGE_HEIGHT;

        double x=xGRID*IMAGE_WIDTH;
        double y=TOTAL_TILES[zoom-1]*IMAGE_HEIGHT-yGRID*IMAGE_HEIGHT;


        double[] l=new double[2];
        l[0]=x*k[0]+a[0];
        l[1]=(TOTAL_TILES[zoom-1]*IMAGE_HEIGHT-y)*k[1]+a[1];

        Transform transform = new Transform();
        GeoData geodata1 = transform.HKGEO(2D, l[1], l[0]);

        double[] ret = new double[2];
        ret[0] = geodata1.getPhi();
        ret[1] = geodata1.getFlam();
        return ret;
    }
}
