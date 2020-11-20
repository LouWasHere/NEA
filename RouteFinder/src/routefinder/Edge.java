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
public class Edge 
{
    private Corner corner;
    private Road road;
    public Edge(Corner corner, Road road)
    {
        this.corner = corner;
        this.road = road;
    }
    public Road getRoad()
    {
        return road;
    }
    public void setRoad(Road newRoad)
    {
        this.road = newRoad;
    }
    public Corner getCorner()
    {
        return corner;
    }
    public void setCorner(Corner newCorner)
    {
        this.corner = newCorner;
    }
}
