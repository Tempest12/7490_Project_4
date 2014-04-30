import java.util.ArrayList;

public class Grid
{
    public ArrayList<Biome> biomes;
    public int[] location;
    public float gridSize;

    public Grid(int[] location, int density, float gridSize)
    {
        this.location = new int[3];
        this.location[0] = location[0];
        this.location[1] = location[1];
        this.location[2] = location[2];
        
        this.biomes = new ArrayList<Biome>();
        
        int count = GridManager.random.nextInt(density) + 1;
        //System.out.println("Making a grid.  Biome count is: " + count);
        this.gridSize = gridSize;
        
        Vector3f point = new Vector3f();
        for(int index = 0; index < count; index++)
        {
            point.set(location[0] + GridManager.random.nextFloat() * gridSize,
                      location[1] + GridManager.random.nextFloat() * gridSize,
                      location[2] + GridManager.random.nextFloat() * gridSize);
                      
            this.biomes.add(new Biome(point));
        }
        
        //System.out.println("Done making a grid cell.");
    }
    
    public boolean matchLocation(int x, int y, int z)
    {
        return (location[0] == x) && (location[1] == y) && (location[2] == z);
    }
}
