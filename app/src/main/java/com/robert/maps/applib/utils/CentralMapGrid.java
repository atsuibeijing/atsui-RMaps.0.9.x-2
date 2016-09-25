//modify by self 20160916
//add CentralMapGrid class
package com.robert.maps.applib.utils;

import com.robert.maps.applib.utils.Transform;

public class CentralMapGrid {
    public static int relativePositionInCenterMapTile[]={0,0} ;
    //public static int MaxX_MaxY_InCenterMapTile[]={0,0} ;
    //public static int MinX_MinY_InCenterMapTile[]={0,0} ;
    final static int IMAGE_WIDTH =240;
    final static int IMAGE_HEIGHT= 240;
    final static double qd[]={836195.0,821952.0};
    //final static double Ca[]={0.21183,0.61883,0.972,1.53167,2.51,4.49016,6.86731,9.50858,17.1683,22.4508,33.8083,83.1473,0};
    final static double Ca[]={83.1473,33.8083,22.4508,17.1683,9.50858,6.86731,4.49016,2.51,1.53167,0.972,0.61883,0.21183};

    public static int[] LatLon2GRID(final double Latitude, final double Longitude, final int zoom) {
        Transform transform = new Transform();
        GridData griddata1 = transform.GEOHK(2D, Latitude, Longitude);

        double[] l=new double[2];
        l[0]=griddata1.getE();
        l[1]=griddata1.getN();

        //var qd = new myqpoint(836195,821952)
        //var x=zb-qd.x; 	x=834297-qd.836195=-1898  	坐標東(米)
        //var o=qd.y-mb;	o=qd.821952-815911=6041		坐標北(米)
        //e.x=Math.floor(x / Ca[R].x); floor(-1898/33.8083)=-57 	(x)
        //e.y=Math.floor(o / Ca[R].y); floor(6041/33.8083)=178	(y)
		
		/*		Math.floor	Math.round	Math.ceil
		1.4		1		1		2
		1.5		1		2		2
		1.6		1		2		2
		-1.4	-2		-1		-1
		-1.5	-2		-1		-1
		-1.6	-2		-2		-1
		*/

        //MinY=INT((821952-860000)/A6/240)
        //MinX=INT((800000-836195)/A11/240)
        //MinX_MinY_InCenterMapTile[0]=(int)Math.floor((qd[1]-860000)/Ca[zoom-1]/IMAGE_HEIGHT); //y
        //MinX_MinY_InCenterMapTile[1]=(int)Math.floor((800000-qd[0])/Ca[zoom-1]/IMAGE_WIDTH); //x

        //MaxY=INT((821952-800000)/A11/240)
        //MaxX=INT((880000-836195)/A11/240)
        //MaxX_MaxY_InCenterMapTile[0]=(int)Math.ceil((qd[1]-800000)/Ca[zoom-1]/IMAGE_HEIGHT); //y
        //MaxX_MaxY_InCenterMapTile[1]=(int)Math.ceil((880000-qd[0])/Ca[zoom-1]/IMAGE_WIDTH); //x

        double[] k=new double[2];
        k[0]=l[0]-qd[0];
        k[1]=qd[1]-l[1];

        int x=(int)Math.floor(k[0]/Ca[zoom-1]);
        int y=(int)Math.floor(k[1]/Ca[zoom-1]);

        if (x<0)
            if (Math.floor(Math.abs(x)/(IMAGE_WIDTH/2))%2==0)
                relativePositionInCenterMapTile[1]=(IMAGE_WIDTH/2)-Math.abs(x)%(IMAGE_WIDTH/2);
            else
                relativePositionInCenterMapTile[1]=IMAGE_WIDTH-Math.abs(x)%(IMAGE_WIDTH/2);
        else
        if (Math.floor(Math.abs(x)/(IMAGE_WIDTH/2))%2==0)
            relativePositionInCenterMapTile[1]=(IMAGE_WIDTH/2)+Math.abs(x)%(IMAGE_WIDTH/2);
        else
            relativePositionInCenterMapTile[1]=Math.abs(x)%(IMAGE_WIDTH/2);

        if (y<0)
            if (Math.floor(Math.abs(y)/(IMAGE_HEIGHT/2))%2==0)
                relativePositionInCenterMapTile[0]=(IMAGE_HEIGHT/2)-Math.abs(y)%(IMAGE_HEIGHT/2);
            else
                relativePositionInCenterMapTile[0]=IMAGE_HEIGHT-Math.abs(y)%120;
        else
        if (Math.floor(Math.abs(y)/(IMAGE_HEIGHT/2))%2==0)
            relativePositionInCenterMapTile[0]=(IMAGE_HEIGHT/2)+Math.abs(y)%(IMAGE_HEIGHT/2);
        else
            relativePositionInCenterMapTile[0]=Math.abs(y)%(IMAGE_HEIGHT/2);


        int[] ret = new int[2];
        ret[0]=(int)Math.floor(y/IMAGE_HEIGHT);
        if ((Math.abs(y)%IMAGE_HEIGHT)>=(IMAGE_HEIGHT/2)){
            if (y>0)
                ret[0]++;
            else
                ret[0]--;
        }
        ret[1]=(int)Math.floor(x/IMAGE_WIDTH);
        if ((Math.abs(x)%IMAGE_WIDTH)>=(IMAGE_WIDTH/2)){
            if (x>0)
                ret[1]++;
            else
                ret[1]--;
        }
        return ret;
    }

    public static double[] GRID2LatLon(final int xGRID, final int yGRID, final int zoom){
        //x= x * Ca[R].x ;
        //o= o * Ca[R].y;
        //e.x=x+qd.x;
        //e.y=qd.y-o;
        double x=xGRID*IMAGE_WIDTH;
        double y=yGRID*IMAGE_HEIGHT;

        double[] l=new double[2];
        l[0]=x*Ca[zoom-1]+qd[0];
        l[1]=qd[1]-y*Ca[zoom-1];

        Transform transform = new Transform();
        GeoData geodata1 = transform.HKGEO(2D, l[1], l[0]);

        double[] ret = new double[2];
        ret[0] = geodata1.getPhi();
        ret[1] = geodata1.getFlam();
        return ret;
    }

}
 