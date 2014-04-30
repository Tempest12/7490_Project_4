import java.util.ArrayList;
import java.util.Random;

public class GridManager
{
    public float gridSize;
    public int density;
    public float transitionSize;

    public ArrayList<Grid> gridList;
    public Color4f grout;

    public GridManager(float gridSize, int density, float transitionSize)
    {
        this.gridSize = gridSize;
        this.density = density;
        this.transitionSize = transitionSize;
        
        this.gridList = new ArrayList<Grid>();
        this.grout = new Color4f(0.65f);
    }
    
    public int[] convertCoordinates(Vector3f global)
    {
        int[] gridCoordinates = new int[3];
        
        gridCoordinates[0] = (int)(global.x / this.gridSize);
        gridCoordinates[1] = (int)(global.y / this.gridSize);
        gridCoordinates[2] = (int)(global.z / this.gridSize);
        
        return gridCoordinates;
    }
    
    public Color4f getColor(Vector3f point)
    {
        //System.out.println("Get Color called.");   
        
        int[] center = this.convertCoordinates(point);
        Grid grid = null;
        
        //System.out.println("Center is: " + center);
        int counter = 0;
        
        Biome closest = null;
        Biome nextClosest = null;      
        Biome currentBiome = null;
        
        float minDistance = Float.MAX_VALUE;
        float nextDistance = Float.MAX_VALUE;
        float currentDistance = 0.0f;
        
        for(int x = center[0] - 1; x <= center[0] + 1; x++)
        {
            for(int y = center[1] - 1; y <= center[1] + 1; y++)
            {
                for(int z = center[2] - 1; z <= center[2] + 1; z++)
                {
                    grid = this.getGridCell(x, y, z);
                    //System.out.println("Grid Cell has: " + grid.biomes.size() + " biomes.");
                
                    for(int index = 0; index < grid.biomes.size(); index++)
                    {
                        currentBiome = grid.biomes.get(index);
                        currentDistance = point.distance(currentBiome.position);
                        counter++;
                        
                        if(currentDistance < minDistance)
                        {
                            nextDistance = minDistance;
                            nextClosest = closest;
                            
                            minDistance = currentDistance;
                            closest = currentBiome;
                        }
                        else if(currentDistance < nextDistance)
                        {
                            nextDistance = currentDistance;
                            nextClosest = currentBiome;
                        }
                    }
                }
            }
        }
        
        //System.out.println("Get Color finished. counter is: " + counter);
        //System.out.println("Returning: " + closest);
        float difference = minDistance - nextDistance;        
        difference = Math.abs(difference);
        
        if(difference < this.transitionSize)
        {
            Color4f noiseGrout = new Color4f(grout.red + random.nextFloat() * 0.2f);
        
            return noiseGrout;
        }
        else
        {        
            return closest.color;
        }
        
        //return closest.color;
    }
    
    public Grid getGridCell(int x, int y, int z)
    {
        //System.out.println("getGridCell Called");
        Grid grid = null;
    
        for(int index = 0; index < this.gridList.size(); index++)
        {
            grid = this.gridList.get(index);
            
            if(grid.matchLocation(x, y, z))
            {
                return grid;
            }
        }
        
        int[] point = new int[3];
        point[0] = x;
        point[1] = y;
        point[2] = z;
        
        grid = new Grid(point, this.density, this.gridSize);
        this.gridList.add(grid);
        
        //System.out.println("getGridCell Done. Returning: " + grid);
        return grid;
    }
    
    public static Random random = new Random(178);
}
