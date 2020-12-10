/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;

/**
 *
 * @author l-bishop
 */
public class Road extends Node
{
    
    private int length;
    private int trafficLevel;
    public Road(String name, int length, int curvature, int traffic)
    {
        super();
        super.name = name;
        this.length = length;
        super.curvature = curvature;
        this.trafficLevel = traffic;
    }
    public int getLength()
    {
        return length;
    }
    public void setLength(int length)
    {
        this.length = length;
    }
    public int getTrafficLevel()
    {
        return trafficLevel;
    }
    public void setTrafficLevel(int trafficLevel)
    {
        this.trafficLevel = trafficLevel;   
    }
    
}
