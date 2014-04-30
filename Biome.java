public class Biome
{
    public Vector3f position;
    public Color4f  color;
    
    public Biome(Vector3f position)
    {  
        this.position = new Vector3f(position);
        this.color = new Color4f(0.0f, 0.25f + GridManager.random.nextFloat() * 0.5f, 0.25f + GridManager.random.nextFloat() * 0.25f);
    }
}
