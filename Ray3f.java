/**
 * Ray class that represents a 3 dimensional Ray.
 * @author Shane del Solar
 */
public class Ray3f
{
    public Vector3f origin;

    public Vector3f direction;

    public Ray3f()
    {
        this.origin = new Vector3f();

        this.direction = new Vector3f();
    }
    /**
     * Ray3f Constructor that takes in and xyz for the ray's origin and then an xyz for it's direction
     * @param startX float -> origin's X value
     * @param startY float -> origin's Y value
     * @param startZ float -> origin's Z value
     * @param dx float -> direction's X value
     * @param dy float -> direction's Y value
     * @param dz float -> direction's Z value
     */
    public Ray3f(float startX, float startY, float startZ, float dx, float dy, float dz)
    {
        this.origin = new Vector3f(startX, startY, startZ);
        
        this.direction = new Vector3f(dx, dy, dz);
    }        
    
	public Ray3f(Vector3f origin)
	{
		this.origin = new Vector3f(origin);
		
		this.direction = new Vector3f();
	}
	
    /**
     * Ray3f Constructor that takes in two Vectors. 1st is the origin Vector, 2nd is the Direction vector, *DEEP COPY*(Vector3f)
     * @param origin Vector3f -> Vector that represents the ray's origin
     * @param direction Vector3f -> Vector that represents the ray's direction
     */
    public Ray3f(Vector3f origin, Vector3f direction)
    {
        this.origin = new Vector3f(origin);
        
        this.direction = new Vector3f(direction);
    }
      
    /**
     * Computes the ray's Location at a given time.
     * @param time float -> Time
     * @return  Vector3f -> Vector3f that represents the ray's location
     */
    public Vector3f computeLocationAtTime(float time)
    {
        Vector3f location = new Vector3f(direction);
        
        location.scale(time);
        location.add(origin);

        return location;
    }

    public void normalize()
    {
        this.direction.normalize();
    }
}