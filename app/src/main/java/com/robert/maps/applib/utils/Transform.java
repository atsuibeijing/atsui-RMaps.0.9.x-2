//modify by self 20160916
//add Transform class
//This program allows users to perform co-ordinates conversion between
//the HK Geodetic Datum 1980, the HK 80 Grid and the World Geodetic System (WGS) 84 Datum.
//Refer http://www.hydro.gov.hk/Useful/datum/JDatum.html
//Hong Kong Marine Department
//Hydrographic Office
//Datum Transformation 2.0J

package com.robert.maps.applib.utils;

public class Transform
{

    public Transform()
    {
        pi = 3.1415926535900001D;
    }

    public GridData GEOHK(double d, double d1, double d2)
    {
        GridData griddata = new GridData();
        RadiusData radiusdata = new RadiusData();
        double d3 = pi / 180D;
        double d4;
        double d5;
        if(d == 1.0D)
        {
            d4 = 22.312133333333335D * d3;
            d5 = 114.17855555555556D * d3;
        } else
        {
            d4 = 22.310602777777778D * d3;
            d5 = 114.1810138888889D * d3;
        }
        double d6 = d1 * d3; //d1=input n, convert to radians
        double d7 = d2 * d3; //d2=input e, convert to radians
        double d8 = SMER(d, 0.0D, d4);
        double d9 = SMER(d, 0.0D, d6);
        radiusdata = RADIUS(d, d6);  //d6=input n
        double d34 = radiusdata.rho;
        double d35 = radiusdata.rmu;
        double d10 = (d7 - d5) * Math.cos(d6);
        double d11 = Math.tan(d6);
        double d12 = d11 * d11;
        double d13 = d12 * d12;
        double d14 = d12 * d13;
        double d15 = d35 / d34;
        double d16 = Math.pow(d15, 2D);
        double d17 = Math.pow(d15, 3D);
        double d18 = Math.pow(d15, 4D);
        double d19 = d9 - d8;
        double d20 = (d35 / 2D) * Math.pow(d10, 2D) * d11;
        double d21 = (d20 / 12D) * Math.pow(d10, 2D) * ((4D * d16 + d15) - d12);
        double d22 = (d21 / 30D) * Math.pow(d10, 2D) * ((((8D * d18 * (11D - 24D * d12) - 28D * d17 * (1.0D - 6D * d12)) + d16 * (1.0D - 32D * d12)) - 2D * d15 * d12) + d13);
        double d23 = (d22 / 56D) * Math.pow(d10, 2D) * (((1385D - 3111D * d12) + 543D * d13) - d14);
        double d36 = d19 + d20 + d21 + d22 + d23 + 819069.80000000005D;
        double d24 = d35 * d10;
        double d25 = (d24 / 6D) * Math.pow(d10, 2D);
        double d26 = (d25 / 20D) * Math.pow(d10, 2D);
        double d27 = (d26 / 42D) * Math.pow(d10, 2D);
        d25 *= d15 - d12;
        d26 *= ((4D * d17 * (1.0D - 6D * d12) + d16 * (1.0D + 8D * d12)) - d15 * 2D * d12) + d13;
        d27 *= ((61D - 479D * d12) + 179D * d13) - d14;
        double d37 = d24 + d25 + d26 + d27 + 836694.05000000005D;
        if(d == 2D)
        {
            double d28 = d36;
            double d29 = d37;
            double d30 = 0.99999983729999997D;
            double d31 = -2.7858E-005D;
            double d32 = -23.098331000000002D;
            double d33 = 23.149764999999999D;
            d36 = (d30 * d28 - d31 * d29) + d32;
            d37 = d31 * d28 + d30 * d29 + d33;
        }
        griddata.setN(d36);
        griddata.setE(d37);
        return griddata;
    }

    public GeoData HKGEO(double d, double d1, double d2)
    {
        GeoData geodata = new GeoData();
        RadiusData radiusdata = new RadiusData();
        double d3 = pi / 180D;
        double d4;
        double d5;
        if(d == 1.0D)
        {
            d4 = 22.312133333333335D * d3;
            d5 = 114.17855555555556D * d3;
        } else
        {
            d4 = 22.310602777777778D * d3;
            d5 = 114.1810138888889D * d3;
            double d6 = 1.0000001619000001D;
            double d7 = 2.7858E-005D;
            double d8 = 23.098979D;
            double d9 = -23.149125000000002D;
            double d10 = (d6 * d1 - d7 * d2) + d8;
            double d11 = d7 * d1 + d6 * d2 + d9;
            d1 = d10;
            d2 = d11;
        }
        double d28 = d1 - 819069.80000000005D;
        double d37 = d2 - 836694.05000000005D;
        double d12 = 6.8535615239999998D;
        double d13 = 110736.3925D;
        double d14 = (((Math.sqrt(d28 * d12 * 4D + Math.pow(d13, 2D)) - d13) * 0.5D) / d12) * d3;
        double d15 = d4 + d14;
        double d16 = 0.0D;
        double d18;
        do
        {
            d15 += d16;
            double d17 = SMER(d, d4, d15);
            d18 = d28 - d17;
            radiusdata = RADIUS(d, d15);
            double d39 = radiusdata.rho;
            double d41 = radiusdata.rmu;
            d16 = d18 / d39;
        } while(Math.abs(d18) >= 1.0000000000000001E-005D);
        radiusdata = RADIUS(d, d15);
        double d40 = radiusdata.rho;
        double d42 = radiusdata.rmu;
        double d19 = Math.tan(d15);
        double d20 = d19 * d19;
        double d21 = d20 * d20;
        double d22 = d20 * d21;
        double d23 = d42 / d40;
        double d24 = Math.pow(d23, 2D);
        double d25 = Math.pow(d23, 3D);
        double d26 = Math.pow(d23, 4D);
        double d27 = d37;
        d28 = d27 / d42;
        double d38 = d28 * d28;
        double d29 = ((d27 / d40) * d28 * d19) / 2D;
        double d30 = (d29 / 12D) * d38 * ((9D * d23 * (1.0D - d20) - 4D * d24) + 12D * d20);
        double d31 = (d29 / 360D) * d38 * d38 * ((8D * d26 * (11D - 24D * d20) - 12D * d25 * (21D - 71D * d20)) + 15D * d24 * ((15D - 98D * d20) + 15D * d21) + 180D * d23 * (5D * d20 - 3D * d21) + 360D * d21);
        double d32 = (d29 / 20160D) * d38 * d38 * d38 * (1385D + 3633D * d20 + 4095D * d21 + 1575D * d20 * d21);
        double d44 = (((d15 - d29) + d30) - d31) + d32;
        double d33 = d28 / Math.cos(d15);
        double d34 = ((d33 * d38) / 6D) * (d23 + 2D * d20);
        double d35 = ((d33 * d38 * d38) / 120D) * ((d24 * (9D - 68D * d20) - 4D * d25 * (1.0D - 6D * d20)) + 72D * d23 * d20 + 24D * d21);
        double d36 = ((d33 * d38 * d38 * d38) / 5040D) * (61D + 662D * d20 + 1320D * d21 + 720D * d20 * d21);
        double d43 = (((d5 + d33) - d34) + d35) - d36;
        d44 /= d3;
        d43 /= d3;
        geodata.setPhi(d44);
        geodata.setFlam(d43);
        return geodata;
    }

    private RadiusData RADIUS(double d, double d1)
    {
        RadiusData radiusdata = new RadiusData();
        double d2;
        double d3;
        if(d == 1.0D)
        {
            d2 = 6378388D;
            d3 = 0.0033670033670033669D;
        } else
        {
            d2 = 6378137D;					//a=semi-major axis of the reference ellipsoid
            d3 = 0.0033528106647429845D;	//f=flattening of the reference ellipsoid
        }
        double d4 = 2D * d3 - Math.pow(d3, 2D); //e2=2f-f^2
        double d5 = 1.0D - d4 * Math.pow(Math.sin(d1), 2D); //d5=1-e2sin(d1)^2
        double d6 = (d2 * (1.0D - d4)) / Math.pow(d5, 1.5D); //p-radius of curvature in the meridian
        double d7 = d2 / Math.sqrt(d5); //v-radius of curvature in the prime vertical
        radiusdata.rho = d6; //p
        radiusdata.rmu = d7; //v
        return radiusdata;
    }

    private double SMER(double d, double d1, double d2)
    {
        double d3;
        double d4;
        if(d == 1.0D)
        {
            d3 = 6378388D;	//a
            d4 = 0.0033670033670033669D;	//f=1/297.0
        } else
        {
            d3 = 6378137D;    //a
            d4 = 0.0033528106647429845D;  //f=1/298.2572235634
        }
        double d5 = 2D * d4 - Math.pow(d4, 2D);  //e2=2f-f^2
        d5 = Math.sqrt(d5);
        double d6 = 1.0D + 0.75D * Math.pow(d5, 2D) + 0.703125D * Math.pow(d5, 4D) + 0.68359375D * Math.pow(d5, 6D);
        double d7 = 0.75D * Math.pow(d5, 2D) + 0.9375D * Math.pow(d5, 4D) + 1.025390625D * Math.pow(d5, 6D);
        double d8 = 0.234375D * Math.pow(d5, 4D) + 0.41015625D * Math.pow(d5, 6D);
        double d9 = 0.068359375D * Math.pow(d5, 6D);
        double d10 = d2 - d1;
        double d11 = Math.sin(2D * d2) - Math.sin(2D * d1);
        double d12 = Math.sin(4D * d2) - Math.sin(4D * d1);
        double d13 = Math.sin(6D * d2) - Math.sin(6D * d1);
        double d14 = d3 * (1.0D - Math.pow(d5, 2D));
        d14 *= ((d6 * d10 - (d7 * d11) / 2D) + (d8 * d12) / 4D) - (d9 * d13) / 6D;
        return d14;
    }

    public double pi;
}

class RadiusData
{

    public RadiusData()
    {
        rho = 0.0D;
        rmu = 0.0D;
    }

    double rho;
    double rmu;
}


class GridData
{

    public GridData()
    {
        n = 0.0D;
        e = 0.0D;
    }

    double getE()
    {
        return e;
    }

    double getN()
    {
        return n;
    }

    void setE(double d)
    {
        e = d;
    }

    void setN(double d)
    {
        n = d;
    }

    public double n;
    public double e;
}


class GeoData
{

    public GeoData()
    {
        phi = 0.0D;
        flam = 0.0D;
    }

    double getFlam()
    {
        return flam;
    }

    double getPhi()
    {
        return phi;
    }

    void setFlam(double d)
    {
        flam = d;
    }

    void setPhi(double d)
    {
        phi = d;
    }

    public double phi;
    public double flam;
}

