/**
 * Abstract class for objects.
 * @author Shane del Solar
 */
public abstract class Intersectable
{
	public int id;
	public Surface surface;
	
	public boolean textured;
	public String textureName;
	
	public boolean noise;	
	public boolean wood;
	public boolean marble;
	public boolean stone;
	
	public float noiseScale;
	
    public Color4f woodColor;
	public float woodScale;
	
    /**
     * Computes the time of intersection between any object and a given ray
     * @param ray Ray3f -> Ray to test against.
     * @return float -> Time of the intersection, or -1.0f if no intersection has occurred
     */
    public abstract float intersectRay(Ray3f ray);

    public boolean equals(Intersectable object)
    {
    	return this.id == object.id;
    }
	
	public abstract Vector3f findNormal(Ray3f ray, float time);
	
	public abstract float[] findUV(Vector3f point);
	
	public void setNoise(float noiseScale)
	{
	    this.noise = true;
	    this.noiseScale = noiseScale;
	}
	
	public void setWood()
	{
	    this.wood = true;
	    
	    this.surface.diffuse = new Color4f(0.9f, 0.75f, 0.6f);
	    this.woodColor = new Color4f(0.65f, 0.48f, 0.39f);
	    
	    this.noiseScale = 8.0f;
	    this.woodScale = 10.0f;
	}
	
	public void setMarble()
	{
	    this.marble = true;
	    this.surface.diffuse.set(-1.0f, -0.5f, -0.8f);
	    this.surface.ambient.set(0.55f, 0.55f, 0.55f); 
	}
	
	public void setStone()
	{
	    this.stone = true;
	}
}
