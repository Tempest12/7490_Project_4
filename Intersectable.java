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
}
